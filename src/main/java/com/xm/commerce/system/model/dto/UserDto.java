package com.xm.commerce.system.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.xm.commerce.system.model.entity.ecommerce.Permission;
import com.xm.commerce.system.model.entity.ecommerce.Role;
import com.xm.commerce.system.model.entity.ecommerce.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

	private Integer id;

	private String username;

	private String nickname;

	private String password;

	private Boolean enabled;

	private List<Role> roles;
	private List<Permission> permissions;

	public UserDto(User user, List<Role> roles, List<Permission> permissions) {
		this.id = user.getId();
		this.username = user.getUsername();
		this.nickname = user.getNickname();
		this.password = user.getPassword();
		this.enabled = user.getEnabled();
		this.roles = roles;
		this.permissions = permissions;
	}

	@JsonIgnore
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<SimpleGrantedAuthority> authorities = new ArrayList<>();
		roles.forEach(r -> authorities.add(new SimpleGrantedAuthority("ROLE_" + r.getCode())));
		permissions.forEach(p -> authorities.add(new SimpleGrantedAuthority(p.getCode())));
		return authorities;
	}
}
