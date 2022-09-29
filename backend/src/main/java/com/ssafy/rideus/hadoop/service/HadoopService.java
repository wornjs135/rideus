package com.ssafy.rideus.hadoop.service;


import com.jcraft.jsch.JSchException;
import com.ssafy.rideus.common.exception.NotFoundException;
import com.ssafy.rideus.domain.Course;
import com.ssafy.rideus.domain.base.Coordinate;
import com.ssafy.rideus.domain.collection.CourseCoordinate;
import com.ssafy.rideus.domain.collection.NearInfo;
import com.ssafy.rideus.dto.CategoryDto;
import com.ssafy.rideus.hadoop.util.SSHUtils;
import com.ssafy.rideus.repository.jpa.CourseRepository;
import com.ssafy.rideus.repository.mongo.CourseCoordinateRepository;
import com.ssafy.rideus.repository.mongo.NearInfoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

import static com.ssafy.rideus.service.NearInfoService.distance;


@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class HadoopService{

    private final SSHUtils ssh;
    private final  CourseRepository courseRepository;
    private final  NearInfoRepository nearInfoRepository;
    private final  CourseCoordinateRepository courseCoordinateRepository;
    private final  MongoTemplate mongoTemplate;

    // FIXME: 배포 시 EC2 디렉토리로 변경
    private static final String LOCAL_FILE_PATH = "C:\\Users\\SSAFY\\Desktop\\input";
//    private static final String LOCAL_FILE_PATH = "/home/ubuntu/input/";
    private static final String SERVER_FILE_PATH = "/home/j7a603/";
    private static final String INPUT_FILE_NAME = "input";
    private static final String OUTPUT_FILE_NAME = "output";
    private static final String FILE_TYPE = ".txt";

    static final int DISTANCE_LIMIT = 1000; // 반경 1km 안에 있는 시설 정보 조회

    /*
    o 1. 코스 주변정보 mongodb에 update
    o 2. 코스 주변정보 txt file로 카테고리만 분류
    3. hadoop cluster 서버 hdfs 안에 파일 복사
    4. mapreduce 실행
    5. 가장 많은 카테고리를 mysql 코스 카테고리로 update
     */


    /* 코스 주변정보 카테고리만 분류해서 txt파일로 변환 */
    @Transactional
    public void hadoopNearInfo() {

        List<Course> courses = courseRepository.findCoruseByCategoryNull();
        List<String> courseIds = new ArrayList<>();
        for(Course course : courses) courseIds.add(course.getId());

        // 전체 주변정보
        List<NearInfo> allNearInfo = nearInfoRepository.findAll();

        for(String courseId : courseIds) {
            log.info("course id : " + courseId);

            // 중복체크 맵
            Map<String, NearInfo> checkedInfo = new HashMap<>();
            // 카테고리 분류 리스트
            Map<String, List<String>> nearInfoIds = new HashMap<>();

            // 코스 정보 mongoDB find
            CourseCoordinate courseCoordinate =
                    courseCoordinateRepository
                            .findById(courseId)
                            .orElseThrow(() -> new NotFoundException("코스 상세 조회 실패"));

            List<Coordinate> checkpoints = courseCoordinate.getCheckpoints();

            int checkpointCounter = 0;
            int nearInfoCounter = 0;
            for(Coordinate checkpoint : checkpoints) {

                log.info(++checkpointCounter + " " + checkpoint);
                // 체크포인트 좌표
                double cpLat = Double.parseDouble(checkpoint.getLat());
                double cpLng = Double.parseDouble(checkpoint.getLng());

                // 주변 정보 전체 조회
                for (NearInfo nearInfo : allNearInfo) {

                    // 이미 저장된 주변정보
                    if (checkedInfo.containsKey(nearInfo.getId())) continue;

                    // 주변 정보 위,경도 좌표
                    double infoLat = Double.parseDouble(nearInfo.getNearinfoLat());
                    double infoLng = Double.parseDouble(nearInfo.getNearinfoLng());

                    // 제한 거리 안에 존재하는 경우
                    if (distance(infoLat, infoLng, cpLat, cpLng) < DISTANCE_LIMIT) {
                        checkedInfo.put(nearInfo.getId(), nearInfo);

                        String category = nearInfo.getNearinfoCategory();

                        if (!nearInfoIds.containsKey(category))
                            nearInfoIds.put(category, new ArrayList<>());

                        nearInfoIds.get(category).add(nearInfo.getId());
                        nearInfoCounter++;
                    } // end of calculate distance
                } // end of neainfo loop
            } // end of checkpoint

//            // 중복체크 맵
//            Map<String, NearInfo> checkedInfo = new HashMap<>();
//            // 카테고리 분류 리스트
//            Map<String, List<String>> nearInfoIds = new HashMap<>();

            // hadoop에 넘겨줄 데이터
            List<NearInfo> nearInfos = new ArrayList<>(checkedInfo.values());
            log.info("near Infos size = " + nearInfos.size());

            Query query = new Query().addCriteria(Criteria.where("_id").is(courseId));
            Update update = new Update();
            update.set("nearInfoIds", nearInfoIds);
            mongoTemplate.updateFirst(query, update, CourseCoordinate.class);

            /* 코스 주변정보 카테고리 mapreudce 실행, 결과 MySQL Update */
            parsingCourseCategory(nearInfos, courseId);



        } // end of courseId



    }

    public void parsingCourseCategory(List<NearInfo> courseNearinfos,String courseid) {

        /* input 파일 생성*/
        File file = new File(LOCAL_FILE_PATH + INPUT_FILE_NAME + FILE_TYPE);
        try {
            if(file.exists()) file.delete();
            if (file.createNewFile())
                log.info("File created");
            else
                log.info("File already exists");

        } catch (IOException e) {
            e.printStackTrace();
        }

        /* 카테고리 input.txt에 입력 */
        try
        {
            String filePath = LOCAL_FILE_PATH + INPUT_FILE_NAME + FILE_TYPE;
            FileWriter fw = new FileWriter(filePath, true);

            /* 주변정보 카테고리만 추출 후 txt 파일에 입력 */
            for(NearInfo nearinfo : courseNearinfos) {
                fw.write(nearinfo.getNearinfoCategory() + "\n");
            }
            fw.close();

            /* input파일 hdfs로 복사 */
            copyFileToHdfs(courseid);
        }
        catch(Exception e){
            System.out.println(e);
        }

    }
    @Transactional
    public void copyFileToHdfs(String courseid) {

        /* session 연결 */
        if (!ssh.checksession()) {
            try {
                ssh.connectSSH();
            } catch (JSchException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

            /*
             [Process 1]
             1. local file을 server로 복사
             2. hdfs에 file 전송
            */
        String localPath = LOCAL_FILE_PATH + INPUT_FILE_NAME + FILE_TYPE;
        String serverPath = SERVER_FILE_PATH + INPUT_FILE_NAME + FILE_TYPE;
        String inputFileName = INPUT_FILE_NAME + FILE_TYPE;
        String outputFileName = OUTPUT_FILE_NAME + FILE_TYPE;

        try {

            ssh.sendFileToOtherServer(localPath, SERVER_FILE_PATH, inputFileName);

            // server command
            ssh.getSSHResponse("hdfs dfs -rm -r " + inputFileName);
            ssh.getSSHResponse("hdfs dfs -put " + inputFileName);
            /* cluster 속 input.txt파일 hdfs로 복사 */

        } catch (Exception e) {
            e.printStackTrace();
        }

        /*
            [Proecss 2]
            1. 기존 output파일 삭제
            2. hadoop mapreduce 실행
         */
        try {
                    ssh.getSSHResponse("hdfs dfs -rm -r " + OUTPUT_FILE_NAME);
                    ssh.getSSHResponse("hadoop jar category.jar categorycount " + inputFileName + " " + OUTPUT_FILE_NAME);

                    // for debugging
                    String sshResponse = ssh.getSSHResponse("hdfs dfs -cat " + OUTPUT_FILE_NAME + "/*");
                    log.info("sshResponse = " + sshResponse);


                    List<CategoryDto> categories = new ArrayList<>();
                    StringTokenizer st = new StringTokenizer(sshResponse, "\n");

                    /* output 파일 분석 */
                    int debugLineCounter = 1;
                    while(st.hasMoreTokens()) {
                        String line = st.nextToken();
                        log.info(debugLineCounter++ + " : "+line);
                        String[] split = line.split("\t");
                        String category = split[0];
                int count = Integer.parseInt(split[1]);
                categories.add(new CategoryDto(category, count));
            }

        /*
            [Process 3]
            1. 카테고리가 많은 순서대로 정렬
            2. 가장 많은 카테고리를 MySQL 코스 카테고리로 저장
         */
            Collections.sort(categories);
            CategoryDto category = categories.get(0);
            log.info("selected category = " + category);

            /* Update MySQL course category */
            courseRepository.updateCourseCategoryByid(courseid, category.getCategory());

            log.info("******** End of Hadoop *******");

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


    }


}
