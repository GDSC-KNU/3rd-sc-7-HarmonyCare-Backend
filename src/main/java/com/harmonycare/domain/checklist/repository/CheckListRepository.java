package com.harmonycare.domain.checklist.repository;

import com.harmonycare.domain.checklist.entity.CheckList;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CheckListRepository extends JpaRepository<CheckList, Long> {

}
