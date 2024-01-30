package com.harmonycare.domain.baby.repository;

import com.harmonycare.domain.baby.entity.Baby;
import com.harmonycare.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BabyRepository extends JpaRepository<Baby, Long> {
    List<Baby> findAllByMember(Member member);
}
