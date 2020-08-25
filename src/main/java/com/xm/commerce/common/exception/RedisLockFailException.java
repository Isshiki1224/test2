package com.xm.commerce.common.exception;

public class RedisLockFailException extends BaseException {
    public RedisLockFailException() {

        super(ExceptionCode.REDIS_IS_LOCKED);
    }
}
