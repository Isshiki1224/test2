package com.xm.commerce.security.sevice;

import com.xm.commerce.security.model.entity.JwtUser;
import com.xm.commerce.system.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	private final UserService userService;

	public UserDetailsServiceImpl(UserService userService) {
		this.userService = userService;
	}

	@Override
	public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
		return new JwtUser(userService.findByUsername(name));
	}

}
