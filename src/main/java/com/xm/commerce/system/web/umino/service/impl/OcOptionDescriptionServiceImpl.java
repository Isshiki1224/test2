package com.xm.commerce.system.web.umino.service.impl;

import com.xm.commerce.system.web.umino.service.OcOptionDescriptionService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.xm.commerce.system.web.umino.mapper.OcOptionDescriptionMapper;
import com.xm.commerce.system.entity.OcOptionDescription;

@Service
public class OcOptionDescriptionServiceImpl implements OcOptionDescriptionService {

    @Resource
    private OcOptionDescriptionMapper ocOptionDescriptionMapper;

    @Override
    public int deleteByPrimaryKey(Integer optionId,Integer languageId) {
        return ocOptionDescriptionMapper.deleteByPrimaryKey(optionId,languageId);
    }

    @Override
    public int insert(OcOptionDescription record) {
        return ocOptionDescriptionMapper.insert(record);
    }

    @Override
    public int insertSelective(OcOptionDescription record) {
        return ocOptionDescriptionMapper.insertSelective(record);
    }

    @Override
    public OcOptionDescription selectByPrimaryKey(Integer optionId,Integer languageId) {
        return ocOptionDescriptionMapper.selectByPrimaryKey(optionId,languageId);
    }

    @Override
    public int updateByPrimaryKeySelective(OcOptionDescription record) {
        return ocOptionDescriptionMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(OcOptionDescription record) {
        return ocOptionDescriptionMapper.updateByPrimaryKey(record);
    }

    @Override
    public OcOptionDescription selectByNameAndLanguage(String key, int i) {
        return ocOptionDescriptionMapper.selectByNameAndLanguage(key, i);
    }

}
