package com.harmonycare.global.utility;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import org.springframework.http.HttpStatus;

public class ApiUtility {

    public static <T> ApiSuccessResult<T> success(HttpStatus httpStatus, T response) {
        return new ApiSuccessResult<>(httpStatus.value(), response);
    }

    public static <T> ApiErrorResult error(HttpStatus status, String code, String message) {
        return new ApiErrorResult(status.value(), code, message);
    }

    @Getter
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public record ApiSuccessResult<T>(int status,T response) {
    }

    @Getter
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public record ApiErrorResult(int status, String code, String message) {
    }

}
