package com.harmonycare.domain.baby.exception;

import com.harmonycare.global.exception.ErrorCode;
import org.springframework.http.HttpStatus;

public enum BabyErrorCode implements ErrorCode {
    BABY_NOT_FOUND(HttpStatus.NOT_FOUND, "Nonexistent Baby");

    private final HttpStatus status;
    private final String message;

    BabyErrorCode(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    public String message() {
        return this.message;
    }

    public String code() {
        return this.name();
    }

    public HttpStatus status() {
        return this.status;
    }
}
