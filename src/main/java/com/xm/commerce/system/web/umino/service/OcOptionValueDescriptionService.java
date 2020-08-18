package com.xm.commerce.system.web.umino.service;

import com.xm.commerce.system.entity.OcOptionValueDescription;

public interface OcOptionValueDescriptionService {


    int deleteByPrimaryKey(Integer optionValueId, Integer languageId);

    int insert(OcOptionValueDescription record);

    int insertSelective(OcOptionValueDescription record);

    OcOptionValueDescription selectByPrimaryKey(Integer optionValueId, Integer languageId);

    int updateByPrimaryKeySelective(OcOptionValueDescription record);

    int updateByPrimaryKey(OcOptionValueDescription record);

    OcOptionValueDescription selectByNameAndLanguage(String value, Integer languageId);
}

