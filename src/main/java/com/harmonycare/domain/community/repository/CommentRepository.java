package com.harmonycare.domain.community.repository;

import com.harmonycare.domain.community.entity.Comment;
import com.harmonycare.domain.community.entity.Community;
import com.harmonycare.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByCommunity(Community community);
}
