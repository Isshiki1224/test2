package com.xm.commerce.system.exception;

public class AccountAlreadyExistException extends BaseException{
    public AccountAlreadyExistException() {
        super(ExceptionCode.ACCOUNT_ALREADY_EXIST);
    }
}
