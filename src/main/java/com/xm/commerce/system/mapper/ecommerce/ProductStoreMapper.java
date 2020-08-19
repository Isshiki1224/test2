package com.xm.commerce.system.mapper.ecommerce;

import com.xm.commerce.system.model.entity.ecommerce.ProductStore;

public interface ProductStoreMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ProductStore record);

    int insertSelective(ProductStore record);

    ProductStore selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ProductStore record);

    int updateByPrimaryKey(ProductStore record);
}