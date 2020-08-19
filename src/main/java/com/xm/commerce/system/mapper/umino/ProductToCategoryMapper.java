package com.xm.commerce.system.mapper.umino;

import com.xm.commerce.system.model.entity.umino.ProductToCategory;
import org.apache.ibatis.annotations.Param;

public interface ProductToCategoryMapper {
	int deleteByPrimaryKey(@Param("productId") Integer productId, @Param("categoryId") Integer categoryId);

	int insert(ProductToCategory record);

	int insertSelective(ProductToCategory record);
}