package com.ssafy.rideus.repository.jpa.domain;

import com.ssafy.rideus.repository.jpa.domain.base.BaseEntity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class NearInfo extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "nearinfo_id")
    private Long id;

    @Column(length = 100)
    private String nearinfoName;

    @Column(length = 30)
    private String nearinfoCategory;

    @Column(length = 80)
    private String nearinfoUrl;

    @Column(length = 200)
    private String nearinfoAddress;

    @Column(length = 30)
    private String nearinfoTel;

    @Column(length = 30)
    private String nearinfoLat;

    @Column(length = 30)
    private String nearinfoLng;
}
