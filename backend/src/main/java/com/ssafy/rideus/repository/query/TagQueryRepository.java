package com.ssafy.rideus.repository.query;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.rideus.domain.Tag;
import com.ssafy.rideus.dto.tag.response.MemberTagResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.ssafy.rideus.domain.QReview.review;
import static com.ssafy.rideus.domain.QReviewTag.reviewTag;
import static com.ssafy.rideus.domain.QTag.tag;

@Repository
@RequiredArgsConstructor
public class TagQueryRepository {
    private final JPAQueryFactory queryFactory;



//    select t.tag_name, r.tag_id, count(r.tag_id)
//    from review_tag r join tag t
//    on r.tag_id = t.tag_id
//    where r.review_id in (
//            select review_id
//        from review
//                where member_id = :member_id)
//    group by r.tag_id;

    public List<MemberTagResponse> searchMemberTag(Long memberId) {
        return queryFactory.select(Projections.constructor(
                        MemberTagResponse.class,
                tag.tagName, reviewTag.tag.id, reviewTag.tag.id.count()))
                .from(reviewTag)
                .leftJoin(tag)
                .on(reviewTag.tag.id.eq(tag.id))
                .where(reviewTag.tag.id
                        .in(queryFactory.select(reviewTag.id)
                                .from(review)
                                .where(review.member.id.eq(memberId)))
                        ).groupBy(reviewTag.tag.id)
                .orderBy(reviewTag.tag.id.count().desc())
                .fetch();
    }
}
