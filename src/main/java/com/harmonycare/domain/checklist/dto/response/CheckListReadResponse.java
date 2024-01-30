package com.harmonycare.domain.checklist.dto.response;

import com.harmonycare.domain.checklist.entity.Day;
import lombok.Builder;
import java.util.List;

@Builder
public record CheckListReadResponse(String title, List<Day> days, String checkTime, Boolean isCheck) {
}
