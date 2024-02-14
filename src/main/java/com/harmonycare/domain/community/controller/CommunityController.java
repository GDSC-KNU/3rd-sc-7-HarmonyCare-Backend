package com.harmonycare.domain.community.controller;

import com.harmonycare.domain.community.dto.request.CommunityWriteRequest;
import com.harmonycare.domain.community.dto.response.CommunityReadResponse;
import com.harmonycare.domain.community.service.CommunityService;
import com.harmonycare.global.security.details.PrincipalDetails;
import com.harmonycare.global.util.ApiUtil;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/community")
@RequiredArgsConstructor
public class CommunityController {
    private final CommunityService communityService;

    /**
     * 커뮤니티 글 작성
     *
     * @param requestBody 글 작성 DTO
     * @return 추가한 데이터 PK값
     */
    @Operation(summary = "커뮤니티 글 작성", description = "커뮤니티 글을 작성합니다.")
    @PostMapping()
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiUtil.ApiSuccessResult<Long>> write(
            @RequestBody CommunityWriteRequest requestBody,
            @AuthenticationPrincipal PrincipalDetails principalDetails
    ) {
        Long communityId = communityService.writeCommunity(principalDetails.member(), requestBody);

        return ResponseEntity.ok()
                .body(ApiUtil.success(HttpStatus.OK, communityId));
    }

    /**
     * 모든 커뮤니티 글 조회
     *
     * @return 모든 커뮤니티 글
     */
    @Operation(summary = "모든 커뮤니티 글 조회", description = "모든 커뮤니티 글을 조회합니다.")
    @GetMapping()
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiUtil.ApiSuccessResult<List<CommunityReadResponse>>> readAll() {
        List<CommunityReadResponse> response = communityService.readAllCommunity();

        return ResponseEntity.ok()
                .body(ApiUtil.success(HttpStatus.OK, response));
    }

    /**
     * 자신의 커뮤니티 글 조회
     *
     * @return 자신의 커뮤니티 글
     */
    @Operation(summary = "자신의 커뮤니티 글 조회", description = "자신의 커뮤니티 글을 조회합니다.")
    @GetMapping("/me")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiUtil.ApiSuccessResult<List<CommunityReadResponse>>> readMyAll(
            @AuthenticationPrincipal PrincipalDetails principalDetails
    ) {
        List<CommunityReadResponse> response = communityService.readAllMyCommunity(principalDetails.member());

        return ResponseEntity.ok()
                .body(ApiUtil.success(HttpStatus.OK, response));
    }


    /**
     * 특정 커뮤니티 글 삭제
     *
     * @param communityId 커뮤니티 글 PK
     * @return 특정 커뮤니티 글
     */
    @Operation(summary = "특정 커뮤니티 글을 삭제합니다.", description = "커뮤니티 글을 삭제합니다.")
    @DeleteMapping("/{communityId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiUtil.ApiSuccessResult<?>> delete(
            @PathVariable("communityId") Long communityId
    ) {
        communityService.deleteCommunity(communityId);

        return ResponseEntity.ok()
                .body(ApiUtil.success(HttpStatus.OK));
    }
}
