package com.xm.commerce.common.exception;

import java.util.Map;

public class SiteNotFoundException extends BaseException {

    public SiteNotFoundException() {
        super(ExceptionCode.SITE_NOT_FOUND);
    }

    public SiteNotFoundException(Map<String, Object> data) {
        super(ExceptionCode.SITE_NOT_FOUND, data);
    }

}
