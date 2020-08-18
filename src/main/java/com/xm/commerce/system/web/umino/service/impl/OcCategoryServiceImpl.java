package com.xm.commerce.system.web.umino.service.impl;

import com.xm.commerce.system.web.umino.service.OcCategoryService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.xm.commerce.system.entity.OcCategory;
import com.xm.commerce.system.web.umino.mapper.OcCategoryMapper;

@Service
public class OcCategoryServiceImpl implements OcCategoryService {

    @Resource
    private OcCategoryMapper ocCategoryMapper;

    @Override
    public int deleteByPrimaryKey(Integer categoryId) {
        return ocCategoryMapper.deleteByPrimaryKey(categoryId);
    }

    @Override
    public int insert(OcCategory record) {
        return ocCategoryMapper.insert(record);
    }

    @Override
    public int insertSelective(OcCategory record) {
        return ocCategoryMapper.insertSelective(record);
    }

    @Override
    public OcCategory selectByPrimaryKey(Integer categoryId) {
        return ocCategoryMapper.selectByPrimaryKey(categoryId);
    }

    @Override
    public int updateByPrimaryKeySelective(OcCategory record) {
        return ocCategoryMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(OcCategory record) {
        return ocCategoryMapper.updateByPrimaryKey(record);
    }

}
