package com.harmonycare.domain.record.service;

import com.harmonycare.domain.baby.entity.Baby;
import com.harmonycare.domain.baby.repository.BabyRepository;
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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RecordService {
    private final RecordRepository recordRepository;
    private final BabyRepository babyRepository;

    /**
     * CREATE
     */
    @Transactional
    public Long saveRecord(RecordSaveRequest request, Member member) {
        LocalDateTime startDateTime = DateTimeUtil.stringToLocalDateTime(request.startTime());
        LocalDateTime endDateTime = DateTimeUtil.stringToLocalDateTime(request.endTime());

        Record newRecord = Record.builder()
                .recordTask(request.recordTask())
                .startTime(startDateTime)
                .endTime(endDateTime)
                .description(request.description())
                .baby(babyRepository.findAllByMember(member).get(0))
                .build();

        recordRepository.save(newRecord);
        return newRecord.getId();
    }

    /**
     * READ
     */
    public RecordReadResponse readRecord(Long id) {
        Record record = recordRepository.findById(id)
                .orElseThrow(() -> new GlobalException(RecordErrorCode.RECORD_NOT_FOUND));

        return RecordReadResponse.from(record);
    }

    /**
     * UPDATE
     */
    @Transactional
    public Long updateRecord(RecordUpdateRequest request, Long oldRecordId) {
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

    /**
     * 내 아기의 모든 기록을 읽기
     *
     * @return 내 아기의 모든 기록
     */
    public List<RecordReadResponse> readMyRecord(Member member) {
        List<Record> recordList = getMyRecordInFirstBaby(member);

        return recordList.stream()
                .map(RecordReadResponse::from)
                .collect(Collectors.toList());
    }

    /**
     * 기간 내 모든 기록 읽기
     *
     * @param day 기준 날짜
     * @param range {@param day}로부터 읽어올 날짜 범위 (단위: 일)
     * @return 기간 내의 모든 기록
     */
    public List<RecordReadResponse> readRecordUsingRange(Member member, LocalDate day, Long range) {
        List<Record> recordList = getMyRecordInFirstBaby(member);

        LocalDateTime startTime = day.atStartOfDay();
        LocalDateTime endTime = day.minusDays(range).atStartOfDay();

        return recordList.stream()
                .filter(record -> isWithinRange(record.getStartTime(), startTime, endTime))
                .map(RecordReadResponse::from)
                .collect(Collectors.toList());
    }

    private boolean isWithinRange(LocalDateTime dateTime, LocalDateTime startTime, LocalDateTime endTime) {
        return !dateTime.isBefore(startTime) && dateTime.isBefore(endTime);
    }

    private List<Record> getMyRecordInFirstBaby(Member member) {
        List<Baby> babyList = babyRepository.findAllByMember(member);
        Baby myBaby = babyList.get(0);

        return myBaby.getRecordList();
    }
}
