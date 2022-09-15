package com.ssafy.rideus.repository.jpa;

import com.ssafy.rideus.domain.Record;
import com.ssafy.rideus.dto.record.response.RecordTotalResponse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecordRepository extends JpaRepository<Record, Long> {
    public List<Record> findTop5RecordsByMemberIdOOrderByIdDesc(Long id);

}
