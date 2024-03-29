package com.harmonycare.domain.checklist.exception;

import com.harmonycare.global.exception.ErrorCode;
import org.springframework.http.HttpStatus;

public enum ChecklistErrorCode implements ErrorCode {
    CHECKLIST_NOT_FOUND(HttpStatus.NOT_FOUND, "Nonexistent Checklist");

    private final HttpStatus status;
    private final String message;

    ChecklistErrorCode(HttpStatus status, String message) {
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
