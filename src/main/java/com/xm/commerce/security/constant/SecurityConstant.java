package com.xm.commerce.security.constant;

public class SecurityConstant {

	public static final String AUTH_LOGIN_URL = "/auth/login";
	public static String TOKEN_HEADER = "Authorization";
	public static String TOKEN_PREFIX = "Bearer ";
	public static final String TOKEN_TYPE = "JWT";
	public static final long TOKEN_EXPIRATION = 60 * 60 * 24 * 7L;
	public static final String ROLE_CLAIMS = "rol";
}
