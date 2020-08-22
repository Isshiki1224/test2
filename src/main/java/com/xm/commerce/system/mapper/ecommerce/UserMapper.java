package com.xm.commerce.system.mapper.ecommerce;

import com.xm.commerce.system.model.entity.ecommerce.User;

import java.util.Optional;

public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    Optional<User> findByUsername(String username);
}