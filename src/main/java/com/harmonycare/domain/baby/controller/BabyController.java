package com.harmonycare.domain.baby.controller;

import com.harmonycare.domain.baby.dto.request.BabyCreateRequest;
import com.harmonycare.domain.baby.dto.response.BabyReadResponse;
import com.harmonycare.domain.baby.service.BabyService;
import com.harmonycare.global.security.details.PrincipalDetails;
import com.harmonycare.global.util.ApiUtil;
import com.harmonycare.global.util.ApiUtil.ApiSuccessResult;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/baby")
@RequiredArgsConstructor
public class BabyController {
    private final BabyService babyService;
    /**
     * 아기 추가
     *
     * @param requestBody 아기 추가 DTO
     * @return 추가한 데이터 PK값
     */
    @Operation(summary = "아기 추가", description = "아기를 추가합니다. (최대 1회로 제한!)")
    @PostMapping()
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiSuccessResult<Long>> create(
            @RequestBody BabyCreateRequest requestBody,
            @AuthenticationPrincipal PrincipalDetails principalDetails
    ) {
        Long babyId = babyService.createBaby(requestBody, principalDetails.member());

        return ResponseEntity.ok()
                .body(ApiUtil.success(HttpStatus.OK, babyId));
    }

    /**
     * 아기 정보 조회
     *
     * @param babyId 아기 정보 PK값
     * @return 아기 정보
     */
    @Operation(summary = "아기 정보 조회 (사용X)")
    @GetMapping("/{babyId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiSuccessResult<BabyReadResponse>> read(
            @PathVariable("babyId") Long babyId
    ) {
        BabyReadResponse response = babyService.readBabyByBabyId(babyId);

        return ResponseEntity.ok()
                .body(ApiUtil.success(HttpStatus.OK, response));
    }


    /**
     * 아기 정보 전체 조회
     *
     * @return 모든 아기 정보
     */
    @Operation(summary = "아기 정보 전체 조회 (사용X)")
    @GetMapping()
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiSuccessResult<List<BabyReadResponse>>> readAll() {
        List<BabyReadResponse> response = babyService.readAllBaby();

        return ResponseEntity.ok()
                .body(ApiUtil.success(HttpStatus.OK, response));
    }


    /**
     * 자신의 아기 정보 조회
     *
     * @return 자신의 모든 아기 정보
     */
    @Operation(summary = "자신의 아기 정보 조회", description = "자신의 아기 정보를 조회합니다.")
    @GetMapping("/me")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiSuccessResult<List<BabyReadResponse>>> readMe(
            @AuthenticationPrincipal PrincipalDetails principalDetails
    ) {
        List<BabyReadResponse> response = babyService.readMyBaby(principalDetails.member());

        return ResponseEntity.ok()
                .body(ApiUtil.success(HttpStatus.OK, response));
    }
}