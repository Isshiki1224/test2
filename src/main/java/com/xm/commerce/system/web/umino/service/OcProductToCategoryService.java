package com.xm.commerce.system.web.umino.service;

import com.xm.commerce.system.entity.OcProductToCategory;
public interface OcProductToCategoryService{


    int deleteByPrimaryKey(Integer productId,Integer categoryId);

    int insert(OcProductToCategory record);

    int insertSelective(OcProductToCategory record);

}
