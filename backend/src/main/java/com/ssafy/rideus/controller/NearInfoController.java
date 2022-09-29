package com.ssafy.rideus.controller;

import com.ssafy.rideus.domain.collection.NearInfo;
import com.ssafy.rideus.service.NearInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/near")
@RequiredArgsConstructor
@RestController
public class NearInfoController {

    private final NearInfoService nearInfoService;

    @GetMapping("/find")
    public ResponseEntity<List<NearInfo>> findNearInfoByCategory( @RequestParam("courseId") String courseId, @RequestParam("category") String category) {
        // 주행 코스 id
        List<NearInfo> courseNearInfos = nearInfoService.findNearInfoByCategory(courseId, category);
        return new ResponseEntity<List<NearInfo>>(courseNearInfos, HttpStatus.OK);
    }
}
