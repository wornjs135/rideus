package com.ssafy.rideus.domain;

import com.ssafy.rideus.domain.base.BaseEntity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class Course extends BaseEntity {

    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "course_id", length = 50)
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
}
