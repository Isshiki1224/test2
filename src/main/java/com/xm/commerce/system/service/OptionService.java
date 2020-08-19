package com.xm.commerce.system.service;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.xm.commerce.system.entity.umino.Option;
import com.xm.commerce.system.mapper.umino.OptionMapper;

@Service
public class OptionService {

	@Resource
	private OptionMapper optionMapper;


	public int deleteByPrimaryKey(Integer optionId) {
		return optionMapper.deleteByPrimaryKey(optionId);
	}


	public int insert(Option record) {
		return optionMapper.insert(record);
	}


	public int insertSelective(Option record) {
		return optionMapper.insertSelective(record);
	}


	public Option selectByPrimaryKey(Integer optionId) {
		return optionMapper.selectByPrimaryKey(optionId);
	}


	public int updateByPrimaryKeySelective(Option record) {
		return optionMapper.updateByPrimaryKeySelective(record);
	}


	public int updateByPrimaryKey(Option record) {
		return optionMapper.updateByPrimaryKey(record);
	}

}

