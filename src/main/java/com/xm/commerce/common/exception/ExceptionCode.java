package com.xm.commerce.common.exception;

import org.springframework.http.HttpStatus;

public enum ExceptionCode {
    USER_RELATED(100, HttpStatus.OK, "");

    private final int code;
    private final HttpStatus httpStatus;
    private final String message;

    public int getCode() {
        return code;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public String getMessage() {
        return message;
    }

    ExceptionCode(int code, HttpStatus httpStatus, String message) {
        this.code = code;
        this.httpStatus = httpStatus;
        this.message = message;
    }
}
