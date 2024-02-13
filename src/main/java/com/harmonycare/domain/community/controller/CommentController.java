package com.harmonycare.domain.community.controller;

import com.harmonycare.domain.community.dto.request.CommentWriteRequest;
import com.harmonycare.domain.community.dto.request.CommunityWriteRequest;
import com.harmonycare.domain.community.dto.response.CommentReadResponse;
import com.harmonycare.domain.community.dto.response.CommunityReadResponse;
import com.harmonycare.domain.community.service.CommentService;
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
@RequestMapping("/comment")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    /**
     * 댓글 작성
     *
     * @param requestBody 댓글 작성 DTO
     * @return 추가한 데이터 PK값
     */
    @Operation(summary = "댓글 작성", description = "커뮤니티에 댓글을 작성합니다.")
    @PostMapping()
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiUtil.ApiSuccessResult<Long>> write(
            @RequestBody CommentWriteRequest requestBody,
            @AuthenticationPrincipal PrincipalDetails principalDetails
    ) {
        Long commentId = commentService.writeComment(principalDetails.member(), requestBody);

        return ResponseEntity.ok()
                .body(ApiUtil.success(HttpStatus.OK, commentId));
    }

    /**
     * 특정 커뮤니티의 댓글 조회
     *
     * @return 특정 커뮤니티의 모든 댓글
     */
    @Operation(summary = "특정 커뮤니티의 댓글 조회", description = "특정 커뮤니티의 댓글을 조회합니다.")
    @GetMapping("/{communityId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiUtil.ApiSuccessResult<List<CommentReadResponse>>> readByCommunityId(
            @PathVariable("communityId") Long communityId
    ) {
        List<CommentReadResponse> response = commentService.readCommentByCommunityId(communityId);

        return ResponseEntity.ok()
                .body(ApiUtil.success(HttpStatus.OK, response));
    }

    /**
     * 특정 댓글 삭제
     *
     * @param commentId 삭제할 댓글의 PK
     * @return 삭제 성공 여부
     */
    @Operation(summary = "특정 댓글을 삭제합니다.", description = "댓글을 삭제합니다.")
    @DeleteMapping("/{commentId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiUtil.ApiSuccessResult<?>> delete(
            @PathVariable("commentId") Long commentId
    ) {
        commentService.deleteComment(commentId);

        return ResponseEntity.ok()
                .body(ApiUtil.success(HttpStatus.OK));
    }
}
