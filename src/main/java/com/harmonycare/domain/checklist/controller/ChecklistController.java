package com.harmonycare.domain.checklist.controller;

import com.harmonycare.domain.checklist.dto.request.ChecklistSaveRequest;
import com.harmonycare.domain.checklist.dto.request.ChecklistUpdateRequest;
import com.harmonycare.domain.checklist.dto.response.ChecklistReadResponse;
import com.harmonycare.domain.checklist.service.ChecklistService;
import com.harmonycare.global.security.details.PrincipalDetails;
import com.harmonycare.global.util.ApiUtil;
import com.harmonycare.global.util.ApiUtil.ApiSuccessResult;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
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
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/checklist")
@RequiredArgsConstructor
public class ChecklistController {
    private final ChecklistService checkListService;

    /**
     * 자신의 체크리스트 추가
     *
     * @param requestBody 체크리스트 추가 DTO
     * @return 추가한 데이터 PK값
     */
    @Operation(summary = "자신의 체크리스트 추가", description = "자신의 체크리스트를 추가합니다.")
    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiSuccessResult<Long>> save(
            @RequestBody ChecklistSaveRequest requestBody,
            @AuthenticationPrincipal PrincipalDetails principalDetails
    ) {
        Long checkListID = checkListService.saveCheckList(requestBody, principalDetails.member());

        return ResponseEntity.ok().body(ApiUtil.success(HttpStatus.CREATED, checkListID));
    }

    /**
     * 체크리스트 조회
     *
     * @param checklistId 체크리스트 정보 PK값
     * @return 체크리스트 정보
     */
    @Operation(summary = "체크리스트 조회 (사용X)")
    @GetMapping("/{checklistId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiSuccessResult<ChecklistReadResponse>> read(
            @PathVariable("checklistId") Long checklistId
    ) {
        ChecklistReadResponse response = checkListService.readCheckList(checklistId);

        return ResponseEntity.ok().body(ApiUtil.success(HttpStatus.OK, response));
    }

    /**
     * 자신의 체크리스트 조회
     *
     * @return 자신의 체크리스트 정보
     */
    @Operation(summary = "자신의 체크리스트 조회", description = "자신의 체크리스트를 조회합니다.")
    @GetMapping("/me")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiSuccessResult<List<ChecklistReadResponse>>> readMyChecklist(
            @AuthenticationPrincipal PrincipalDetails principalDetails
    ) {

        checkListService.saveDefaultCheckList(principalDetails.member());
        List<ChecklistReadResponse> response = checkListService.readMyChecklist(principalDetails.member());

        return ResponseEntity.ok().body(ApiUtil.success(HttpStatus.OK, response));
    }

    /**
     * 체크리스트 체크
     *
     * @return 자신의 체크리스트 정보
     */
    @Operation(summary = "체크리스트 체크", description = "체크리스트 체크를 ON/OFF 합니다.")
    @PutMapping("/check/{checklistId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiSuccessResult<Boolean>> checkChecklist(
            @PathVariable("checklistId") Long checklistId
    ) {
        Boolean response = checkListService.checkChecklist(checklistId);

        return ResponseEntity.ok().body(ApiUtil.success(HttpStatus.OK, response));
    }

    /**
     * 체크리스트 수정
     *
     * @param requestBody 체크리스트 수정 DTO
     * @param checklistId 수정할 체크리스트 정보 PK값
     * @return 수정한 데이터 PK값
     */
    @Operation(summary = "체크리스트 수정", description = "체크리스트를 수정합니다.")
    @PutMapping("/{checklistId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiSuccessResult<Long>> update(
            @RequestBody ChecklistUpdateRequest requestBody,
            @PathVariable("checklistId") Long checklistId
    ) {
        Long newCheckListId = checkListService.updateCheckList(requestBody, checklistId);

        return ResponseEntity.ok().body(ApiUtil.success(HttpStatus.OK, newCheckListId));
    }

    /**
     * 체크리스트 삭제
     *
     * @param checklistId 삭제할 체크리스트 정보 PK값
     */
    @Operation(summary = "체크리스트 삭제", description = "체크리스트를 삭제합니다.")
    @DeleteMapping("/{checklistId}")
    public ResponseEntity<ApiSuccessResult<?>> delete(
            @PathVariable("checklistId") Long checklistId
    ) {
        checkListService.deleteCheckList(checklistId);

        return ResponseEntity.ok().body(ApiUtil.success(HttpStatus.OK));
    }

    /**
     * 팁 제공
     *
     * @return 팁 내용 제공
     */
    @Operation(summary = "팁 제공", description = "사용자에게 맞는 팁을 제공합니다")
    @GetMapping("/tip")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiSuccessResult<String>> provideTips(
            @AuthenticationPrincipal PrincipalDetails principalDetails) {
        String tip = checkListService.provideTips(principalDetails.member());

        return ResponseEntity.ok().body(ApiUtil.success(HttpStatus.OK, tip));
    }
}
