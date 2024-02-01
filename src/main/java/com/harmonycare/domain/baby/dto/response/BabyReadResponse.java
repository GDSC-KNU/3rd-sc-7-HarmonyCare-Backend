package com.harmonycare.domain.baby.dto.response;

import com.harmonycare.domain.baby.entity.Baby;
import com.harmonycare.global.util.DateTimeUtil;
import lombok.Builder;

@Builder
public record BabyReadResponse(
        String name,
        String gender,
        String birthDate,
        Float birthWeight
) {

    public static BabyReadResponse from(Baby baby) {
        return BabyReadResponse.builder()
                .name(baby.getName())
                .gender(baby.getGender().name())
                .birthDate(DateTimeUtil.localDateTimeToString(baby.getBirthdate()))
                .birthWeight(baby.getBirthWeight())
                .build();
    }
}