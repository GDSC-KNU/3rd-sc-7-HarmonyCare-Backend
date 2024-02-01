package com.harmonycare.domain.record.exception;

import com.harmonycare.global.exception.ErrorCode;
import org.springframework.http.HttpStatus;

public enum RecordErrorCode implements ErrorCode {

    CHECKLIST_NOT_FOUND(HttpStatus.NOT_FOUND, "Nonexistent Checklist");

    private final HttpStatus status;
    private final String message;

    RecordErrorCode(HttpStatus status, String message) {
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
