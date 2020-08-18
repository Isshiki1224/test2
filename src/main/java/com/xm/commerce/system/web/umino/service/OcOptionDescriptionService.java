package com.xm.commerce.system.web.umino.service;

import com.xm.commerce.system.entity.OcOptionDescription;
public interface OcOptionDescriptionService{


    int deleteByPrimaryKey(Integer optionId,Integer languageId);

    int insert(OcOptionDescription record);

    int insertSelective(OcOptionDescription record);

    OcOptionDescription selectByPrimaryKey(Integer optionId,Integer languageId);

    int updateByPrimaryKeySelective(OcOptionDescription record);

    int updateByPrimaryKey(OcOptionDescription record);

    OcOptionDescription selectByNameAndLanguage(String key, int i);
}
