package com.xm.commerce.system.mapper.umino;

import com.xm.commerce.system.model.entity.umino.ProductAttribute;
import org.apache.ibatis.annotations.Param;

public interface ProductAttributeMapper {
	int deleteByPrimaryKey(@Param("productId") Integer productId, @Param("attributeId") Integer attributeId, @Param("languageId") Integer languageId);

	int insert(ProductAttribute record);

	int insertSelective(ProductAttribute record);

	ProductAttribute selectByPrimaryKey(@Param("productId") Integer productId, @Param("attributeId") Integer attributeId, @Param("languageId") Integer languageId);

	int updateByPrimaryKeySelective(ProductAttribute record);

	int updateByPrimaryKey(ProductAttribute record);
}