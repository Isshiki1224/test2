package com.xm.commerce.system.service;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.xm.commerce.system.mapper.umino.OptionDescriptionMapper;
import com.xm.commerce.system.entity.umino.OptionDescription;

@Service
public class OptionDescriptionService {

	@Resource
	private OptionDescriptionMapper optionDescriptionMapper;


	public int deleteByPrimaryKey(Integer optionId, Integer languageId) {
		return optionDescriptionMapper.deleteByPrimaryKey(optionId, languageId);
	}


	public int insert(OptionDescription record) {
		return optionDescriptionMapper.insert(record);
	}


	public int insertSelective(OptionDescription record) {
		return optionDescriptionMapper.insertSelective(record);
	}


	public OptionDescription selectByPrimaryKey(Integer optionId, Integer languageId) {
		return optionDescriptionMapper.selectByPrimaryKey(optionId, languageId);
	}


	public int updateByPrimaryKeySelective(OptionDescription record) {
		return optionDescriptionMapper.updateByPrimaryKeySelective(record);
	}


	public int updateByPrimaryKey(OptionDescription record) {
		return optionDescriptionMapper.updateByPrimaryKey(record);
	}

}

