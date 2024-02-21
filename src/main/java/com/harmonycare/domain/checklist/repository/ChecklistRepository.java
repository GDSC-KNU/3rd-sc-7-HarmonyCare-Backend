package com.harmonycare.domain.checklist.repository;

import com.harmonycare.domain.checklist.entity.Checklist;
import com.harmonycare.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface ChecklistRepository extends JpaRepository<Checklist, Long> {
    List<Checklist> findByMember(Member member);
    List<Checklist> findByMemberAndCheckTimeBetween(Member member, LocalDateTime startDate, LocalDateTime endDate);
    boolean existByMemberAndTitleAndCreatedDate(Member member, String title, LocalDate localDate);

}
