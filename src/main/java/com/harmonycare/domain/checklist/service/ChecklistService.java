package com.harmonycare.domain.checklist.service;

import com.harmonycare.domain.checklist.dto.request.ChecklistSaveRequest;
import com.harmonycare.domain.checklist.dto.request.ChecklistUpdateRequest;
import com.harmonycare.domain.checklist.dto.response.ChecklistReadResponse;
import com.harmonycare.domain.checklist.entity.Checklist;
import com.harmonycare.domain.checklist.entity.Day;
import com.harmonycare.domain.checklist.entity.DayEntity;
import com.harmonycare.domain.checklist.exception.ChecklistErrorCode;
import com.harmonycare.domain.checklist.repository.ChecklistRepository;
import com.harmonycare.domain.checklist.repository.DayEntityRepository;
import com.harmonycare.domain.member.entity.Member;
import com.harmonycare.global.exception.GlobalException;
import com.harmonycare.global.util.DateTimeUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ChecklistService {
    private final DayEntityRepository dayEntityRepository;
    private final ChecklistRepository checkListRepository;

    /**
     * CREATE
     */
    @Transactional
    public Long saveCheckList(ChecklistSaveRequest requestBody, Member member) {
        LocalDateTime resultDateTime = DateTimeUtil.stringToLocalDateTime(requestBody.checkTime());

        Checklist checklist = checkListRepository.save(Checklist.builder()
                .title(requestBody.title())
                .checkTime(resultDateTime)
                .isCheck(false)
                .member(member)
                .build());


        List<DayEntity> dayEntityList = Day.dayListToDayEntityList(requestBody.days(), checklist);
        dayEntityRepository.saveAll(dayEntityList);

        return checklist.getId();
    }

    /**
     * READ
     */
    public ChecklistReadResponse readCheckList(Long checklistId) {
        Checklist checklist = checkListRepository.findById(checklistId)
                .orElseThrow(() -> new GlobalException(ChecklistErrorCode.CHECKLIST_NOT_FOUND));

        return ChecklistReadResponse.from(checklist);
    }

    /**
     * UPDATE
     */
    @Transactional
    public Long updateCheckList(ChecklistUpdateRequest requestBody, Long oldCheckListId) {
        Checklist checklist = checkListRepository.findById(oldCheckListId)
                .orElseThrow(() -> new GlobalException(ChecklistErrorCode.CHECKLIST_NOT_FOUND));

        checklist.update(requestBody);
        checkListRepository.save(checklist);

        return checklist.getId();
    }

    /**
     * DELETE
     */
    @Transactional
    public void deleteCheckList(Long deleteCheckId) {
        Checklist deleteChecklist = checkListRepository.findById(deleteCheckId)
                .orElseThrow(() -> new GlobalException(ChecklistErrorCode.CHECKLIST_NOT_FOUND));

        checkListRepository.delete(deleteChecklist);
    }

    public List<ChecklistReadResponse> readMyChecklist(Member member) {
        List<Checklist> checklistList = checkListRepository.findByMember(member);

        return checklistList.stream()
                .map(ChecklistReadResponse::from)
                .collect(Collectors.toList());
    }

    @Transactional
    public Boolean checkChecklist(Long checklistId) {
        Checklist checklist = checkListRepository.findById(checklistId)
                .orElseThrow(() -> new GlobalException(ChecklistErrorCode.CHECKLIST_NOT_FOUND));

        checklist.check();
        checkListRepository.save(checklist);

        return checklist.getIsCheck();
    }
}
