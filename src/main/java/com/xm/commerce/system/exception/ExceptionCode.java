package com.xm.commerce.system.exception;

import org.springframework.http.HttpStatus;

public enum ExceptionCode {
    NOT_FOUND(10, HttpStatus.BAD_REQUEST, "未找到资源"),
    ACCOUNT_ALREADY_EXIST(11, HttpStatus.BAD_REQUEST, "用户名已存在"),
    METHOD_ARGUMENT_NOT_VALID(1003, HttpStatus.BAD_REQUEST, "方法参数验证失败"),
    REDIS_NOT_EXIST(14, HttpStatus.BAD_REQUEST, "缓存已失效"),
    REDIS_IS_LOCKED(15, HttpStatus.OK, "缓存已被锁"),
    FILE_UPLOAD_EXCEPTION(18, HttpStatus.BAD_REQUEST, "文件上传错误"),
    CURRENT_USER_EXCEPTION(21, HttpStatus.BAD_REQUEST, "用户名密码错误"),
    SITE_NOT_FOUND(13, HttpStatus.BAD_REQUEST, "站点错误"),
    PRODUCT_ALREADY_UPLOAD(16, HttpStatus.BAD_REQUEST, "商品已入站"),
    CREATE_FOLDER_EXCEPTION(12, HttpStatus.BAD_REQUEST, "商品入站失败，商品文件存放目录不存在");


    private final int code;
    private final HttpStatus httpStatus;
    private final String message;

    public int getCode() {
        return code;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public String getMessage() {
        return message;
    }

    ExceptionCode(int code, HttpStatus httpStatus, String message) {
        this.code = code;
        this.httpStatus = httpStatus;
        this.message = message;
    }
}
