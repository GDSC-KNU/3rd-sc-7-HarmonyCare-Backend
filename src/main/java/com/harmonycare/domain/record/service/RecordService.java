package com.harmonycare.domain.record.service;

import com.harmonycare.domain.checklist.dto.request.CheckListSaveRequest;
import com.harmonycare.domain.record.dto.request.RecordSaveRequest;
import com.harmonycare.domain.record.dto.response.RecordReadResponse;
import com.harmonycare.domain.record.entity.Record;
import com.harmonycare.domain.record.exception.RecordErrorCode;
import com.harmonycare.domain.record.repositiry.RecordRepository;
import com.harmonycare.global.exception.GlobalException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    public Long saveRecord(RecordSaveRequest request) {
        LocalDateTime recordDateTime = createLocalDateTime(request);

        Record newRecord = Record.builder()
                .recordTask(request.recordTask())
                .recordTime(recordDateTime)
                .amount(request.amount())
                .build();

        recordRepository.save(newRecord);
        return newRecord.getId();
    }

    /**
     * READ
     */
    public RecordReadResponse readRecord(Long id) {
        Record findRecord = recordRepository.findById(id)
                .orElseThrow(() -> new GlobalException(RecordErrorCode.CHECKLIST_NOT_FOUND));

        return RecordReadResponse.builder()
                .recordTime(String.valueOf(findRecord.getRecordTime()))
                .recordTask(findRecord.getRecordTask())
                .amount(findRecord.getAmount())
                .build();
    }

    /**
     * UPDATE
     */
    @Transactional
    public Long updateRecord(RecordSaveRequest request, Long oldRecordId) {
        LocalDateTime updateRecordTime = createLocalDateTime(request);

        Record oldRecord = recordRepository.findById(oldRecordId)
                .orElseThrow(() -> new GlobalException(RecordErrorCode.CHECKLIST_NOT_FOUND));

        Record newRecord = Record.builder()
                .id(oldRecord.getId())
                .recordTask(request.recordTask())
                .recordTime(updateRecordTime)
                .amount(request.amount())
                .build();

        recordRepository.save(newRecord);
        return newRecord.getId();
    }

    /**
     * DELETE
     */
    @Transactional
    public void deleteRecord(Long deleteRecordId) {
        Record deleteRecord = recordRepository.findById(deleteRecordId)
                .orElseThrow(() -> new GlobalException(RecordErrorCode.CHECKLIST_NOT_FOUND));

        recordRepository.delete(deleteRecord);
    }

    /**
     * 사용자가 설정한 체크리스트 시간에 따라 LocalDateTime을 생성하는 로직
     */
    private LocalDateTime createLocalDateTime(RecordSaveRequest recordSaveRequest) {

        // 사용자가 보낸 문자열
        String userDateString = recordSaveRequest.recordTime();

        // 사용자가 보낸 문자열을 LocalDateTime으로 파싱
        LocalDateTime userDateTime = parseStringToDateTime(userDateString);

        return userDateTime;
    }

    // 문자열을 LocalDateTime으로 파싱하는 메서드
    private static LocalDateTime parseStringToDateTime(String dateString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return LocalDateTime.parse(dateString, formatter);
    }

}
