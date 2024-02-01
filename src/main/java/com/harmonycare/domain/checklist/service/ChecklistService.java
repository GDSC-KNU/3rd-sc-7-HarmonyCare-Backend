package com.harmonycare.domain.checklist.service;

import com.harmonycare.domain.checklist.dto.request.ChecklistSaveRequest;
import com.harmonycare.domain.checklist.dto.request.ChecklistUpdateRequest;
import com.harmonycare.domain.checklist.dto.response.ChecklistReadResponse;
import com.harmonycare.domain.checklist.entity.Checklist;
import com.harmonycare.domain.checklist.entity.Day;
import com.harmonycare.domain.checklist.entity.DayEntity;
import com.harmonycare.domain.checklist.exception.ChecklistErrorCode;
import com.harmonycare.domain.checklist.repository.ChecklistRepository;
import com.harmonycare.domain.member.entity.Member;
import com.harmonycare.global.exception.GlobalException;
import com.harmonycare.global.util.DateTimeUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ChecklistService {

    private final ChecklistRepository checkListRepository;

    /**
     * CREATE
     */
    @Transactional
    public Long saveCheckList(ChecklistSaveRequest requestBody, Member member) {
        LocalDateTime resultDateTime = DateTimeUtil.stringToLocalDateTime(requestBody.checkTime());
        List<DayEntity> dayEntityList = Day.dayListToDayEntityList(requestBody.days());

        Checklist checkList = Checklist.builder()
                .title(requestBody.title())
                .dayList(dayEntityList)
                .checkTime(resultDateTime)
                .isCheck(false)
                .member(member)
                .build();

        checkListRepository.save(checkList);
        return checkList.getId();
    }

    /**
     * READ
     */
    public ChecklistReadResponse readCheckList(Long checklistId) {
        Checklist checkList = checkListRepository.findById(checklistId)
                .orElseThrow(() -> new GlobalException(ChecklistErrorCode.CHECKLIST_NOT_FOUND));

        return ChecklistReadResponse.builder()
                .title(checkList.getTitle())
                .days(Day.dayEntityListToDayList(checkList.getDayList()))
                .checkTime(String.valueOf(checkList.getCheckTime()))
                .isCheck(checkList.getIsCheck())
                .build();
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
}
