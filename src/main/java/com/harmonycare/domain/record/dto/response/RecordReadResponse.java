package com.harmonycare.domain.record.dto.response;

import com.harmonycare.domain.record.entity.RecordTask;
import lombok.Builder;

@Builder
public record RecordReadResponse(RecordTask recordTask, String recordTime, String description) {
}
