package com.ssafy.rideus.repository.query;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;

import com.ssafy.rideus.dto.record.response.RecordTotalResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.ssafy.rideus.domain.QRecord.record;


@Repository
@RequiredArgsConstructor
public class MemberQueryRepository {
    private final JPAQueryFactory queryFactory;


    public RecordTotalResponse searchTotalRecord(Long id) {
        return queryFactory.select(
                        Projections.constructor(
                                RecordTotalResponse.class,
                                record.recordDistance.sum(),
                                record.recordTime.sum()
                        )
                )
                .from(record)
                .where(record.member.id.eq(id))
                .fetchOne();
    }
}
