package com.ssafy.rideus.service;

import com.ssafy.rideus.domain.NearInfo;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

public interface NearInfoService {

    List<NearInfo> findAllNearInfo(String courseId);
}
