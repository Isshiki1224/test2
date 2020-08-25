package com.xm.commerce.common.exception;

import org.springframework.util.ObjectUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author shuang.kou
 */
abstract class BaseException extends RuntimeException {
    private final ExceptionCode exceptionCode;
    private final transient HashMap<String, Object> data = new HashMap<>();

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
