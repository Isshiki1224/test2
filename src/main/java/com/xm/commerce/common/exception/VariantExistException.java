package com.xm.commerce.common.exception;

public class VariantExistException extends BaseException{
    public VariantExistException() {
        super(ExceptionCode.VARIANT_EXIST);
    }
}
