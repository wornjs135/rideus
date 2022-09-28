package com.ssafy.rideus.dto.bookmark.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateBookmarkResponse {

    private Long bookmarkId;

    public static CreateBookmarkResponse from(Long bookmarkId) {
        CreateBookmarkResponse createBookmarkResponse = new CreateBookmarkResponse();
        createBookmarkResponse.bookmarkId = bookmarkId;

        return createBookmarkResponse;
    }
}
