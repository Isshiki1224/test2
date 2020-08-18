package com.xm.commerce.system.web.umino.service;

import com.xm.commerce.system.entity.OcProductImage;
public interface OcProductImageService{


    int deleteByPrimaryKey(Integer productImageId);

    int insert(OcProductImage record);

    int insertSelective(OcProductImage record);

    OcProductImage selectByPrimaryKey(Integer productImageId);

    int updateByPrimaryKeySelective(OcProductImage record);

    int updateByPrimaryKey(OcProductImage record);

}
