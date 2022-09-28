package com.ssafy.rideus.controller;

import com.ssafy.rideus.repository.jpa.CourseRepository;
import com.ssafy.rideus.service.HadoopService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/hadoop")
@Slf4j
public class HadoopController {


    @Autowired
    HadoopService hadoopService;


    @PostMapping("/category/{courseid}")
    public ResponseEntity<?> mapreduceCategory(@PathVariable String courseid) {

        log.info("Execute hadoop map reduce. courseid :" + courseid);
        hadoopService.mapreduceCategory(courseid);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @GetMapping("/setting")
    public ResponseEntity<?> settingDB(@RequestParam("newCourseList") List<String> newCourseList) {
        hadoopService.settingDB(newCourseList);
        System.out.println("end of cotroller");
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
