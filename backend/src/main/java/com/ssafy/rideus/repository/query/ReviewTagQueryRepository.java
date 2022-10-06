package com.ssafy.rideus.repository.query;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.rideus.domain.QReviewTag;
import com.ssafy.rideus.domain.QTag;
import com.ssafy.rideus.domain.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.ssafy.rideus.domain.QReviewTag.reviewTag;
import static com.ssafy.rideus.domain.QTag.tag;

@Repository
@RequiredArgsConstructor
public class ReviewTagQueryRepository {

    private final JPAQueryFactory queryFactory;

    public List<Tag> getPopularityTag() {
        return queryFactory
                .select(reviewTag.tag)
                .from(reviewTag)
                .join(reviewTag.tag, tag)
                .groupBy(reviewTag.tag)
                .orderBy(tag.count().desc())
                .limit(5)
                .fetch();
    }
}
