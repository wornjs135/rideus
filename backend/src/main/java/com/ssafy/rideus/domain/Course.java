package com.ssafy.rideus.domain;

import com.ssafy.rideus.domain.base.BaseEntity;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class Course extends BaseEntity {

    @Id
    @Column(name = "course_id")
    private String id;

    @Column(length = 100)
    private String courseName;

    @Column(length = 100)
    private String distance;

    @Column(length = 100)
    private String start;

    @Column(length = 100)
    private String finish;

    @Column(length = 100)
    private String expectedTime;

    private Integer likeCount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "course")
    private List<Review> reviews = new ArrayList<>();

    @OneToMany(mappedBy = "course")
    private List<CourseTag> courseTags = new ArrayList<>();
}
