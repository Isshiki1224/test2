package com.xm.commerce.system.exception;

import org.springframework.util.ObjectUtils;

import java.util.HashMap;
import java.util.Map;


public class BaseException extends RuntimeException {
    private final ExceptionCode exceptionCode;
    private final transient HashMap<String, Object> data = new HashMap<>();
    private transient Object object = new Object();

    public BaseException(ExceptionCode exceptionCode, Map<String, Object> data) {
        super(exceptionCode.getMessage());
        this.exceptionCode = exceptionCode;
        if (!ObjectUtils.isEmpty(data)) {
            this.data.putAll(data);
        }
    }

    public BaseException(ExceptionCode exceptionCode){
        super(exceptionCode.getMessage());
        this.exceptionCode = exceptionCode;
    }

    public BaseException(ExceptionCode exceptionCode,Object object){
        super(exceptionCode.getMessage());
        this.exceptionCode = exceptionCode;
        if (null != object){
            this.object = object;
        }
    }

    BaseException(ExceptionCode exceptionCode, Map<String, Object> data, Throwable cause) {
        super(exceptionCode.getMessage(), cause);
        this.exceptionCode = exceptionCode;
        if (!ObjectUtils.isEmpty(data)) {
            this.data.putAll(data);
        }
    }

    public ExceptionCode getErrorCode() {
        return exceptionCode;
    }

    public Map<String, Object> getData() {
        return data;
    }

}
