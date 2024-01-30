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

    /**
     * CREATE
     */
    @Transactional
    public Long saveCheckList(CheckListSaveRequest checkListSaveRequest) {
        LocalDateTime resultDateTime = createLocalDateTime(checkListSaveRequest);
        List<Day> days = checkListSaveRequest.days();

        CheckList checkList = CheckList.builder()
                .title(checkListSaveRequest.title())
                .dayList(createDayEntity(days))
                .checkTime(resultDateTime)
                .isCheck(false)
                .build();

        checkListRepository.save(checkList);
        return checkList.getId();
    }

    /**
     * READ
     */
    public CheckListReadResponse readCheckList(Long checklistId) {
        CheckList checkList = checkListRepository.findById(checklistId)
                .orElseThrow(() -> new GlobalException(ChecklistErrorCode.CHECKLIST_NOT_FOUND));

        return CheckListReadResponse.builder()
                .title(checkList.getTitle())
                .days(createDayList(checkList.getDayList()))
                .checkTime(String.valueOf(checkList.getCheckTime()))
                .isCheck(checkList.getIsCheck())
                .build();
    }

    /**
     * UPDATE
     */
    @Transactional
    public Long updateCheckList(CheckListSaveRequest checkListSaveRequest, Long oldCheckListId) {
        LocalDateTime resultDateTime = createLocalDateTime(checkListSaveRequest);
        List<Day> days = checkListSaveRequest.days();

        CheckList oldCheckList = checkListRepository.findById(oldCheckListId)
                .orElseThrow(() -> new GlobalException(ChecklistErrorCode.CHECKLIST_NOT_FOUND));

        CheckList updateCheckList = CheckList.builder()
                .id(oldCheckList.getId()) // 기존의 checklist_id 값을 가져와서 업데이트
                .title(checkListSaveRequest.title())
                .dayList(createDayEntity(days))
                .checkTime(resultDateTime)
                .isCheck(oldCheckList.getIsCheck())
                .build();

        checkListRepository.save(updateCheckList);
        return updateCheckList.getId();
    }


    /**
     * 사용자가 설정한 체크리스트 시간에 따라 LocalDateTime을 생성하는 로직
     */
    private LocalDateTime createLocalDateTime(CheckListSaveRequest checkListSaveRequest) {
        String customDateTimeString = checkListSaveRequest.checkTime(); // 11:23
        LocalDateTime currentDate = LocalDateTime.now(); // 2024-01-30 T 22:35:17.348
        LocalTime userTime = LocalTime.parse(customDateTimeString, DateTimeFormatter.ofPattern("HH:mm")); //T 11:23
        LocalDateTime resultDateTime = LocalDateTime.of(currentDate.toLocalDate(), userTime); // 2024-01-30 T 11:23
        return resultDateTime;
    }

    /**
     *  List<Day>의 객체를 List<DayEntity>로 바꿔주는 로직
     */
    private List<DayEntity> createDayEntity(List<Day> days) {
        List<DayEntity> dayEntities = new ArrayList<>();
        //null 예외 처리 해야할 듯
        for (Day day : days) {
            dayEntities.add(DayEntity.builder().day(day).build());
        }

        return dayEntities;
    }

    /**
     * List<DayEntity>의 객체를 List<Day>로 바꿔주는 로직
     */
    private List<Day> createDayList(List<DayEntity> dayEntityList) {
        List<Day> dayList = new ArrayList<>();
        //null 예외 처리 해야할 듯
        for (DayEntity dayEntity : dayEntityList) {
            dayList.add(dayEntity.getDay());
        }

        return dayList;
    }
}
