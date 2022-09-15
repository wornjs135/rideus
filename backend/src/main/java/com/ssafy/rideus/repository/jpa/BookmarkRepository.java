package com.ssafy.rideus.repository.jpa;

import com.ssafy.rideus.repository.jpa.domain.Bookmark;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {
}
