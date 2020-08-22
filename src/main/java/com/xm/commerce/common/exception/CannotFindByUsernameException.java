package com.xm.commerce.common.exception;

import java.util.Map;

public class CannotFindByUsernameException extends BaseException {

	public CannotFindByUsernameException(Map<String, Object> data) {
		super(ExceptionCode.USER_RELATED, data);
	}
}
