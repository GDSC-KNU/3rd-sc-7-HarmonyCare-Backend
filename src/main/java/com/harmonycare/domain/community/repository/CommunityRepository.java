package com.harmonycare.domain.community.repository;

import com.harmonycare.domain.community.entity.Community;
import com.harmonycare.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommunityRepository extends JpaRepository<Community, Long> {
    List<Community> findAllByMember(Member member);
}
