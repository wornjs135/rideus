package com.ssafy.rideus.controller;

import com.ssafy.rideus.service.HadoopService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/hadoop")

public class HadoopController {


    @Autowired
    HadoopService hadoopService;

    @GetMapping("/ssh")
    public ResponseEntity<?> sshResponse() {

        System.out.println("ssh response controller");
        hadoopService.saveCategoryToCourse("632d7434e0fd22694437bec9");
        System.out.println("end of cotroller");
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
