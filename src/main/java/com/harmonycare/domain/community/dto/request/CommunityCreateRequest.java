package com.harmonycare.domain.community.dto.request;

public record CommunityCreateRequest(
        String name,
        String gender,
        String birthDate,
        float birthWeight
) {
}