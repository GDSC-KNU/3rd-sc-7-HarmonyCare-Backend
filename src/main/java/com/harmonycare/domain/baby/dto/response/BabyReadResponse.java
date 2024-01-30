package com.harmonycare.domain.baby.dto.response;

import com.harmonycare.domain.baby.entity.Baby;
import lombok.Builder;

import java.time.format.DateTimeFormatter;

@Builder
public record BabyReadResponse(
        String name,
        String gender,
        String birthDate,
        Float birthWeight
) {

    public static BabyReadResponse from(Baby baby) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        return BabyReadResponse.builder()
                .name(baby.getName())
                .gender(baby.getGender().name())
                .birthDate(baby.getBirthdate().format(formatter))
                .birthWeight(baby.getBirthWeight())
                .build();
    }
}