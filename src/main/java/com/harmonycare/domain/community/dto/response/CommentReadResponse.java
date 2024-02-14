package com.harmonycare.domain.community.dto.response;

import com.harmonycare.domain.community.entity.Comment;
import lombok.Builder;

@Builder
public record CommentReadResponse(
        Long commentId,
        String content
) {
    public static CommentReadResponse from(Comment comment) {
        return CommentReadResponse.builder()
                .commentId(comment.getId())
                .content(comment.getContent())
                .build();
    }
}