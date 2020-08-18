package com.xm.commerce.system.web.umino.service.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.xm.commerce.system.entity.OcOptionValue;
import com.xm.commerce.system.web.umino.mapper.OcOptionValueMapper;
import com.xm.commerce.system.web.umino.service.OcOptionValueService;

import java.util.List;

@Service
public class OcOptionValueServiceImpl implements OcOptionValueService{

    @Resource
    private OcOptionValueMapper ocOptionValueMapper;

    @Override
    public int deleteByPrimaryKey(Integer optionValueId) {
        return ocOptionValueMapper.deleteByPrimaryKey(optionValueId);
    }

    @Override
    public int insert(OcOptionValue record) {
        return ocOptionValueMapper.insert(record);
    }

    @Override
    public int insertSelective(OcOptionValue record) {
        return ocOptionValueMapper.insertSelective(record);
    }

    @Override
    public OcOptionValue selectByPrimaryKey(Integer optionValueId) {
        return ocOptionValueMapper.selectByPrimaryKey(optionValueId);
    }

    @Override
    public int updateByPrimaryKeySelective(OcOptionValue record) {
        return ocOptionValueMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(OcOptionValue record) {
        return ocOptionValueMapper.updateByPrimaryKey(record);
    }

    @Override
    public List<OcOptionValue> selectByOptionId(Integer optionId) {
        return ocOptionValueMapper.selectByOptionId(optionId);
    }

}
