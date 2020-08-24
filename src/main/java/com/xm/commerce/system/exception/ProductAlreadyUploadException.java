package com.xm.commerce.system.exception;

public class ProductAlreadyUploadException extends BaseException{
    public ProductAlreadyUploadException() {
        super(ExceptionCode.PRODUCT_ALREADY_UPLOAD);
    }
}
