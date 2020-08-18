package com.xm.commerce.system.web.umino.service;

import com.xm.commerce.system.entity.OcProductDescription;
public interface OcProductDescriptionService{


    int deleteByPrimaryKey(Integer productId,Integer languageId);

    int insert(OcProductDescription record);

    int insertSelective(OcProductDescription record);

    OcProductDescription selectByPrimaryKey(Integer productId,Integer languageId);

    int updateByPrimaryKeySelective(OcProductDescription record);

    int updateByPrimaryKey(OcProductDescription record);

}
