package com.xm.commerce.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xm.commerce.security.constant.SecurityConstant;
import com.xm.commerce.security.model.entity.JwtUser;
import com.xm.commerce.security.model.request.LoginRequest;
import com.xm.commerce.security.util.JwtTokenUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 如果用户名和密码正确，那么过滤器将创建一个JWT Token 并在HTTP Response 的header中返回它，格式：token: "Bearer +具体token值"
 */
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	private final AuthenticationManager authenticationManager;
	private final RedisTemplate<String, Object> redisTemplate;
	private final JwtTokenUtils jwtTokenUtils;

	public JwtAuthenticationFilter(AuthenticationManager authenticationManager, RedisTemplate<String, Object> redisTemplate, JwtTokenUtils jwtTokenUtils) {
		this.authenticationManager = authenticationManager;
		// 设置URL，以确定是否需要身份验证
		super.setFilterProcessesUrl(SecurityConstant.AUTH_LOGIN_URL);
		this.redisTemplate = redisTemplate;
		this.jwtTokenUtils = jwtTokenUtils;
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request,
												HttpServletResponse response) throws AuthenticationException {

		ObjectMapper objectMapper = new ObjectMapper();
		try {
			// 获取登录的信息
			LoginRequest loginRequest = objectMapper.readValue(request.getInputStream(), LoginRequest.class);
			UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
					loginRequest.getUsername(), loginRequest.getPassword());
			return authenticationManager.authenticate(authentication);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 如果验证成功，就生成token并返回
	 */
	@Override
	protected void successfulAuthentication(HttpServletRequest request,
											HttpServletResponse response,
											FilterChain chain,
											Authentication authentication) {

		JwtUser jwtUser = (JwtUser) authentication.getPrincipal();
		List<String> authorities = jwtUser.getAuthorities()
				.stream()
				.map(GrantedAuthority::getAuthority)
				.collect(Collectors.toList());
		// 创建 Token
		String token = jwtTokenUtils.createToken(jwtUser.getUsername(), authorities);
//		final long expiration = SecurityConstant.TOKEN_EXPIRATION * 1000;
//		redisTemplate.opsForZSet().add(RedisKeyConstant.TOKEN + jwtUser.getUsername(), token,
//				new Date().getTime() + expiration);
//		redisTemplate.opsForValue().set(RedisKeyConstant.TOKEN + jwtUser.getUsername(), token, expiration - new Date().getTime(), TimeUnit.MILLISECONDS);
		// Http Response Header 中返回 Token
		response.setHeader(SecurityConstant.TOKEN_HEADER, token);
	}

	@Override
	protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException authenticationException) throws IOException {
		response.sendError(HttpServletResponse.SC_UNAUTHORIZED, authenticationException.getMessage());
	}
}
