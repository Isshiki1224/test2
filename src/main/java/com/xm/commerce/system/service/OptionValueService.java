package com.xm.commerce.system.service;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.xm.commerce.system.mapper.umino.OptionValueMapper;
import com.xm.commerce.system.entity.umino.OptionValue;

@Service
public class OptionValueService {

	@Resource
	private OptionValueMapper optionValueMapper;


	public int deleteByPrimaryKey(Integer optionValueId) {
		return optionValueMapper.deleteByPrimaryKey(optionValueId);
	}


	public int insert(OptionValue record) {
		return optionValueMapper.insert(record);
	}


	public int insertSelective(OptionValue record) {
		return optionValueMapper.insertSelective(record);
	}


	public OptionValue selectByPrimaryKey(Integer optionValueId) {
		return optionValueMapper.selectByPrimaryKey(optionValueId);
	}


	public int updateByPrimaryKeySelective(OptionValue record) {
		return optionValueMapper.updateByPrimaryKeySelective(record);
	}


	public int updateByPrimaryKey(OptionValue record) {
		return optionValueMapper.updateByPrimaryKey(record);
	}

}

