package com.xm.commerce.security.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * 获取当前请求的用户
 */
public class CurrentUserUtils {

	public String getCurrentAccount() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null && authentication.getPrincipal() != null) {
			return (String) authentication.getPrincipal();
		}
		return null;
	}
}
