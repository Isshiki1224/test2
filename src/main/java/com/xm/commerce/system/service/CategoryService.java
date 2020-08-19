package com.xm.commerce.system.service;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.xm.commerce.system.entity.umino.Category;
import com.xm.commerce.system.mapper.umino.CategoryMapper;
@Service
public class CategoryService{

    @Resource
    private CategoryMapper categoryMapper;

    
    public int deleteByPrimaryKey(Integer categoryId) {
        return categoryMapper.deleteByPrimaryKey(categoryId);
    }

    
    public int insert(Category record) {
        return categoryMapper.insert(record);
    }

    
    public int insertSelective(Category record) {
        return categoryMapper.insertSelective(record);
    }

    
    public Category selectByPrimaryKey(Integer categoryId) {
        return categoryMapper.selectByPrimaryKey(categoryId);
    }

    
    public int updateByPrimaryKeySelective(Category record) {
        return categoryMapper.updateByPrimaryKeySelective(record);
    }

    
    public int updateByPrimaryKey(Category record) {
        return categoryMapper.updateByPrimaryKey(record);
    }

}
