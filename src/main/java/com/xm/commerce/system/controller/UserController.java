package com.xm.commerce.system.controller;

import com.xm.commerce.security.util.CurrentUserUtils;
import com.xm.commerce.system.model.response.ResponseCode;
import com.xm.commerce.system.model.response.ResponseData;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("user")
public class UserController {

	private final CurrentUserUtils currentUserUtils;

	public UserController(CurrentUserUtils currentUserUtils) {
		this.currentUserUtils = currentUserUtils;
	}

	@GetMapping("currentUser")
	public ResponseData getCurrentUser() {
		String currentUsername = currentUserUtils.getCurrentUsername();
		return new ResponseData(ResponseCode.SUCCESS, currentUsername);
	}
}
