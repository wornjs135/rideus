package com.ssafy.rideus.repository.jpa;

import com.ssafy.rideus.domain.Bookmark;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {
    public List<Bookmark> findTop5ByMemberIdOrderByIdDesc(Long memberId);

}
