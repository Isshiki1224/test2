package com.xm.commerce.common.exception;

import org.springframework.http.HttpStatus;

public enum ExceptionCode {
    USER_RELATED(100, HttpStatus.OK, ""),
    NOT_FOUND(10, HttpStatus.BAD_REQUEST, "未找到资源"),
    ACCOUNT_ALREADY_EXIST(11, HttpStatus.BAD_REQUEST, "用户名已存在"),
    METHOD_ARGUMENT_NOT_VALID(1003, HttpStatus.BAD_REQUEST, "方法参数验证失败"),
    REDIS_NOT_EXIST(14, HttpStatus.BAD_REQUEST, "缓存已失效"),
    REDIS_IS_LOCKED(15, HttpStatus.OK, "缓存已被锁"),
    FILE_UPLOAD_EXCEPTION(18, HttpStatus.BAD_REQUEST, "文件上传错误"),
    CURRENT_USER_EXCEPTION(21, HttpStatus.BAD_REQUEST, "用户名密码错误"),
    SITE_NOT_FOUND(13, HttpStatus.BAD_REQUEST, "站点错误"),
    PRODUCT_ALREADY_UPLOAD(16, HttpStatus.BAD_REQUEST, "相同sku的商品已入站"),
    MAX_UPLOAD_SIZE(17, HttpStatus.BAD_REQUEST, "文件大小超出限制（10m）"),
    CREATE_FOLDER_EXCEPTION(12, HttpStatus.BAD_REQUEST, "商品入站失败，商品文件存放目录不存在"),
    VARIANT_EXIST(19, HttpStatus.BAD_REQUEST, "variant exists"),
    REST_TEMPLATE(21, HttpStatus.BAD_REQUEST, "restTemplate Exception"),
    PRODUCT_ALREADY_EXIST(25, HttpStatus.BAD_REQUEST, "商品已经存在"),
    SITE_MESSAGE(26, HttpStatus.BAD_REQUEST, "站点信息有误"),
    SFTP_CONNECT_EXCEPTION(20, HttpStatus.INTERNAL_SERVER_ERROR, "文件服务器无法连接");

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
