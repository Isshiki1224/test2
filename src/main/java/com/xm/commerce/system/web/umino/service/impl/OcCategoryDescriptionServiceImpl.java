package com.xm.commerce.system.web.umino.service.impl;

import com.xm.commerce.system.web.umino.service.OcCategoryDescriptionService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.xm.commerce.system.web.umino.mapper.OcCategoryDescriptionMapper;
import com.xm.commerce.system.entity.OcCategoryDescription;

@Service
public class OcCategoryDescriptionServiceImpl implements OcCategoryDescriptionService {

    @Resource
    private OcCategoryDescriptionMapper ocCategoryDescriptionMapper;

    @Override
    public int deleteByPrimaryKey(Integer categoryId,Integer languageId) {
        return ocCategoryDescriptionMapper.deleteByPrimaryKey(categoryId,languageId);
    }

    @Override
    public int insert(OcCategoryDescription record) {
        return ocCategoryDescriptionMapper.insert(record);
    }

    @Override
    public int insertSelective(OcCategoryDescription record) {
        return ocCategoryDescriptionMapper.insertSelective(record);
    }

    @Override
    public OcCategoryDescription selectByPrimaryKey(Integer categoryId,Integer languageId) {
        return ocCategoryDescriptionMapper.selectByPrimaryKey(categoryId,languageId);
    }

    @Override
    public int updateByPrimaryKeySelective(OcCategoryDescription record) {
        return ocCategoryDescriptionMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(OcCategoryDescription record) {
        return ocCategoryDescriptionMapper.updateByPrimaryKey(record);
    }

    @Override
    public OcCategoryDescription selectByNameAndLanguage(String categoryName,Integer languageId) {
        return ocCategoryDescriptionMapper.selectByNameAndLanguage(categoryName, languageId);
    }

}
