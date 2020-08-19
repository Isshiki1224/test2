package com.xm.commerce.system.mapper.umino;

import com.xm.commerce.system.entity.umino.OptionValue;

public interface OptionValueMapper {
	int deleteByPrimaryKey(Integer optionValueId);

	int insert(OptionValue record);

	int insertSelective(OptionValue record);

	OptionValue selectByPrimaryKey(Integer optionValueId);

	int updateByPrimaryKeySelective(OptionValue record);

	int updateByPrimaryKey(OptionValue record);
}