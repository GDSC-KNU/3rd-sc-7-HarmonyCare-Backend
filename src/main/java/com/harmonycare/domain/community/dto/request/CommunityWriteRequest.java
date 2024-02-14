package com.harmonycare.domain.community.dto.request;

import com.harmonycare.domain.community.entity.Community;
import com.harmonycare.domain.member.entity.Member;

public record CommunityWriteRequest(
        String title,
        String content
) {
    public Community toEntity(Member member) {
        return Community.builder()
                .title(this.title())
                .content(this.content())
                .member(member)
                .build();
    }
}