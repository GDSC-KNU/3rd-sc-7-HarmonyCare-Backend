package com.harmonycare.domain.record.controller;

import com.harmonycare.domain.record.dto.request.RecordSaveRequest;
import com.harmonycare.domain.record.dto.request.RecordUpdateRequest;
import com.harmonycare.domain.record.dto.response.RecordReadResponse;
import com.harmonycare.domain.record.service.RecordService;
import com.harmonycare.global.security.details.PrincipalDetails;
import com.harmonycare.global.util.ApiUtil;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

import static com.harmonycare.global.util.ApiUtil.success;

@RestController
@RequestMapping("/record")
@RequiredArgsConstructor
public class RecordController {

    private final RecordService recordService;

    /**
     * Record 추가
     *
     * @param requestBody Record 추가 DTO
     * @return 추가한 Record의 PK값 리턴
     */
    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiUtil.ApiSuccessResult<Long>> save(
            @RequestBody RecordSaveRequest requestBody,
            @AuthenticationPrincipal PrincipalDetails principalDetails
    ) {
        Long recordId = recordService.saveRecord(requestBody, principalDetails.member());

        return ResponseEntity.ok().body(success(HttpStatus.CREATED, recordId));
    }

    /**
     * Record 클릭
     *
     * @param id 클릭한 Record의 PK 값
     * @return Record 정보
     */
    @GetMapping("/{recordId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiUtil.ApiSuccessResult<RecordReadResponse>> read(
            @PathVariable("recordId") Long id
    ) {
        RecordReadResponse response = recordService.readRecord(id);

        return ResponseEntity.ok().body(ApiUtil.success(HttpStatus.OK, response));
    }

    /**
     * Record 수정
     *
     * @param requestBody Record 수정 DTO
     * @param id 수정할 Record의 PK 값
     * @return 수정된 Record의 PK 값
     */
    @PutMapping("/{recordId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiUtil.ApiSuccessResult<Long>> update(
            @RequestBody RecordUpdateRequest requestBody,
            @PathVariable("recordId") Long id
    ) {
        Long recordId = recordService.updateRecord(requestBody, id);

        return ResponseEntity.ok().body(success(HttpStatus.OK, recordId));
    }

    /**
     * Record 삭제
     *
     * @param id 삭제할 Record의 PK 값
     */
    @DeleteMapping("/{recordId}")
    public ResponseEntity<ApiUtil.ApiSuccessResult<?>> delete(
            @PathVariable("recordId") Long id
    ) {
        recordService.deleteRecord(id);

        return ResponseEntity.ok().body(success(HttpStatus.OK));
    }

    /**
     * 모든 Record 조회
     *
     * @return 자신의 모든 Record 조회
     */
    @GetMapping("/me")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiUtil.ApiSuccessResult<List<RecordReadResponse>>> readMe(
            @AuthenticationPrincipal PrincipalDetails principalDetails
    ) {
        List<RecordReadResponse> recordReadResponses = recordService.readMyRecord(principalDetails.member());

        return ResponseEntity.ok().body(success(HttpStatus.OK, recordReadResponses));
    }

    /**
     *  기간 내의 모든 기록 읽어오기
     *
     * @param today 시작할 날짜
     * @param duration 읽어오고 싶은 기간
     * @return 기간 내의 모든 기록
     */
    @GetMapping()
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiUtil.ApiSuccessResult<List<RecordReadResponse>>> readDuration(
            @AuthenticationPrincipal PrincipalDetails principalDetails,
            @RequestParam("date") @DateTimeFormat(pattern = "yyyy-mm-dd") LocalDate today,
            @RequestParam("duration") Long duration
    ) {
        List<RecordReadResponse> recordReadResponses =
                recordService.readDurationRecord(principalDetails.member(), today, duration);

        return ResponseEntity.ok().body(success(HttpStatus.OK, recordReadResponses));
    }

}
