package com.harmonycare.domain.community.repository;

import com.harmonycare.domain.community.entity.Comment;
import com.harmonycare.domain.community.entity.Community;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommunityRepository extends JpaRepository<Community, Long> {
}
