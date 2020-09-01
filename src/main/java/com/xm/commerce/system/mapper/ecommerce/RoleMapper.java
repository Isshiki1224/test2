package com.xm.commerce.system.mapper.ecommerce;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xm.commerce.system.model.entity.ecommerce.EcommerceRole;

import java.util.List;

public interface RoleMapper extends BaseMapper<EcommerceRole> {


    List<EcommerceRole> findByUserId(int userId);
}