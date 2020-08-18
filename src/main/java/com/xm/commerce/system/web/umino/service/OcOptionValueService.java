package com.xm.commerce.system.web.umino.service;

import com.xm.commerce.system.entity.OcOptionValue;

import java.util.List;

public interface OcOptionValueService {


    int deleteByPrimaryKey(Integer optionValueId);

    int insert(OcOptionValue record);

    int insertSelective(OcOptionValue record);

    OcOptionValue selectByPrimaryKey(Integer optionValueId);

    int updateByPrimaryKeySelective(OcOptionValue record);

    int updateByPrimaryKey(OcOptionValue record);

    List<OcOptionValue> selectByOptionId(Integer optionId);
}

