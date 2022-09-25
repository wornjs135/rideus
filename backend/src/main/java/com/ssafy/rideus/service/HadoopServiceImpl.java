package com.ssafy.rideus.service;


import com.jcraft.jsch.JSchException;
import com.ssafy.rideus.domain.Course;
import com.ssafy.rideus.domain.collection.CourseCoordinate;
import com.ssafy.rideus.domain.collection.NearInfo;
import com.ssafy.rideus.hadoop.Controller.SSHUtils;
import com.ssafy.rideus.repository.jpa.CourseRepository;
import com.ssafy.rideus.repository.mongo.CourseCoordinateRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

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
    CourseCoordinateRepository courseCoordinateRepository;


    //    private String sendFilePath = "/home/ubuntu/mysqltablefile/"; // ubuntu ?
    private String sendFilePath = "C:\\input\\";
    private String receiveFilePath = "/home/j7a603/"; // hadoop path
    private String hadoopdefault = "/usr/local/hadoop/bin/"; // hadoop
    private String hadoopdefault2 = "/home/j7a603/"; // hadoop



    /*
    o 1. 코스 주변정보 mongodb에 update
    o 2. 코스 주변정보 txt file로 카테고리만 분류
    3. hadoop cluster 서버 hdfs 안에 파일 복사
    4. mapreduce 실행
    5. 가장 많은 카테고리를 mysql 코스 카테고리로 update
     */
    @Transactional
    @Override
    public void saveCategoryToCourse(String courseid) {

        // session 연결 상태 아님
        if (!ssh.checksession()) {
            try {
                ssh.connectSSH();
            } catch (JSchException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        System.out.println("connect to ssh");
        StringBuilder sb,temp;
        try {

            ssh.getSSHResponse("hdfs dfs -rm -r output");
            ssh.getSSHResponse("hadoop jar category.jar categorycount a.txt output2");
            String sshResponse = ssh.getSSHResponse("hdfs dfs -cat output2/*");
            System.out.println("sshResponse = " + sshResponse);

            List<Category> categories = new ArrayList<>();

            StringTokenizer st = new StringTokenizer(sshResponse, "\n");
            int line = 1;
            while(st.hasMoreTokens()) {
                String[] split = st.nextToken().split("\t");
                String category = split[0];
                int count = Integer.parseInt(split[1]);
                categories.add(new Category(category, count));
                System.out.println(line++ + " : "+st.nextToken());
            }

            Collections.sort(categories);
            System.out.println(categories.get(0));
            Category category = categories.get(0);
            // mysql 카테고리 넣기
            Course course = courseRepository.findById(courseid).get();
            System.out.println("Before : " + course.toString());
            course.setCategory(category.getCategory());
            System.out.println("After : " + course.toString());
            courseRepository.save(course);


            log.info("********전송끝 *******");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


    /* 새로 입력된 코스 주변정보 update */
    public List<NearInfo> updateCourseNearInfo(String courseid) {
        List<NearInfo> nearInfos = nearInfoService.saveNearInfo(courseid);
        parsingCourseCategory(nearInfos, courseid);
        return nearInfos;
    }

    /* 코스 주변정보 카테고리만 분류해서 txt파일로 변환 */
    public void parsingCourseCategory(List<NearInfo> courseNearinfos,String courseid) {


        /* input 파일 생성*/
        File file = new File("C:\\input\\file.txt");
        try {
            if(file.exists()){ // 파일이 존재하지 않으면
                file.delete();
            }
            if (file.createNewFile()) {
                System.out.println("File created");
            } else {
                System.out.println("File already exists");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        /* 카테고리 input.txt에 입력 */
        try
        {
            String filePath = "C:\\input\\file.txt";
            FileWriter fw = new FileWriter(filePath, true);
            String fileInputString = "";

            /* txt 파일에 입력 */
            for(NearInfo nearinfo : courseNearinfos) {
                fw.write(nearinfo.getNearinfoCategory() + "\n");
            }
            fw.close();

            /* input파일 hdfs로 복사 */
            copyFileToHdfs(file ,filePath, courseid);
        }
        catch(Exception e){
            System.out.println(e);
        }

    }
    @Transactional
    @Override
    public void copyFileToHdfs(File file, String filePath, String courseid) {

        /* session 연결 */
        if (!ssh.checksession()) {
            try {
                ssh.connectSSH();
            } catch (JSchException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }


        try {
            String filename = "file";

            /* local input.txt파일을 cluster 서버로 복사 */
            ssh.sendFileToOtherServer(sendFilePath+filename+".txt", receiveFilePath, "a.txt");
            ssh.getSSHResponse("hdfs dfs -rm -r a.txt");
            ssh.getSSHResponse("hdfs dfs -put a.txt");
            /* cluster 속 input.txt파일 hdfs로 복사 */

        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("connect to ssh");
        StringBuilder sb,temp;
        try {

            ssh.getSSHResponse("hdfs dfs -rm -r output2");
            ssh.getSSHResponse("hadoop jar category.jar categorycount a.txt output2");
            String sshResponse = ssh.getSSHResponse("hdfs dfs -cat output2/*");
            System.out.println("sshResponse = " + sshResponse);

            List<Category> categories = new ArrayList<>();

            StringTokenizer st = new StringTokenizer(sshResponse, "\n");
            int line = 1;
            while(st.hasMoreTokens()) {
                String[] split = st.nextToken().split("\t");
                String category = split[0];
                int count = Integer.parseInt(split[1]);
                categories.add(new Category(category, count));
                System.out.println(line++ + " : "+st.nextToken());
            }

            Collections.sort(categories);
            System.out.println(categories.get(0));
            Category category = categories.get(0);
            // mysql 카테고리 넣기
            courseRepository.updateCourseCategoryByid(courseid, category.getCategory());

            log.info("********전송끝 *******");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


    }

    @Override
    public void settingDB() {
        List<Course> courses = courseRepository.findAll();
        for(Course course : courses) {
            String courseid = course.getId();
            updateCourseNearInfo(courseid);
        }
    }


    public class Category implements Comparable<Category>{
        String category;
        int count;

        @Override
        public String toString() {
            return "Category{" +
                    "category='" + category + '\'' +
                    ", count=" + count +
                    '}';
        }

        public Category(String category, int count) {
            this.category = category;
            this.count = count;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        @Override
        public int compareTo(Category o) {
            return o.count - this.count;
        }
    }
}
