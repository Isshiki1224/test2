package com.xm.commerce.security.model.entity;

import com.xm.commerce.system.model.dto.UserDto;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Getter
@Setter
public class JwtUser implements UserDetails {

	private Integer id;
	private String username;
	private String password;
	private Boolean enabled;
	private Collection<? extends GrantedAuthority> authorities;

	public JwtUser(UserDto userDto) {
		this.id = userDto.getId();
		this.username = userDto.getUsername();
		this.password = userDto.getPassword();
		this.enabled = userDto.getEnabled();
		this.authorities = userDto.getAuthorities();
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return enabled;
	}
}
