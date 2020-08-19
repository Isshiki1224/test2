package com.xm.commerce.system.mapper.umino;

import com.xm.commerce.system.entity.umino.Option;

public interface OptionMapper {
	int deleteByPrimaryKey(Integer optionId);

	int insert(Option record);

	int insertSelective(Option record);

	Option selectByPrimaryKey(Integer optionId);

	int updateByPrimaryKeySelective(Option record);

	int updateByPrimaryKey(Option record);
}