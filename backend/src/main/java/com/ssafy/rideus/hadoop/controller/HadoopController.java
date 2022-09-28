package com.ssafy.rideus.hadoop.controller;

import com.ssafy.rideus.hadoop.service.HadoopService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/hadoop")
@Slf4j
public class HadoopController {

    private final HadoopService hadoopService;

    @PatchMapping("/hadoop/near/info")
    public ResponseEntity<?> hadoopNearInfo() {
        hadoopService.hadoopNearInfo();
        log.info("end of controller");
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
