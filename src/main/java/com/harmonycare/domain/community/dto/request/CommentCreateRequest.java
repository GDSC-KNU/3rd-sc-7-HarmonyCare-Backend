package com.harmonycare.domain.community.dto.request;

public record CommentCreateRequest(
        String name,
        String gender,
        String birthDate,
        float birthWeight
) {
}