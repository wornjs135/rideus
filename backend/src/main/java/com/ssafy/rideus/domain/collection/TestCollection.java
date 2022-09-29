package com.ssafy.rideus.domain.collection;

import com.ssafy.rideus.domain.base.Coordinate;
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
    private List<Coordinate> coordinates;

    public static TestCollection create(int age, String name, List<String> favorite, List<Coordinate> coordinates) {
        TestCollection testCollection = new TestCollection();
        testCollection.age = age;
        testCollection.name = name;
        testCollection.favorite = favorite;
        testCollection.coordinates = coordinates;

        return testCollection;
    }

}
