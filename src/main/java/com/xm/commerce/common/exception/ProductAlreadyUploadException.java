package com.xm.commerce.common.exception;

public class ProductAlreadyUploadException extends BaseException {
    public ProductAlreadyUploadException() {
        super(ExceptionCode.PRODUCT_ALREADY_UPLOAD);
    }
}
