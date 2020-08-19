package com.xm.commerce.system.service;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.xm.commerce.system.mapper.umino.OptionValueDescriptionMapper;
import com.xm.commerce.system.entity.umino.OptionValueDescription;

@Service
public class OptionValueDescriptionService {

	@Resource
	private OptionValueDescriptionMapper optionValueDescriptionMapper;


	public int deleteByPrimaryKey(Integer optionValueId, Integer languageId) {
		return optionValueDescriptionMapper.deleteByPrimaryKey(optionValueId, languageId);
	}


	public int insert(OptionValueDescription record) {
		return optionValueDescriptionMapper.insert(record);
	}


	public int insertSelective(OptionValueDescription record) {
		return optionValueDescriptionMapper.insertSelective(record);
	}


	public OptionValueDescription selectByPrimaryKey(Integer optionValueId, Integer languageId) {
		return optionValueDescriptionMapper.selectByPrimaryKey(optionValueId, languageId);
	}


	public int updateByPrimaryKeySelective(OptionValueDescription record) {
		return optionValueDescriptionMapper.updateByPrimaryKeySelective(record);
	}


	public int updateByPrimaryKey(OptionValueDescription record) {
		return optionValueDescriptionMapper.updateByPrimaryKey(record);
	}

}

