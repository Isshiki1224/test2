package com.xm.commerce.system.web.umino.service;

import com.xm.commerce.system.entity.OcCategory;
import com.xm.commerce.system.entity.OcCategoryDescription;
public interface OcCategoryDescriptionService{


    int deleteByPrimaryKey(Integer categoryId,Integer languageId);

    int insert(OcCategoryDescription record);

    int insertSelective(OcCategoryDescription record);

    OcCategoryDescription selectByPrimaryKey(Integer categoryId,Integer languageId);

    int updateByPrimaryKeySelective(OcCategoryDescription record);

    int updateByPrimaryKey(OcCategoryDescription record);

    OcCategoryDescription selectByNameAndLanguage(String categoryName,Integer languageId);
}
