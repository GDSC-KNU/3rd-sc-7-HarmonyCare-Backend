package com.harmonycare.google.exception;

import com.harmonycare.global.exception.ErrorCode;
import org.springframework.http.HttpStatus;

public enum Oauth2ErrorCode implements ErrorCode {
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "Nonexistent Member");

    private final HttpStatus status;
    private final String message;

    Oauth2ErrorCode(HttpStatus status, String message) {
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
