package com.harmonycare.domain.record.dto.request;

import com.harmonycare.domain.record.entity.RecordTask;

public record RecordUpdateRequest(RecordTask recordTask, String startTime, String endTime, String description) {
}
