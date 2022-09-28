package com.ssafy.rideus.service;


import com.jcraft.jsch.JSchException;
import com.ssafy.rideus.common.exception.NotFoundException;
import com.ssafy.rideus.domain.Course;
import com.ssafy.rideus.domain.base.Coordinate;
import com.ssafy.rideus.domain.collection.CourseCoordinate;
import com.ssafy.rideus.domain.collection.NearInfo;
import com.ssafy.rideus.dto.CategoryDto;
import com.ssafy.rideus.hadoop.Controller.SSHUtils;
import com.ssafy.rideus.repository.jpa.CourseRepository;
import com.ssafy.rideus.repository.mongo.CourseCoordinateRepository;
import com.ssafy.rideus.repository.mongo.NearInfoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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

import static com.ssafy.rideus.service.NearInfoServiceImpl.distance;


@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class HadoopServiceImpl implements HadoopService{

    @Autowired
    SSHUtils ssh;
    @Autowired
    CourseRepository courseRepository;
    @Autowired
    NearInfoService nearInfoService;
    @Autowired
    NearInfoRepository nearInfoRepository;
    @Autowired
    CourseCoordinateRepository courseCoordinateRepository;
    @Autowired
    MongoTemplate mongoTemplate;

    // FIXME: 배포 시 EC2 디렉토리로 변경
    private static final String LOCAL_FILE_PATH = "C:\\Users\\SSAFY\\Desktop\\input";
//    private static final String LOCAL_FILE_PATH = "/home/ubuntu/input/";
    private static final String SERVER_FILE_PATH = "/home/j7a603/";
    private static final String INPUT_FILE_NAME = "input";
    private static final String OUTPUT_FILE_NAME = "output";
    private static final String FILE_TYPE = ".txt";

    static final int DISTANCE_LIMIT = 2000; // 반경 4km 안에 있는 시설 정보 조회

    /*
    o 1. 코스 주변정보 mongodb에 update
    o 2. 코스 주변정보 txt file로 카테고리만 분류
    3. hadoop cluster 서버 hdfs 안에 파일 복사
    4. mapreduce 실행
    5. 가장 많은 카테고리를 mysql 코스 카테고리로 update
     */

    @Transactional
    /* 새로 입력된 코스 주변정보 update */
    public List<NearInfo> mapreduceCategory(String courseid) {

        /* mongoDB 새로 입력된 코스 주변정보 입력 */
        List<NearInfo> nearInfos = nearInfoService.saveNearInfo(courseid);

        /* 코스 주변정보 카테고리 mapreudce 실행, 결과 MySQL Update */
        parsingCourseCategory(nearInfos, courseid);

        return nearInfos;
    }

    /* 코스 주변정보 카테고리만 분류해서 txt파일로 변환 */

    /**
     * 실행 순서
     * 1. make input.txt, course nearinfo category file
     * 2. send input.txt file to cluster server
     * 3. copy input.txt to hdfs
     * 4. run mapreduce
     * 5. read output file, MySQL Update
     *
     * @param courseNearinfos : 코스 주변 정보
     * @param courseid : 코스 id
     */
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
    @Override
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
    @Transactional
    @Override
    public void settingDB(List<String> newCourseList) {

        List<Course> courseByCategoryNull = courseRepository.findCoruseByCategoryNull();
        newCourseList = new ArrayList<>();
        for(Course course : courseByCategoryNull)
            newCourseList.add(course.getId());
        System.out.println(Arrays.toString(newCourseList.toArray()));

        List<NearInfo> allNearInfo = nearInfoRepository.findAll();
        int count = 0;
        for(String courseId : newCourseList) {

            List<String> nearInfoIds = new ArrayList<>();
            if(count++ == 20 )break;
//            mapreduceCategory(courseId);
            CourseCoordinate courseCoordinate =
                    courseCoordinateRepository
                            .findById(courseId)
                            .orElseThrow(() -> new NotFoundException("코스 상세 조회 실패"));

            // mongoDB에서 체크포인트 리스트 가져오기
            List<Coordinate> checkpoints = courseCoordinate.getCheckpoints();
            // 주변 정보 중복 체크 map
            Map<String, NearInfo> checkedInfo = new HashMap<>();

            log.info("all Near Info size = " + allNearInfo.size());
            log.info("check point size = " + checkpoints.size());

            // 체크포인트 별로 주변정보 검색
            int checkpointCounter = 1;
            for ( Coordinate checkPoint : checkpoints ) {

                log.info(checkpointCounter++ +" "+ checkPoint);
                // 체크포인트 좌표
                double cpLat = Double.parseDouble(checkPoint.getLat());
                double cpLng = Double.parseDouble(checkPoint.getLng());

                // 주변 정보 전체 조회
                for ( NearInfo nearInfo : allNearInfo ) {
                    if(checkedInfo.containsKey(nearInfo.getId())) continue;

                    // 주변 정보 위,경도 좌표
                    double infoLat = Double.parseDouble(nearInfo.getNearinfoLat());
                    double infoLng = Double.parseDouble(nearInfo.getNearinfoLng());

                    // 제한 거리 안에 존재하는 경우
                    if(distance(infoLat, infoLng, cpLat, cpLng) < DISTANCE_LIMIT) {
                        checkedInfo.put(nearInfo.getId(), nearInfo);
                        nearInfoIds.add(nearInfo.getId());
                    }

                } // end of neainfo loop
            } // end of checkpoint loop

            List<NearInfo> nearInfos = new ArrayList<>(checkedInfo.values());
            log.info("near Infos size = " + nearInfos.size());

//            courseCoordinateRepository.save( new CourseCoordinate(
//                    courseCoordinate.getId(),
//                    courseCoordinate.getCoordinates(),
//                    courseCoordinate.getCheckpoints(),
//                    nearInfos));
            Query query = new Query().addCriteria(Criteria.where("_id").is(courseId));
            Update update = new Update();
            update.set("nearInfoIds", nearInfoIds);
            mongoTemplate.updateFirst(query, update, CourseCoordinate.class);

            /* 코스 주변정보 카테고리 mapreudce 실행, 결과 MySQL Update */
            parsingCourseCategory(nearInfos, courseId);

        }


    }


}
