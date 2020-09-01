package com.xm.commerce.system.service;

import com.google.common.collect.ImmutableMap;
import com.xm.commerce.common.exception.CannotFindByUsernameException;
import com.xm.commerce.system.mapper.ecommerce.PermissionMapper;
import com.xm.commerce.system.mapper.ecommerce.RoleMapper;
import com.xm.commerce.system.mapper.ecommerce.UserMapper;
import com.xm.commerce.system.model.dto.UserDto;
import com.xm.commerce.system.model.entity.ecommerce.EcommercePermission;
import com.xm.commerce.system.model.entity.ecommerce.EcommerceRole;
import com.xm.commerce.system.model.entity.ecommerce.EcommerceUser;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

	@Resource
	private UserMapper userMapper;
	@Resource
	private RoleMapper roleMapper;
	@Resource
	private PermissionMapper permissionMapper;

	public UserDto findByUsername(String username) {
		EcommerceUser ecommerceUser = userMapper.findByUsername(username)
				.orElseThrow(() -> new CannotFindByUsernameException(ImmutableMap.of(" cant findByUsername", username)));
		List<EcommerceRole> ecommerceRoles = roleMapper.findByUserId(ecommerceUser.getId());
		List<EcommercePermission> ecommercePermissions = new ArrayList<>();
		for (EcommerceRole ecommerceRole : ecommerceRoles) {
			ecommercePermissions.addAll(permissionMapper.findByRoleId(ecommerceRole.getId()));
		}
		return new UserDto(ecommerceUser, ecommerceRoles, ecommercePermissions);
	}

	public EcommerceUser findUserByUsername(String username) {
		return userMapper.findByUsername(username)
				.orElseThrow(() -> new CannotFindByUsernameException(ImmutableMap.of(" cant findByUsername", username)));
	}

}
