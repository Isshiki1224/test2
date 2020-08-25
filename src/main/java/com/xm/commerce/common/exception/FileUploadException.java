package com.xm.commerce.common.exception;

import java.util.Map;

public class FileUploadException extends BaseException {
    public FileUploadException() {
        super(ExceptionCode.FILE_UPLOAD_EXCEPTION);
    }

    public FileUploadException(Map<String, Object> data) {
        super(ExceptionCode.FILE_UPLOAD_EXCEPTION, data);
    }

}
