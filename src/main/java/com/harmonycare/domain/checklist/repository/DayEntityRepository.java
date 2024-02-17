package com.harmonycare.domain.checklist.repository;

import com.harmonycare.domain.checklist.entity.DayEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DayEntityRepository extends JpaRepository<DayEntity, Long> {
}
