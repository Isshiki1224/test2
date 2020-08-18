package com.xm.commerce.system.web.umino.service;

import com.xm.commerce.system.entity.OcOption;

public interface OcOptionService {


    int deleteByPrimaryKey(Integer optionId);

    int insert(OcOption record);

    int insertSelective(OcOption record);

    OcOption selectByPrimaryKey(Integer optionId);

    int updateByPrimaryKeySelective(OcOption record);

    int updateByPrimaryKey(OcOption record);

    OcOption selectByNameAndLanguage(String key, int i);
}

