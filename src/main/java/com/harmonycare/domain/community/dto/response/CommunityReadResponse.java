package com.harmonycare.domain.community.dto.response;

import com.harmonycare.domain.community.entity.Community;
import lombok.Builder;

@Builder
public record CommunityReadResponse(
        Long communityId,
        String title,
        String content
) {

    public static CommunityReadResponse from(Community community) {
        return CommunityReadResponse.builder()
                .communityId(community.getId())
                .title(community.getTitle())
                .content(community.getContent())
                .build();
    }
}