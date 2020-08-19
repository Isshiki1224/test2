package com.xm.commerce.system.mapper.umino;

import com.xm.commerce.system.entity.umino.Product;

public interface ProductMapper {
	int deleteByPrimaryKey(Integer productId);

	int insert(Product record);

	int insertSelective(Product record);

	Product selectByPrimaryKey(Integer productId);

	int updateByPrimaryKeySelective(Product record);

	int updateByPrimaryKey(Product record);
}