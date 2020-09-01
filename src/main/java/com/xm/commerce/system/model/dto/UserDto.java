package com.xm.commerce.system.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.xm.commerce.system.model.entity.ecommerce.EcommercePermission;
import com.xm.commerce.system.model.entity.ecommerce.EcommerceRole;
import com.xm.commerce.system.model.entity.ecommerce.EcommerceUser;
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

	private List<EcommerceRole> ecommerceRoles;
	private List<EcommercePermission> ecommercePermissions;

	public UserDto(EcommerceUser ecommerceUser, List<EcommerceRole> ecommerceRoles, List<EcommercePermission> ecommercePermissions) {
		this.id = ecommerceUser.getId();
		this.username = ecommerceUser.getUsername();
		this.nickname = ecommerceUser.getNickname();
		this.password = ecommerceUser.getPassword();
		this.enabled = ecommerceUser.getEnabled();
		this.ecommerceRoles = ecommerceRoles;
		this.ecommercePermissions = ecommercePermissions;
	}

	@JsonIgnore
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<SimpleGrantedAuthority> authorities = new ArrayList<>();
		ecommerceRoles.forEach(r -> authorities.add(new SimpleGrantedAuthority("ROLE_" + r.getCode())));
		ecommercePermissions.forEach(p -> authorities.add(new SimpleGrantedAuthority(p.getCode())));
		return authorities;
	}
}
