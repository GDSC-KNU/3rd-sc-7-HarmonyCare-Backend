package com.harmonycare.domain.member.dto.response;

import com.harmonycare.domain.baby.entity.Baby;
import com.harmonycare.domain.member.entity.Member;
import com.harmonycare.global.util.DateTimeUtil;
import lombok.Builder;

@Builder
public record ProfileReadResponse(
        String parentName, String email,
        String babyName, String babyBirthDate
) {
    public static ProfileReadResponse from(Member member, Baby baby) {
        return ProfileReadResponse.builder()
                .parentName(member.getName())
                .email(member.getEmail())
                .babyName(baby.getName())
                .babyBirthDate(DateTimeUtil.localDateTimeToString(baby.getBirthdate()))
                .build();
    }
}
