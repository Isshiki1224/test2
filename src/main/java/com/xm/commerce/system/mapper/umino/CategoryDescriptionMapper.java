package com.xm.commerce.system.mapper.umino;

import com.xm.commerce.system.model.entity.umino.CategoryDescription;
import org.apache.ibatis.annotations.Param;

public interface CategoryDescriptionMapper {
	int deleteByPrimaryKey(@Param("categoryId") Integer categoryId, @Param("languageId") Integer languageId);

	int insert(CategoryDescription record);

	int insertSelective(CategoryDescription record);

	CategoryDescription selectByPrimaryKey(@Param("categoryId") Integer categoryId, @Param("languageId") Integer languageId);

	int updateByPrimaryKeySelective(CategoryDescription record);

	int updateByPrimaryKey(CategoryDescription record);

    CategoryDescription selectByNameAndLanguageId(@Param("name") String name, @Param("languageId") int languageId);
}