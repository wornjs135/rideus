package com.ssafy.rideus.repository.jpa;

import com.ssafy.rideus.repository.jpa.domain.CheckPoint;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CheckPointRepository extends JpaRepository<CheckPoint, Long> {
}
