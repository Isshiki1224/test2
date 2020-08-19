package com.xm.commerce.system.mapper.umino;

import com.xm.commerce.system.model.entity.umino.OptionValueDescription;
import org.apache.ibatis.annotations.Param;

public interface OptionValueDescriptionMapper {
	int deleteByPrimaryKey(@Param("optionValueId") Integer optionValueId, @Param("languageId") Integer languageId);

	int insert(OptionValueDescription record);

	int insertSelective(OptionValueDescription record);

	OptionValueDescription selectByPrimaryKey(@Param("optionValueId") Integer optionValueId, @Param("languageId") Integer languageId);

	int updateByPrimaryKeySelective(OptionValueDescription record);

	int updateByPrimaryKey(OptionValueDescription record);

    OptionValueDescription selectByNameAndLanguageId(@Param("name") String name, @Param("languageId") int languageId);
}