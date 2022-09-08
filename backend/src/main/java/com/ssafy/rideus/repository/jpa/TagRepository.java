package com.ssafy.rideus.repository.jpa;

import com.ssafy.rideus.domain.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, Long> {
}
