package com.ssafy.rideus.domain;

import com.ssafy.rideus.domain.base.BaseEntity;
import lombok.*;

import javax.persistence.*;
import java.util.Map;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
public class MemberTag extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tag_id")
    private Tag tag;

    public static MemberTag create(Member member, Tag tag) {
        MemberTag memberTag = new MemberTag();
        memberTag.member = member;
        memberTag.tag = tag;

        return memberTag;
    }
}

