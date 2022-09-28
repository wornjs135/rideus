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

    private final HadoopService hadoopService;


    @PatchMapping("/category/{courseId}")
    public ResponseEntity<?> mapreduceCategory(@PathVariable String courseId) {

        log.info("Execute hadoop map reduce. courseId :" + courseId);
        hadoopService.mapreduceCategory(courseId);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @PatchMapping("/setting")
    public ResponseEntity<?> settingDB(@RequestParam("newCourseList") List<String> newCourseList) {
        hadoopService.settingDB(newCourseList);
        log.info("end of controller");
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
