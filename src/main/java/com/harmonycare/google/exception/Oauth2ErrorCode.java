package com.harmonycare.google.exception;

import com.harmonycare.global.exception.ErrorCode;
import org.springframework.http.HttpStatus;

public enum Oauth2ErrorCode implements ErrorCode {
    FAILED_GET_ACCESS_TOKEN(HttpStatus.BAD_REQUEST, "구글 ACCESS TOKEN 발급 실패");

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
