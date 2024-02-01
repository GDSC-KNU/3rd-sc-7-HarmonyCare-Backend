package com.harmonycare.domain.checklist.controller;

import com.harmonycare.domain.checklist.dto.request.ChecklistSaveRequest;
import com.harmonycare.domain.checklist.dto.request.ChecklistUpdateRequest;
import com.harmonycare.domain.checklist.dto.response.ChecklistReadResponse;
import com.harmonycare.domain.checklist.service.ChecklistService;
import com.harmonycare.global.security.details.PrincipalDetails;
import com.harmonycare.global.util.ApiUtil;
import com.harmonycare.global.util.ApiUtil.ApiSuccessResult;
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

@RestController
@RequestMapping("/checklist")
@RequiredArgsConstructor
public class ChecklistController {
    // TODO: 2/1/24 체크리스트 체크 기능 추가, 자신의 체크리스트 가져오기 추가

    private final ChecklistService checkListService;

    /**
     * 체크리스트 추가
     *
     * @param requestBody 체크리스트 추가 DTO
     * @return 추가한 데이터 PK값
     */
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
     * 아기 정보 조회
     *
     * @param checklistId 체크리스트 정보 PK값
     * @return 체크리스트 정보
     */
    @GetMapping("/{checklistId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiSuccessResult<ChecklistReadResponse>> read(
            @PathVariable("checklistId") Long checklistId
    ) {
        ChecklistReadResponse response = checkListService.readCheckList(checklistId);

        return ResponseEntity.ok().body(ApiUtil.success(HttpStatus.OK, response));
    }

    /**
     * 아기 정보 수정
     *
     * @param requestBody 체크리스트 수정 DTO
     * @param checklistId 수정할 체크리스트 정보 PK값
     * @return 수정한 데이터 PK값
     */
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
     * 아기 정보 삭제
     *
     * @param checklistId 삭제할 체크리스트 정보 PK값
     */
    @DeleteMapping("/{checklistId}")
    public ResponseEntity<ApiSuccessResult<?>> delete(
            @PathVariable("checklistId") Long checklistId
    ) {
        checkListService.deleteCheckList(checklistId);

        return ResponseEntity.ok().body(ApiUtil.success(HttpStatus.OK));
    }
}
