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
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
        LocalDateTime resultDateTime = LocalDateTime.parse(request.recordTime());

        Record newRecord = Record.builder()
                .recordTask(request.recordTask())
                .recordTime(resultDateTime)
                .minute(0L)
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
        Record findRecord = recordRepository.findById(id)
                .orElseThrow(() -> new GlobalException(RecordErrorCode.RECORD_NOT_FOUND));

        return RecordReadResponse.builder()
                .recordTime(String.valueOf(findRecord.getRecordTime()))
                .recordTask(findRecord.getRecordTask())
                .minute(findRecord.getMinute())
                .description(findRecord.getDescription())
                .build();
    }

    /**
     * UPDATE
     */
    @Transactional
    public Long updateRecord(RecordUpdateRequest request, Long oldRecordId) {
        LocalDateTime resultDateTime = LocalDateTime.parse(request.recordTime());

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
     * 내 아기의 모든 기록을 가져오기
     *
     * @param member
     * @return 내 아기의 모든 기록
     */
    public List<RecordReadResponse> readMyRecord(Member member) {
        List<Baby> babyList = babyRepository.findAllByMember(member);
        Baby myBaby = babyList.get(0);

        List<Record> recordList = myBaby.getRecordList();

        return recordList.stream()
                .map(record -> RecordReadResponse.builder()
                        .recordTask(record.getRecordTask())
                        .recordTime(String.valueOf(record.getRecordTime()))
                        .minute(record.getMinute())
                        .description(record.getDescription())
                        .build())
                .collect(Collectors.toList());
    }

    /**
     * 기간내의 모든 기록 읽어오기
     *
     * @param member
     * @param today 읽어오고 싶은 날짜
     * @param duration 해당 날짜로부터 며칠 읽어올건지?
     * @return 기간내의 모든 기록
     */
    public List<RecordReadResponse> readDurationRecord(Member member, LocalDate today, Long duration) {

        List<RecordReadResponse> responses = readMyRecord(member);
        List<RecordReadResponse> result = new ArrayList<>();

        for (int i = 0; i < duration; i++) {
            LocalDate currentDate = today.plusDays(i);

            for (RecordReadResponse recordReadResponse : responses) {
                if (recordReadResponse.isSameToday(currentDate, recordReadResponse)) {
                    result.add(recordReadResponse);
                }
            }
        }
        return result;
    }
}
