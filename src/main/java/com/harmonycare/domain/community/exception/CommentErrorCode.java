package com.harmonycare.domain.community.exception;

import com.harmonycare.global.exception.ErrorCode;
import org.springframework.http.HttpStatus;

public enum CommentErrorCode implements ErrorCode {
    COMMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "Nonexistent Comment");

    private final HttpStatus status;
    private final String message;

    CommentErrorCode(HttpStatus status, String message) {
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
