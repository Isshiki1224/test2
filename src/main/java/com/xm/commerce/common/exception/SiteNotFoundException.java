package com.xm.commerce.common.exception;

public class SiteNotFoundException extends BaseException {

    public SiteNotFoundException() {
        super(ExceptionCode.SITE_NOT_FOUND);
    }
}
