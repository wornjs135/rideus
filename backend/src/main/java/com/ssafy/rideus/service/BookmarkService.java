package com.ssafy.rideus.service;

import com.ssafy.rideus.common.exception.NotFoundException;
import com.ssafy.rideus.common.exception.NotMatchException;
import com.ssafy.rideus.domain.Bookmark;
import com.ssafy.rideus.domain.Course;
import com.ssafy.rideus.domain.Member;
import com.ssafy.rideus.dto.bookmark.response.CreateBookmarkResponse;
import com.ssafy.rideus.repository.jpa.BookmarkRepository;
import com.ssafy.rideus.repository.jpa.CourseRepository;
import com.ssafy.rideus.repository.jpa.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.ssafy.rideus.common.exception.NotFoundException.*;
import static com.ssafy.rideus.common.exception.NotMatchException.MEMBER_BOOKMARK_NOT_MATCH;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BookmarkService {

    private final BookmarkRepository bookmarkRepository;
    private final MemberRepository memberRepository;
    private final CourseRepository courseRepository;

    @Transactional
    public CreateBookmarkResponse createBookmark(Long memberId, String courseId) {
        Member findMember = memberRepository.findById(memberId)
                .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND));
        Course findCourse = courseRepository.findById(courseId)
                .orElseThrow(() -> new NotFoundException(COURSE_NOT_FOUND));

        Bookmark saveBookmark = bookmarkRepository.save(Bookmark.create(findMember, findCourse));

        return CreateBookmarkResponse.from(saveBookmark.getId());
    }

    @Transactional
    public void deleteBookmark(Long memberId, Long bookmarkId) {
        Bookmark findBookmark = bookmarkRepository.findById(bookmarkId)
                .orElseThrow(() -> new NotFoundException(BOOKMARK_NOT_FOUND));
        if (findBookmark.getId() != memberId) {
            throw new NotMatchException(MEMBER_BOOKMARK_NOT_MATCH);
        }

        bookmarkRepository.delete(findBookmark);
    }
}
