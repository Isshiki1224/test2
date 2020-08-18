package com.xm.commerce.system.web.umino.service;

import com.xm.commerce.system.entity.OcCategory;
public interface OcCategoryService{


    int deleteByPrimaryKey(Integer categoryId);

    int insert(OcCategory record);

    int insertSelective(OcCategory record);

    OcCategory selectByPrimaryKey(Integer categoryId);

    int updateByPrimaryKeySelective(OcCategory record);

    int updateByPrimaryKey(OcCategory record);

}
