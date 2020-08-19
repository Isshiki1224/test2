package com.xm.commerce.system.mapper.umino;

import com.xm.commerce.system.model.entity.umino.OptionDescription;
import org.apache.ibatis.annotations.Param;

public interface OptionDescriptionMapper {
	int deleteByPrimaryKey(@Param("optionId") Integer optionId, @Param("languageId") Integer languageId);

	int insert(OptionDescription record);

	int insertSelective(OptionDescription record);

	OptionDescription selectByPrimaryKey(@Param("optionId") Integer optionId, @Param("languageId") Integer languageId);

	int updateByPrimaryKeySelective(OptionDescription record);

	int updateByPrimaryKey(OptionDescription record);

    OptionDescription selectByNameAndLanguageId(@Param("name") String name, @Param("languageId") int languageId);
}