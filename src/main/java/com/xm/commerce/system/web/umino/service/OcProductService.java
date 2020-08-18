package com.xm.commerce.system.web.umino.service;

import com.xm.commerce.system.entity.OcProduct;
public interface OcProductService{


    int deleteByPrimaryKey(Integer productId);

    int insert(OcProduct record);

    int insertSelective(OcProduct record);

    OcProduct selectByPrimaryKey(Integer productId);

    int updateByPrimaryKeySelective(OcProduct record);

    int updateByPrimaryKey(OcProduct record);

}
