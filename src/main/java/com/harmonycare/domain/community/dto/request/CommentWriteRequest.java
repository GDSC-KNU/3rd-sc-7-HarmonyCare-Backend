package com.harmonycare.domain.community.dto.request;

import com.harmonycare.domain.community.entity.Comment;
import com.harmonycare.domain.community.entity.Community;
import com.harmonycare.domain.member.entity.Member;

public record CommentWriteRequest(
        Long communityId,
        String content
) {
    public Comment toEntity(Member member, Community community) {
        return Comment.builder()
                .content(this.content())
                .member(member)
                .community(community)
                .build();
    }
}