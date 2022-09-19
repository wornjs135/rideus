package com.ssafy.rideus.controller;

import com.ssafy.rideus.domain.NearInfo;
import com.ssafy.rideus.service.NearInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.List;

@RequestMapping("/near")
@RequiredArgsConstructor
@RestController
public class NearInfoController {

    @Autowired
    NearInfoService nearInfoService;

    @GetMapping("/{courseid}")
    public ResponseEntity<List<NearInfo>> findAllNearInfo(@PathVariable String courseId) {
        // 코스 주변 정보 리스트
        List<NearInfo> nearInfos = nearInfoService.findAllNearInfo(courseId);

        return new ResponseEntity<>(nearInfos, HttpStatus.OK);
    }


}
