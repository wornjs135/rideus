package com.ssafy.rideus.repository.jpa;

import com.ssafy.rideus.domain.CheckPoint;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CheckPointRepository extends JpaRepository<CheckPoint, Long> {
}
