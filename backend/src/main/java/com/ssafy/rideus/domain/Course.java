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
@Setter
public class Course extends BaseEntity {

    @Id
    @Column(name = "course_id")
    private String id;

    @Column(length = 100)
    private String courseName;

    private Double distance;

    @Column(length = 100)
    private String start;

    @Column(length = 100)
    private String finish;

    private Integer expectedTime;

    private Integer likeCount;
    
    @Column(length = 200)
    private String imageUrl;
    
    @Column(length = 10)
    private String category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "course")
    private List<Review> reviews = new ArrayList<>();

    @OneToMany(mappedBy = "course")
    private List<CourseTag> courseTags = new ArrayList<>();
}
