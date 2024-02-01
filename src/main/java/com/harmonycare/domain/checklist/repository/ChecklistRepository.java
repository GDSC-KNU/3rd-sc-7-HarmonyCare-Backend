package com.harmonycare.domain.checklist.repository;

import com.harmonycare.domain.checklist.entity.Checklist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChecklistRepository extends JpaRepository<Checklist, Long> {

}
