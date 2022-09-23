package com.ssafy.rideus.service;


import com.jcraft.jsch.JSchException;
import com.ssafy.rideus.domain.Course;
import com.ssafy.rideus.hadoop.Controller.SSHUtils;
import com.ssafy.rideus.repository.jpa.CourseRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)

public class HadoopServiceImpl implements HadoopService{


    @Autowired
    SSHUtils ssh;
    @Autowired
    CourseRepository courseRepository;

    //    private String sendFilePath = "/home/ubuntu/mysqltablefile/"; // ubuntu ?
//    //	private String sendFilePath = "C:\\SSAFY\\sshtest\\";
//    private String receiveFilePath = "/home/j5d205/receive/"; // hadoop path
    private String hadoopdefault = "/usr/local/hadoop/bin/"; // hadoop
    private String hadoopdefault2 = "/home/j7a603/"; // hadoop


    @Transactional
    @Override
    public void saveCategoryToCourse(String courseid) {

        System.out.println("save category to course");
        // session 연결 상태 아님
        if (!ssh.checksession()) {
            try {
                ssh.connectSSH();
            } catch (JSchException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        /* 킹 갓 제너럴 인수 팀장s
        그놈의 a!!!!!!!!!!!!!

        배치파일 돌린다.

        -> 새로운 코스 정리한다.
        {
        1개 코스정보 category뽑기

        1. ** mongodb 코스별 주변정보 카테고리 -> input파일로 cluster에 넣는 작업
        코스 id가져오면
        주변정보 정리해서
        카테고리만 txt파일로 넣는다.
        이거를 cluster로 복사
        --------------------------------------------------------------------
        코스아이디 받아오면 수정한다~~
        2. input파일을 mapreduce돌리는거 까지 성공
        3. mapreduce결과 파일을 정렬해서 1개 카테고리 골라갖고 mysql 입력 완료
        }
         */

        System.out.println("connect to ssh");
        StringBuilder sb,temp;

        try {

            ssh.getSSHResponse("hdfs dfs -rm -r output");
            ssh.getSSHResponse("hadoop jar category.jar categorycount a.txt output");
            String sshResponse = ssh.getSSHResponse("hdfs dfs -cat output/*");
            System.out.println("sshResponse = " + sshResponse);




            // 미
            // 쳤
            // 다
            /*
                sshResponse = 공중화장실	20
                관광명소	48
                분류	1
                음식점	22
                문화시설	15
                자전거정비	16
                카페	117
                편의점	56
             */

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

            // 짝 짝 짝 짝 ~~~~~~~~~~ 성 to the 공
            // 짝 짝 짝 짝 ~~~~~~~~~~ 성 to the 공
            // 짝 짝 짝 짝 ~~~~~~~~~~ 성 to the 공
            // 짝 짝 짝 짝 ~~~~~~~~~~ 성 to the 공
            // 짝 짝 짝 짝 ~~~~~~~~~~ 성 to the 공
            // 짝 짝 짝 짝 ~~~~~~~~~~ 성 to the 공
            // 짝 짝 짝 짝 ~~~~~~~~~~ 성 to the 공
            // 짝 짝 짝 짝 ~~~~~~~~~~ 성 to the 공
            // 짝 짝 짝 짝 ~~~~~~~~~~ 성 to the 공





            log.info("********전송끝 *******");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
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
