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
    public Long saveCheckList(ChecklistSaveRequest checkListSaveRequest, Member member) {
        LocalDateTime resultDateTime = createLocalDateTime(checkListSaveRequest);
        List<Day> days = checkListSaveRequest.days();

        Checklist checkList = Checklist.builder()
                .title(checkListSaveRequest.title())
                .dayList(Day.getDayEntity(days))
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
                .days(createDayList(checkList.getDayList()))
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


    /**
     * 사용자가 설정한 체크리스트 시간에 따라 LocalDateTime을 생성하는 로직
     */
    private LocalDateTime createLocalDateTime(ChecklistSaveRequest checkListSaveRequest) {
        String customDateTimeString = checkListSaveRequest.checkTime(); // 11:23
        LocalDateTime currentDate = LocalDateTime.now(); // 2024-01-30 T 22:35:17.348
        LocalTime userTime = LocalTime.parse(customDateTimeString, DateTimeFormatter.ofPattern("HH:mm")); //T 11:23
        LocalDateTime resultDateTime = LocalDateTime.of(currentDate.toLocalDate(), userTime); // 2024-01-30 T 11:23
        return resultDateTime;
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
