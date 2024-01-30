package com.harmonycare.domain.baby.dto.request;

public record BabyCreateRequest(
        String name,
        String gender,
        String birthDate,
        float birthWeight
) {
}