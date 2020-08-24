package com.xm.commerce.security.util;

import com.xm.commerce.system.model.dto.UserDto;
import com.xm.commerce.system.model.entity.ecommerce.User;
import com.xm.commerce.system.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 获取当前请求的用户
 */
@Component
public class CurrentUserUtils {

	@Resource
	UserService userService;

	public String getCurrentUsername() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null && authentication.getPrincipal() != null) {
			return (String) authentication.getPrincipal();
		}
		return null;
	}

	public User getCurrentUser(){
		String currentUsername = getCurrentUsername();
		return userService.findUserByUsername(currentUsername);
	}

}
