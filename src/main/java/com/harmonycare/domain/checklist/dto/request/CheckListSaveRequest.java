package com.harmonycare.domain.checklist.dto.request;

import com.harmonycare.domain.checklist.entity.Day;

import java.util.List;

public record CheckListSaveRequest(String title, List<Day> days, String checkTime) {

    
}
