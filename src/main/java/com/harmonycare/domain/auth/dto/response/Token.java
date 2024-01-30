package com.harmonycare.domain.auth.dto.response;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record Token(
    @NotNull String grantType,
    @NotNull String accessToken
) {
}