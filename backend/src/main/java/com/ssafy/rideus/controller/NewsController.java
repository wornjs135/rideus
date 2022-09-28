package com.ssafy.rideus.controller;

import com.ssafy.rideus.dto.news.NewsResponseDto;
import com.ssafy.rideus.service.NewsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RequestMapping("/news")
@RequiredArgsConstructor
@RestController
public class NewsController {

    private final NewsService newsService;

    @GetMapping
    public ResponseEntity<List<NewsResponseDto>> getNews() throws IOException {
        return new ResponseEntity<List<NewsResponseDto>>(newsService.getNews(), HttpStatus.OK);
    }
}
