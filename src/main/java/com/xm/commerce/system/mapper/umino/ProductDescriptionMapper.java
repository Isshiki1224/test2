package com.xm.commerce.system.mapper.umino;

import com.xm.commerce.system.entity.umino.ProductDescription;
import org.apache.ibatis.annotations.Param;

public interface ProductDescriptionMapper {
	int deleteByPrimaryKey(@Param("productId") Integer productId, @Param("languageId") Integer languageId);

	int insert(ProductDescription record);

	int insertSelective(ProductDescription record);

	ProductDescription selectByPrimaryKey(@Param("productId") Integer productId, @Param("languageId") Integer languageId);

	int updateByPrimaryKeySelective(ProductDescription record);

	int updateByPrimaryKey(ProductDescription record);
}