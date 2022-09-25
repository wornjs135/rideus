package com.ssafy.rideus.controller;

import com.ssafy.rideus.repository.jpa.CourseRepository;
import com.ssafy.rideus.service.HadoopService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/hadoop")
@Slf4j
public class HadoopController {


    @Autowired
    HadoopService hadoopService;


    @PostMapping("category/{courseid}")
    public ResponseEntity<?> mapreduceCategory(@PathVariable String courseid) {

        log.info("Execute hadoop map reduce. courseid :" + courseid);
        hadoopService.mapreduceCategory(courseid);
        return new ResponseEntity<>(HttpStatus.OK);
    }




    @GetMapping("/parse/{courseid}")
    public ResponseEntity<?> parseNearinfo(@PathVariable String courseid) {


        hadoopService.mapreduceCategory(courseid);
        System.out.println("end of cotroller");
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/setting")
    public ResponseEntity<?> settingDB() {
        hadoopService.settingDB();
        System.out.println("end of cotroller");
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
