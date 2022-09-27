package com.ssafy.rideus.controller;

import com.ssafy.rideus.domain.collection.NearInfo;
import com.ssafy.rideus.service.NearInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.lang.model.SourceVersion;
import java.util.List;

@RequestMapping("/near")
@RequiredArgsConstructor
@RestController
public class NearInfoController {

    @Autowired
    NearInfoService nearInfoService;

    @GetMapping("/save/{courseId}")
    public ResponseEntity<?> saveNearInfo(@PathVariable String courseId) {
        // 주행 코스 id
        // 코스 주변 정보 리스트
        List<NearInfo> nearInfos = nearInfoService.saveNearInfo(courseId);

        return new ResponseEntity<List<NearInfo>>(nearInfos, HttpStatus.OK);
    }

    @GetMapping("/find")
    public ResponseEntity<?> findNearinfoByCategories( @RequestParam("courseid") String courseid, @RequestParam("categories") List<String> categories) {
        // 주행 코스 id
        List<NearInfo> courseNearinfos = nearInfoService.findNearinfoByCategories(courseid, categories);
        return new ResponseEntity<List<NearInfo>>(courseNearinfos, HttpStatus.OK);
    }


    @GetMapping("/findAll")
    public ResponseEntity<?> findAllNearInfo() {

        System.out.println("near info controller, find all near info");
        // 주행 코스 id
        List<NearInfo> allNearInfo = nearInfoService.findAllNearInfo();
        System.out.println("allNearInfo = " + allNearInfo.size());
        return new ResponseEntity<List<NearInfo>>(allNearInfo,HttpStatus.OK);
    }
}
