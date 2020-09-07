package com.xm.commerce.common.exception;

import java.util.Map;

public class ProductSkuAlreadyExistException extends BaseException {

	public ProductSkuAlreadyExistException(Map<String, Object> data) {
		super(ExceptionCode.PRODUCT_ALREADY_UPLOAD, data);
	}
}
