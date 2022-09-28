package com.ssafy.rideus.controller;

import com.ssafy.rideus.config.security.auth.CustomUserDetails;
import com.ssafy.rideus.dto.bookmark.response.CreateBookmarkResponse;
import com.ssafy.rideus.service.BookmarkService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequiredArgsConstructor
@RequestMapping("/bookmark")
public class BookmarkController {

    private final BookmarkService bookmarkService;

    // 북마크 생성
    @PostMapping("/course/{courseId}")
    public ResponseEntity<CreateBookmarkResponse> createBookmark(@ApiIgnore @AuthenticationPrincipal CustomUserDetails member, @PathVariable String courseId) {
        return ResponseEntity.status(HttpStatus.CREATED).body(bookmarkService.createBookmark(member.getId(), courseId));
    }

    // 북마크 해제
    @DeleteMapping("/{bookmarkId}")
    public ResponseEntity deleteBookmark(@ApiIgnore @AuthenticationPrincipal CustomUserDetails member, @PathVariable Long bookmarkId) {
        bookmarkService.deleteBookmark(member.getId(), bookmarkId);

        return ResponseEntity.ok().build();
    }
}
