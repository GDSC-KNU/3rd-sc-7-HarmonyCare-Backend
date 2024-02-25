package com.harmonycare.domain.community.dto.response;

import com.harmonycare.domain.community.entity.Comment;
import com.harmonycare.domain.member.entity.Member;
import lombok.Builder;

import java.util.Objects;

@Builder
public record CommentReadResponse(
        Long commentId,
        String content,
        Boolean isMyComment
) {
    public static CommentReadResponse from(Comment comment, Member member) {
        return CommentReadResponse.builder()
                .commentId(comment.getId())
                .content(comment.getContent())
                .isMyComment(Objects.equals(comment.getMember().getMemberId(), member.getMemberId()))
                .build();
    }
}