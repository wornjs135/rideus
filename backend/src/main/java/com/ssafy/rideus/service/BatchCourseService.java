package com.ssafy.rideus.service;

import com.ssafy.rideus.common.exception.NotFoundException;
import com.ssafy.rideus.domain.*;
import com.ssafy.rideus.dto.course.common.CourseReviewTagTop5DtoInterface;
import com.ssafy.rideus.dto.member.common.MemberReviewTagTop5DtoInterface;
import com.ssafy.rideus.repository.jpa.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.ssafy.rideus.common.exception.NotFoundException.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BatchCourseService {

    private final CourseRepository courseRepository;
    private final TagRepository tagRepository;
    private final CourseTagRepository courseTagRepository;
    private final MemberRepository memberRepository;
    private final MemberTagRepository memberTagRepository;


    @Transactional
    public void setCourseReviewTop5() {
        List<CourseReviewTagTop5DtoInterface> courseReviewTagTop5 = courseRepository.getCourseReviewTagTop5();

        courseTagRepository.deleteAllInBatch();
        for (CourseReviewTagTop5DtoInterface t : courseReviewTagTop5) {
            Course findCourse = courseRepository.findById(t.getCourseId())
                    .orElseThrow(() -> new NotFoundException(COURSE_NOT_FOUND));
            Tag findTag = tagRepository.findById(t.getTagId())
                    .orElseThrow(() -> new NotFoundException(TAG_NOT_FOUND));

            courseTagRepository.save(CourseTag.create(findCourse, findTag));
        }
    }

    @Transactional
    public void setMemberReviewTop5() {
        List<MemberReviewTagTop5DtoInterface> memberReviewTagTop5 = memberRepository.getMemberReviewTagTop5();

        memberTagRepository.deleteAllInBatch();
        for (MemberReviewTagTop5DtoInterface m : memberReviewTagTop5) {
            Member findMember = memberRepository.findById(m.getMemberId())
                    .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND));
            Tag findTag = tagRepository.findById(m.getTagId())
                    .orElseThrow(() -> new NotFoundException(TAG_NOT_FOUND));

            memberTagRepository.save(MemberTag.create(findMember, findTag));
        }
    }
}
