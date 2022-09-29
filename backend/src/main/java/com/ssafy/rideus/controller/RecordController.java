package com.ssafy.rideus.controller;

import com.ssafy.rideus.dto.record.response.RecordInfoResponse;
import com.ssafy.rideus.service.RecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/record")
public class RecordController {

    private final RecordService recordService;

    @GetMapping("/{recordId}")
    public ResponseEntity<RecordInfoResponse> getRecordInfo(@PathVariable String recordId) {
        return ResponseEntity.ok(recordService.getRecordInfo(recordId));
    }
}
