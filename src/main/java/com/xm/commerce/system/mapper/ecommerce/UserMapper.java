package com.xm.commerce.system.mapper.ecommerce;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xm.commerce.system.model.entity.ecommerce.EcommerceUser;

import java.util.Optional;

public interface UserMapper extends BaseMapper<EcommerceUser> {


    Optional<EcommerceUser> findByUsername(String username);
}