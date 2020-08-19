package com.xm.commerce.system.mapper.umino;

import com.xm.commerce.system.model.entity.umino.ProductOptionValue;

public interface ProductOptionValueMapper {
	int deleteByPrimaryKey(Integer productOptionValueId);

	int insert(ProductOptionValue record);

	int insertSelective(ProductOptionValue record);

	ProductOptionValue selectByPrimaryKey(Integer productOptionValueId);

	int updateByPrimaryKeySelective(ProductOptionValue record);

	int updateByPrimaryKey(ProductOptionValue record);
}