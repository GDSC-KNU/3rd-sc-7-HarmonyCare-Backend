package com.harmonycare.domain.checklist.dto.response;

import lombok.Builder;

@Builder
public record CheckListReadResponse(String title, String days, String checkTime, Boolean isCheck) {
}
