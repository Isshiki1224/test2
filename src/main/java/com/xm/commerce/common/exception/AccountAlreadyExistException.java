package com.xm.commerce.common.exception;

public class AccountAlreadyExistException extends BaseException {
    public AccountAlreadyExistException() {
        super(ExceptionCode.ACCOUNT_ALREADY_EXIST);
    }
}
