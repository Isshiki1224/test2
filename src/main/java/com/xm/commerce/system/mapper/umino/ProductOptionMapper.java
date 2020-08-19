package com.xm.commerce.system.mapper.umino;

import com.xm.commerce.system.entity.umino.ProductOption;

public interface ProductOptionMapper {
	int deleteByPrimaryKey(Integer productOptionId);

	int insert(ProductOption record);

	int insertSelective(ProductOption record);

	ProductOption selectByPrimaryKey(Integer productOptionId);

	int updateByPrimaryKeySelective(ProductOption record);

	int updateByPrimaryKey(ProductOption record);
}