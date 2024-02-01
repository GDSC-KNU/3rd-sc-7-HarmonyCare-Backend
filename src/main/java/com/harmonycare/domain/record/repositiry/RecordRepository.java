package com.harmonycare.domain.record.repositiry;

import com.harmonycare.domain.record.entity.Record;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecordRepository extends JpaRepository<Record, Long> {
}
