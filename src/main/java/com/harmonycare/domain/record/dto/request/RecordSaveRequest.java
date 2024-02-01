package com.harmonycare.domain.record.dto.request;

import com.harmonycare.domain.record.entity.RecordTask;

public record RecordSaveRequest(RecordTask recordTask, String recordTime, int amount) {
}
