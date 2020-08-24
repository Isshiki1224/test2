package com.xm.commerce.system.exception;

public class SiteNotFoundException extends BaseException {

    public SiteNotFoundException() {
        super(ExceptionCode.SITE_NOT_FOUND);
    }
}
