package com.harmonycare.domain.auth.dto.request;

import jakarta.validation.constraints.NotNull;

public record LoginRequest(
    @NotNull String authcode
) {
}