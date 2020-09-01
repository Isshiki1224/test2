package com.xm.commerce.system.mapper.ecommerce;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xm.commerce.system.model.entity.ecommerce.EcommercePermission;

import java.util.List;

public interface PermissionMapper extends BaseMapper<EcommercePermission> {


    List<EcommercePermission> findByRoleId(int roleId);
}