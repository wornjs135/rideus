package com.ssafy.rideus.controller;

import com.ssafy.rideus.domain.base.Point;
import com.ssafy.rideus.domain.collection.TestCollection;
import com.ssafy.rideus.repository.jpa.MemberRepository;
import com.ssafy.rideus.repository.mongo.TestCollectionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class TestMongoController {

    private final TestCollectionRepository testCollectionRepository;
    private final MemberRepository memberRepository;

    @GetMapping("/test")
    public ResponseEntity test() {

        System.out.println(testCollectionRepository.findAll());
        System.out.println(memberRepository.findAll());


        return ResponseEntity.ok().build();
    }

    @PostMapping("/test")
    public ResponseEntity test_create() {
        List<String> favorite = new ArrayList<>();
        favorite.add("ddd");
        favorite.add("ccc");
        favorite.add("eee");

        List<Point> points = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Point point = new Point(i, i);
            points.add(point);
        }

        System.out.println(testCollectionRepository.save(TestCollection.create(10, "박재권", favorite, points)));

        return ResponseEntity.ok().build();
    }

    @GetMapping("/test/jenkins")
    public ResponseEntity testJenkins() {
        System.out.println("젠킨스 빌드 됐나요");

        return ResponseEntity.ok("젠킨스 빌드 완료?");
    }
}
