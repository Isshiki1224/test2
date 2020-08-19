package com.xm.commerce.system.mapper.umino;

import com.xm.commerce.system.entity.umino.ProductImage;

public interface ProductImageMapper {
	int deleteByPrimaryKey(Integer productImageId);

	int insert(ProductImage record);

	int insertSelective(ProductImage record);

	ProductImage selectByPrimaryKey(Integer productImageId);

	int updateByPrimaryKeySelective(ProductImage record);

	int updateByPrimaryKey(ProductImage record);
}