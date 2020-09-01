package com.xm.commerce.common.exception;

import java.util.Map;

public class ProductAlreadyExistException extends BaseException{
    public ProductAlreadyExistException(Map<String, Object> data) {
        super(ExceptionCode.PRODUCT_ALREADY_EXIST, data);
    }
}
