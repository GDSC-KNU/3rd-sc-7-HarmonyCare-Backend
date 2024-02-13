package com.harmonycare.domain.community.dto.response;

import com.harmonycare.domain.baby.entity.Baby;
import com.harmonycare.global.util.DateTimeUtil;
import lombok.Builder;

@Builder
public record CommunityReadResponse(
        String name,
        String gender,
        String birthDate,
        Float birthWeight
) {

    public static CommunityReadResponse from(Baby baby) {
        return CommunityReadResponse.builder()
                .name(baby.getName())
                .gender(baby.getGender().name())
                .birthDate(DateTimeUtil.localDateTimeToString(baby.getBirthdate()))
                .birthWeight(baby.getBirthWeight())
                .build();
    }
}