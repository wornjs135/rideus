package com.ssafy.rideus.domain;

import com.ssafy.rideus.domain.base.BaseEntity;
import com.ssafy.rideus.dto.review.ReviewRequestDto;
import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.Optional;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Builder
public class Review extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private Long id;

    private Integer score;

    @Column(length = 200)
    private String content;

    private Integer likeCount;

    @Column(length = 200)
    private String imageUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id")
    private Course course;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "record_id")
    private Record record;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ride_room_id")
    private RideRoom rideRoom;

    @OneToMany(mappedBy = "review")
    private List<ReviewTag> reviewTags;

    public void decreaseLike() {
        this.likeCount--;
    }

    public void increaseLike() {
        this.likeCount++;
    }

    public static Review createReview(ReviewRequestDto reviewRequestDto, Member member, String imageUrl, Record record) {
        Review review = new Review();
        review.score = reviewRequestDto.getScore();
        review.content = reviewRequestDto.getContent();
        review.likeCount = 0;
        review.imageUrl = imageUrl;
        review.member = member;
        review.course = record.getCourse();
        review.record = record;
        review.rideRoom = record.getRideRoom();

        return review;
    }

}
