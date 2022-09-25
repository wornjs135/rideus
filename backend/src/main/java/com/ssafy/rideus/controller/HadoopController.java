package com.ssafy.rideus.controller;

import com.ssafy.rideus.repository.jpa.CourseRepository;
import com.ssafy.rideus.service.HadoopService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/hadoop")

public class HadoopController {


    @Autowired
    HadoopService hadoopService;


    @PostMapping("/hadoop/{courseid}")
    public ResponseEntity<?> hadoop(@PathVariable String courseid) {


        hadoopService.updateCourseNearInfo(courseid);
//        hadoopService.saveCategoryToCourse(courseid);
        System.out.println("end of cotroller");
        return new ResponseEntity<>(HttpStatus.OK);
    }



    @GetMapping("/ssh/{courseid}")
    public ResponseEntity<?> runMapReduce(@PathVariable String courseid) {


        hadoopService.saveCategoryToCourse(courseid);
        System.out.println("end of cotroller");
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/parse/{courseid}")
    public ResponseEntity<?> parseNearinfo(@PathVariable String courseid) {


        hadoopService.updateCourseNearInfo(courseid);
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
