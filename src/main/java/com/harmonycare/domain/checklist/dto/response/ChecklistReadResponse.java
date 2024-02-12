package com.harmonycare.domain.checklist.dto.response;

import com.harmonycare.domain.checklist.entity.Checklist;
import com.harmonycare.domain.checklist.entity.Day;
import com.harmonycare.global.util.DateTimeUtil;
import lombok.Builder;

import java.util.List;

@Builder
public record ChecklistReadResponse(String title, List<Day> days, String checkTime, Boolean isCheck) {
    public static ChecklistReadResponse from(Checklist checklist) {
        return ChecklistReadResponse.builder()
                .title(checklist.getTitle())
                .days(Day.dayEntityListToDayList(checklist.getDayList()))
                .checkTime(DateTimeUtil.localDateTimeToString(checklist.getCheckTime()))
                .isCheck(checklist.getIsCheck())
                .build();
    }
}
