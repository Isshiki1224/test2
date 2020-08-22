package com.xm.commerce.system.exception;


import org.springframework.http.HttpStatus;
import org.springframework.util.ObjectUtils;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

public class ErrorResponse {
    private int code;
    private int status;
    private String message;
    private String path;
    private Instant timestamp;
    private HashMap<String, Object> errorDetail = new HashMap<>();

    public ErrorResponse(BaseException ex, String path) {
        this(ex.getErrorCode().getCode(), ex.getErrorCode().getHttpStatus().value(), ex.getErrorCode().getMessage(), path, ex.getData());
    }

    public ErrorResponse(RuntimeException ex, String path) {
        this(500, HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage(), path);
    }


    public ErrorResponse(ExceptionCode errorCode, String path) {
        this(errorCode.getCode(), errorCode.getHttpStatus().value(), errorCode.getMessage(), path, null);
    }

    public ErrorResponse(ExceptionCode errorCode, String path, Map<String, Object> errorDetail) {
        this(errorCode.getCode(), errorCode.getHttpStatus().value(), errorCode.getMessage(), path, errorDetail);
    }

    private ErrorResponse(int code, int status, String message, String path, Map<String, Object> errorDetail) {
        this.code = code;
        this.status = status;
        this.message = message;
        this.path = path;
        this.timestamp = Instant.now();
        if (!ObjectUtils.isEmpty(errorDetail)) {
            this.errorDetail.putAll(errorDetail);
        }
    }

    private ErrorResponse(int code, int status, String message, String path) {
        this.code = code;
        this.status = status;
        this.message = message;
        this.path = path;
        this.timestamp = Instant.now();

    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }

    public HashMap<String, Object> getErrorDetail() {
        return errorDetail;
    }

    public void setErrorDetail(HashMap<String, Object> errorDetail) {
        this.errorDetail = errorDetail;
    }

    @Override
    public String toString() {
        return "ErrorResponse{" +
                "code=" + code +
                ", status=" + status +
                ", message='" + message + '\'' +
                ", path='" + path + '\'' +
                ", timestamp=" + timestamp +
                ", errorDetail=" + errorDetail +
                '}';
    }
}
