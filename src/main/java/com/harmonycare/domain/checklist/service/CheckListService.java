package com.harmonycare.domain.checklist.service;

import com.harmonycare.domain.checklist.dto.request.CheckListSaveRequest;
import com.harmonycare.domain.checklist.dto.response.CheckListReadResponse;
import com.harmonycare.domain.checklist.entity.CheckList;
import com.harmonycare.domain.checklist.entity.Day;
import com.harmonycare.domain.checklist.entity.DayEntity;
import com.harmonycare.domain.checklist.exception.ChecklistErrorCode;
import com.harmonycare.domain.checklist.repository.CheckListRepository;
import com.harmonycare.global.exception.GlobalException;
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
public class CheckListService {

    private final CheckListRepository checkListRepository;

    @Transactional
    public Long saveCheckList(CheckListSaveRequest checkListSaveRequest) {
        String customDateTimeString = checkListSaveRequest.checkTime(); // 11:23
        LocalDateTime currentDate = LocalDateTime.now(); // 2024-01-30 T 22:35:17.348
        LocalTime userTime = LocalTime.parse(customDateTimeString, DateTimeFormatter.ofPattern("HH:mm")); //T 11:23
        LocalDateTime resultDateTime = LocalDateTime.of(currentDate.toLocalDate(), userTime); // 2024-01-30 T 11:23

        List<Day> days = checkListSaveRequest.days();

        CheckList checkList = CheckList.builder()
                .title(checkListSaveRequest.title())
                .dayList(createdDayEntity(days))
                .checkTime(resultDateTime)
                .isCheck(false)
                .build();

        return checkListRepository.save(checkList).getId();
    }

    private List<DayEntity> createdDayEntity(List<Day> days) {
        List<DayEntity> dayEntities = new ArrayList<>();

        for (Day day : days) {
            dayEntities.add(DayEntity.builder().day(day).build());
        }

        return dayEntities;
    }

    public CheckListReadResponse readCheckList(Long checklistId) {
        CheckList checkList = checkListRepository.findById(checklistId)
                .orElseThrow(() -> new GlobalException(ChecklistErrorCode.CHECKLIST_NOT_FOUND));

        return CheckListReadResponse.builder()
                .title(checkList.getTitle())
                .days(String.valueOf(checkList.getDayList()))
                .checkTime(String.valueOf(checkList.getCheckTime()))
                .isCheck(checkList.getIsCheck())
                .build();
    }

}
