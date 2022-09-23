package com.ssafy.rideus.service;


import com.jcraft.jsch.JSchException;
import com.ssafy.rideus.hadoop.Controller.SSHUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)

public class HadoopServiceImpl implements HadoopService{


    @Autowired
    SSHUtils ssh;


    //    private String sendFilePath = "/home/ubuntu/mysqltablefile/"; // ubuntu ?
//    //	private String sendFilePath = "C:\\SSAFY\\sshtest\\";
//    private String receiveFilePath = "/home/j5d205/receive/"; // hadoop path
    private String hadoopdefault = "/usr/local/hadoop/bin/"; // hadoop


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

        System.out.println("connect to ssh");
        StringBuilder sb,temp;

        try {
//            ssh.sendFileToOtherServer(sendFilePath+p+".txt", receiveFilePath, p + ".txt");
//            String f = "/usr/local/hadoop/bin/hadoop fs -put /home/j5d205/receive/" + p + ".txt "+ "Set/"+p+".txt";
//            ssh.getSSHResponse(f);
//            ssh.getSSHResponse("/usr/local/hadoop/bin/hadoop fs -rm -r Setoutput;/usr/local/hadoop/bin/hadoop fs -mkdir Setoutput");
////				 "/home/j5d205/mapReducer/S05P21D205/mapReducerMaven/ssafy.jar"
//            String cmd =hadoopdefault+"hadoop jar /home/j5d205/mapReducer/S05P21D205/mapReducerMaven/ssafy.jar setjoin 0.3 Set/"+p+".txt Setoutput";
//            ssh.getSSHResponse(cmd);

            System.out.println("before ssh response");

            String result = ssh.getSSHResponse(hadoopdefault+"hadoop dfs -cat result/*");
            System.out.println("result = " + result);
//            StringTokenizer st = new StringTokenizer(result,"\r\n");
//            StringTokenizer ss,tempst;

//            while(st.hasMoreTokens()) {
//
//                // 카테고리, 개수 분리
//
//                // 많은 순서로 sort
//
//                // 1순위 MySQL에 저장
//
//
////                ss = new StringTokenizer(st.nextToken(),"\t");
////                psas = new ProductSellArticleSimiler();
////                tempst = new StringTokenizer(ss.nextToken(),"|");
////                psas.setArticleA(new ProductSellList(Long.parseLong(tempst.nextToken()),tempst.nextToken()));
////
////
////                tempst = new StringTokenizer(ss.nextToken(),"|");
////                psas.setArticleB(new ProductSellList(Long.parseLong(tempst.nextToken()), tempst.nextToken()));
////                similarityScore =Double.parseDouble(ss.nextToken());
////                if(similarityScore<=80&&similarityScore>=10) {
////                    psas.setSimilarity(similarityScore);
////                    insertProductSellArticleSimiler(psas);
////                }
//
//            }

//            log.info("********전송끝 *******");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


    }
}
