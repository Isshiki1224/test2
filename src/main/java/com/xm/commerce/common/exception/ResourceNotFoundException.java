package com.xm.commerce.common.exception;

import java.util.Map;

public class ResourceNotFoundException extends BaseException {


    public ResourceNotFoundException(Map<String, Object> data) {
        super(ExceptionCode.NOT_FOUND, data);
    }

    public ResourceNotFoundException(){
        super(ExceptionCode.NOT_FOUND);
    }


}
