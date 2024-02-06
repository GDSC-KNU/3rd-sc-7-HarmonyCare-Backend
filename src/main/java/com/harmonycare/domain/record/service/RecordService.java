package com.harmonycare.domain.record.service;

import com.harmonycare.domain.member.entity.Member;
import com.harmonycare.domain.record.dto.request.RecordSaveRequest;
import com.harmonycare.domain.record.dto.request.RecordUpdateRequest;
import com.harmonycare.domain.record.dto.response.RecordReadResponse;
import com.harmonycare.domain.record.entity.Record;
import com.harmonycare.domain.record.exception.RecordErrorCode;
import com.harmonycare.domain.record.repositiry.RecordRepository;
import com.harmonycare.global.exception.GlobalException;
import com.harmonycare.global.util.DateTimeUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RecordService {

    private final RecordRepository recordRepository;

    /**
     * CREATE
     */
    @Transactional
    public Long saveRecord(RecordSaveRequest request, Member member) {
        LocalDateTime resultDateTime = DateTimeUtil.stringToLocalDateTime(request.recordTime());

        Record newRecord = Record.builder()
                .recordTask(request.recordTask())
                .recordTime(resultDateTime)
                .description(request.description())
                .member(member)
                .build();

        recordRepository.save(newRecord);
        return newRecord.getId();
    }

    /**
     * READ
     */
    public RecordReadResponse readRecord(Long id) {
        Record findRecord = recordRepository.findById(id)
                .orElseThrow(() -> new GlobalException(RecordErrorCode.RECORD_NOT_FOUND));

        return RecordReadResponse.builder()
                .recordTime(String.valueOf(findRecord.getRecordTime()))
                .recordTask(findRecord.getRecordTask())
                .description(findRecord.getDescription())
                .build();
    }

    /**
     * UPDATE
     */
    @Transactional
    public Long updateRecord(RecordUpdateRequest request, Long oldRecordId) {
        LocalDateTime resultDateTime = DateTimeUtil.stringToLocalDateTime(request.recordTime());

        Record newRecord = recordRepository.findById(oldRecordId)
                .orElseThrow(() -> new GlobalException(RecordErrorCode.RECORD_NOT_FOUND));

        newRecord.update(request);

        recordRepository.save(newRecord);
        return newRecord.getId();
    }

    /**
     * DELETE
     */
    @Transactional
    public void deleteRecord(Long deleteRecordId) {
        Record deleteRecord = recordRepository.findById(deleteRecordId)
                .orElseThrow(() -> new GlobalException(RecordErrorCode.RECORD_NOT_FOUND));

        recordRepository.delete(deleteRecord);
    }
}
