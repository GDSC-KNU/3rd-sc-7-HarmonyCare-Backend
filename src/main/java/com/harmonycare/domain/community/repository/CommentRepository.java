package com.harmonycare.domain.community.repository;

import com.harmonycare.domain.community.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
