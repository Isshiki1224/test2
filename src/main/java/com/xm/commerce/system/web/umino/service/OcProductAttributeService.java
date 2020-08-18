package com.xm.commerce.system.web.umino.service;

import com.xm.commerce.system.entity.OcProductAttribute;
public interface OcProductAttributeService{


    int deleteByPrimaryKey(Integer productId,Integer attributeId,Integer languageId);

    int insert(OcProductAttribute record);

    int insertSelective(OcProductAttribute record);

    OcProductAttribute selectByPrimaryKey(Integer productId,Integer attributeId,Integer languageId);

    int updateByPrimaryKeySelective(OcProductAttribute record);

    int updateByPrimaryKey(OcProductAttribute record);

}
