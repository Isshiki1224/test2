package com.xm.commerce.system.web.umino.service.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.xm.commerce.system.entity.OcOption;
import com.xm.commerce.system.web.umino.mapper.OcOptionMapper;
import com.xm.commerce.system.web.umino.service.OcOptionService;
@Service
public class OcOptionServiceImpl implements OcOptionService{

    @Resource
    private OcOptionMapper ocOptionMapper;

    @Override
    public int deleteByPrimaryKey(Integer optionId) {
        return ocOptionMapper.deleteByPrimaryKey(optionId);
    }

    @Override
    public int insert(OcOption record) {
        return ocOptionMapper.insert(record);
    }

    @Override
    public int insertSelective(OcOption record) {
        return ocOptionMapper.insertSelective(record);
    }

    @Override
    public OcOption selectByPrimaryKey(Integer optionId) {
        return ocOptionMapper.selectByPrimaryKey(optionId);
    }

    @Override
    public int updateByPrimaryKeySelective(OcOption record) {
        return ocOptionMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(OcOption record) {
        return ocOptionMapper.updateByPrimaryKey(record);
    }

    @Override
    public OcOption selectByNameAndLanguage(String key, int i) {
        return ocOptionMapper.selectByNameAndLanguage(key, i);
    }

}
