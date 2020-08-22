package com.xm.commerce.system.mapper.ecommerce;

import com.xm.commerce.system.model.entity.ecommerce.Permission;

import java.util.Collection;
import java.util.List;

public interface PermissionMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Permission record);

    int insertSelective(Permission record);

    Permission selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Permission record);

    int updateByPrimaryKey(Permission record);

    List<Permission> findByRoleId(int roleId);
}