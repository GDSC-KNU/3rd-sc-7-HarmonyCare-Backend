package com.harmonycare.domain.record.dto.response;

import com.harmonycare.domain.record.entity.RecordTask;
import lombok.Builder;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder
public record RecordReadResponse(RecordTask recordTask, String recordTime, Long minute, String description)
{
    public static boolean isSameToday(LocalDate today, RecordReadResponse recordReadResponse) {
        return LocalDateTime.parse(recordReadResponse.recordTime())
                .toLocalDate().isEqual(today);
    }
}

