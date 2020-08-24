package com.xm.commerce.system.service;

import com.google.common.collect.ImmutableMap;
import com.xm.commerce.common.exception.CannotFindByUsernameException;
import com.xm.commerce.system.mapper.ecommerce.PermissionMapper;
import com.xm.commerce.system.mapper.ecommerce.RoleMapper;
import com.xm.commerce.system.mapper.ecommerce.UserMapper;
import com.xm.commerce.system.model.dto.UserDto;
import com.xm.commerce.system.model.entity.ecommerce.Permission;
import com.xm.commerce.system.model.entity.ecommerce.Role;
import com.xm.commerce.system.model.entity.ecommerce.User;
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
		User user = userMapper.findByUsername(username)
				.orElseThrow(() -> new CannotFindByUsernameException(ImmutableMap.of(" cant findByUsername", username)));
		List<Role> roles = roleMapper.findByUserId(user.getId());
		List<Permission> permissions = new ArrayList<>();
		for (Role role : roles) {
			permissions.addAll(permissionMapper.findByRoleId(role.getId()));
		}
		return new UserDto(user, roles, permissions);
	}

	public User findUserByUsername(String username) {
		return userMapper.findByUsername(username)
				.orElseThrow(() -> new CannotFindByUsernameException(ImmutableMap.of(" cant findByUsername", username)));
	}

}
