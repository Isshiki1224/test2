package com.xm.commerce.system.web.umino.service;

import com.xm.commerce.system.entity.OcProductOption;
public interface OcProductOptionService{


    int deleteByPrimaryKey(Integer productOptionId);

    int insert(OcProductOption record);

    int insertSelective(OcProductOption record);

    OcProductOption selectByPrimaryKey(Integer productOptionId);

    int updateByPrimaryKeySelective(OcProductOption record);

    int updateByPrimaryKey(OcProductOption record);

}
