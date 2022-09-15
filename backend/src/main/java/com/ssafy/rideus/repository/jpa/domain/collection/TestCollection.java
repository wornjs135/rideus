package com.ssafy.rideus.repository.jpa.domain.collection;

import com.ssafy.rideus.repository.jpa.domain.base.Point;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.util.List;

@Document(collection = "test_connection")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@ToString
public class TestCollection {

    @Id
    private String id;
    private int age;
    private String name;
    private List<String> favorite;
    private List<Point> points;

    public static TestCollection create(int age, String name, List<String> favorite, List<Point> points) {
        TestCollection testCollection = new TestCollection();
        testCollection.age = age;
        testCollection.name = name;
        testCollection.favorite = favorite;
        testCollection.points = points;

        return testCollection;
    }

}
