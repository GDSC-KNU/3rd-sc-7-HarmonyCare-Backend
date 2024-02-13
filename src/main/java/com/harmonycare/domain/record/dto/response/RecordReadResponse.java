package com.harmonycare.domain.record.dto.response;

import com.harmonycare.domain.record.entity.Record;
import com.harmonycare.domain.record.entity.RecordTask;
import com.harmonycare.global.util.DateTimeUtil;
import lombok.Builder;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder
public record RecordReadResponse(RecordTask recordTask, String startTime, String endTime, String description) {
    public static RecordReadResponse from(Record record) {
        return RecordReadResponse.builder()
                .recordTask(record.getRecordTask())
                .startTime(DateTimeUtil.localDateTimeToString(record.getStartTime()))
                .endTime(DateTimeUtil.localDateTimeToString(record.getEndTime()))
                .description(record.getDescription())
                .build();
    }
}

