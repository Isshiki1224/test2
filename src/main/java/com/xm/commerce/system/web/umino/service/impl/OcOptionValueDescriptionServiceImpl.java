package com.xm.commerce.system.web.umino.service.impl;

import com.xm.commerce.system.web.umino.service.OcOptionValueDescriptionService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.xm.commerce.system.web.umino.mapper.OcOptionValueDescriptionMapper;
import com.xm.commerce.system.entity.OcOptionValueDescription;

@Service
public class OcOptionValueDescriptionServiceImpl implements OcOptionValueDescriptionService {

    @Resource
    private OcOptionValueDescriptionMapper ocOptionValueDescriptionMapper;

    @Override
    public int deleteByPrimaryKey(Integer optionValueId, Integer languageId) {
        return ocOptionValueDescriptionMapper.deleteByPrimaryKey(optionValueId, languageId);
    }

    @Override
    public int insert(OcOptionValueDescription record) {
        return ocOptionValueDescriptionMapper.insert(record);
    }

    @Override
    public int insertSelective(OcOptionValueDescription record) {
        return ocOptionValueDescriptionMapper.insertSelective(record);
    }

    @Override
    public OcOptionValueDescription selectByPrimaryKey(Integer optionValueId, Integer languageId) {
        return ocOptionValueDescriptionMapper.selectByPrimaryKey(optionValueId, languageId);
    }

    @Override
    public int updateByPrimaryKeySelective(OcOptionValueDescription record) {
        return ocOptionValueDescriptionMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(OcOptionValueDescription record) {
        return ocOptionValueDescriptionMapper.updateByPrimaryKey(record);
    }

    @Override
    public OcOptionValueDescription selectByNameAndLanguage(String value, Integer languageId) {
        return ocOptionValueDescriptionMapper.selectByNameAndLanguage(value, languageId);
    }

}

