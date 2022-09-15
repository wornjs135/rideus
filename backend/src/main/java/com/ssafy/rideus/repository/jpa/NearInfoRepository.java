package com.ssafy.rideus.repository.jpa;

import com.ssafy.rideus.repository.jpa.domain.NearInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NearInfoRepository extends JpaRepository<NearInfo, Long> {
}
