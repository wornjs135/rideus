package com.ssafy.rideus.service;

import com.ssafy.rideus.common.exception.NotFoundException;
import com.ssafy.rideus.common.exception.NotMatchException;
import com.ssafy.rideus.domain.Bookmark;
import com.ssafy.rideus.domain.Course;
import com.ssafy.rideus.domain.Member;
import com.ssafy.rideus.dto.bookmark.response.BookmarkCourseRes;
import com.ssafy.rideus.dto.bookmark.response.CreateBookmarkResponse;
import com.ssafy.rideus.repository.jpa.BookmarkRepository;
import com.ssafy.rideus.repository.jpa.CourseRepository;
import com.ssafy.rideus.repository.jpa.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

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
        findCourse.addBookmarkCount();

        return CreateBookmarkResponse.from(saveBookmark.getId());
    }

    @Transactional
    public void deleteBookmark(Long memberId, Long bookmarkId) {
        Bookmark findBookmark = bookmarkRepository.findById(bookmarkId)
                .orElseThrow(() -> new NotFoundException(BOOKMARK_NOT_FOUND));
        if (findBookmark.getMember().getId() != memberId) {
            throw new NotMatchException(MEMBER_BOOKMARK_NOT_MATCH);
        }

        findBookmark.getCourse().minusBookmarkCount();
        bookmarkRepository.delete(findBookmark);
    }

    public List<BookmarkCourseRes> findBookmarkedCourses(Long memberId) {
        List<Bookmark> top5ByMemberIdOrderByIdDesc = bookmarkRepository.findTop5ByMemberIdOrderByIdDesc(memberId);
        List<BookmarkCourseRes> bookmarkCourses = new ArrayList<>();
        top5ByMemberIdOrderByIdDesc.forEach(bookmark -> {

            bookmarkCourses.add(BookmarkCourseRes.of(bookmark.getCourse()));
        });

        return bookmarkCourses;
    }
}
