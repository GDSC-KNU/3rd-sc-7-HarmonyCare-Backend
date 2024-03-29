package com.harmonycare.domain.auth.dto.response;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record LoginResponse(
    @NotNull boolean hasBaby,
    @NotNull Token token
) {
}