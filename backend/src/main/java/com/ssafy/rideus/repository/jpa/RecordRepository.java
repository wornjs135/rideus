package com.ssafy.rideus.repository.jpa;

import com.ssafy.rideus.domain.Record;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecordRepository extends JpaRepository<Record, String> {
}
