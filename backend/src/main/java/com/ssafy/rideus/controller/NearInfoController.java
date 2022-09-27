package com.ssafy.rideus.controller;

import com.ssafy.rideus.domain.collection.NearInfo;
import com.ssafy.rideus.service.NearInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/near")
@RequiredArgsConstructor
@RestController
public class NearInfoController {

    private final NearInfoService nearInfoService;

    @GetMapping("/save/{courseid}")
    public ResponseEntity<?> saveNearInfo(@PathVariable String courseId) {
        // 주행 코스 id
        long course = Long.parseLong(courseId);
        // 코스 주변 정보 리스트
        List<NearInfo> nearInfos = nearInfoService.saveNearInfo(course);

        return new ResponseEntity<List<NearInfo>>(nearInfos, HttpStatus.OK);
    }

    @GetMapping("/find/{courseid}")
    public ResponseEntity<?> findNearInfo(@PathVariable String courseId) {
        // 주행 코스 id
        List<NearInfo> nearInfos = nearInfoService.findNearInfo(Long.parseLong(courseId));

        return new ResponseEntity<List<NearInfo>>(nearInfos, HttpStatus.OK);
    }

}
