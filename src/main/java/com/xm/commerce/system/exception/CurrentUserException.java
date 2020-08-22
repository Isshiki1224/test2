package com.xm.commerce.system.exception;

public class CurrentUserException extends BaseException{
    public CurrentUserException() {
        super(ExceptionCode.CURRENT_USER_EXCEPTION);
    }
}
