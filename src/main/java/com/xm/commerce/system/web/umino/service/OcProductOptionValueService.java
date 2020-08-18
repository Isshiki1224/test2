package com.xm.commerce.system.web.umino.service;

import com.xm.commerce.system.entity.OcProductOptionValue;
public interface OcProductOptionValueService{


    int deleteByPrimaryKey(Integer productOptionValueId);

    int insert(OcProductOptionValue record);

    int insertSelective(OcProductOptionValue record);

    OcProductOptionValue selectByPrimaryKey(Integer productOptionValueId);

    int updateByPrimaryKeySelective(OcProductOptionValue record);

    int updateByPrimaryKey(OcProductOptionValue record);

}
