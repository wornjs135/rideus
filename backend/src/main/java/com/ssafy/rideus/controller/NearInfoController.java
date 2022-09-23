package com.ssafy.rideus.controller;

import com.ssafy.rideus.domain.collection.NearInfo;
import com.ssafy.rideus.service.NearInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/near")
@RequiredArgsConstructor
@RestController
public class NearInfoController {

    @Autowired
    NearInfoService nearInfoService;

    @GetMapping("/save")
    public ResponseEntity<?> saveNearInfo(@RequestParam("courseId") String courseId) {
        // 주행 코스 id
        // 코스 주변 정보 리스트
        List<NearInfo> nearInfos = nearInfoService.saveNearInfo(courseId);

        return new ResponseEntity<List<NearInfo>>(nearInfos, HttpStatus.OK);
    }

    @GetMapping("/find")
    public ResponseEntity<?> findNearInfo(@RequestParam("courseId") String courseId) {
        // 주행 코스 id
        List<NearInfo> nearInfos = nearInfoService.findNearInfo(courseId);

        return new ResponseEntity<List<NearInfo>>(nearInfos, HttpStatus.OK);
    }

}
