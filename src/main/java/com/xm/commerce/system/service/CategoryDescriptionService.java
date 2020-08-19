package com.xm.commerce.system.service;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.xm.commerce.system.entity.umino.CategoryDescription;
import com.xm.commerce.system.mapper.umino.CategoryDescriptionMapper;

@Service
public class CategoryDescriptionService {

	@Resource
	private CategoryDescriptionMapper categoryDescriptionMapper;


	public int deleteByPrimaryKey(Integer categoryId, Integer languageId) {
		return categoryDescriptionMapper.deleteByPrimaryKey(categoryId, languageId);
	}


	public int insert(CategoryDescription record) {
		return categoryDescriptionMapper.insert(record);
	}


	public int insertSelective(CategoryDescription record) {
		return categoryDescriptionMapper.insertSelective(record);
	}


	public CategoryDescription selectByPrimaryKey(Integer categoryId, Integer languageId) {
		return categoryDescriptionMapper.selectByPrimaryKey(categoryId, languageId);
	}


	public int updateByPrimaryKeySelective(CategoryDescription record) {
		return categoryDescriptionMapper.updateByPrimaryKeySelective(record);
	}


	public int updateByPrimaryKey(CategoryDescription record) {
		return categoryDescriptionMapper.updateByPrimaryKey(record);
	}

}

