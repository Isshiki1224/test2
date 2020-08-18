package com.xm.commerce.system.web.umino.service.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.xm.commerce.system.web.umino.mapper.OcProductOptionValueMapper;
import com.xm.commerce.system.entity.OcProductOptionValue;
import com.xm.commerce.system.web.umino.service.OcProductOptionValueService;
@Service
public class OcProductOptionValueServiceImpl implements OcProductOptionValueService{

    @Resource
    private OcProductOptionValueMapper ocProductOptionValueMapper;

    @Override
    public int deleteByPrimaryKey(Integer productOptionValueId) {
        return ocProductOptionValueMapper.deleteByPrimaryKey(productOptionValueId);
    }

    @Override
    public int insert(OcProductOptionValue record) {
        return ocProductOptionValueMapper.insert(record);
    }

    @Override
    public int insertSelective(OcProductOptionValue record) {
        return ocProductOptionValueMapper.insertSelective(record);
    }

    @Override
    public OcProductOptionValue selectByPrimaryKey(Integer productOptionValueId) {
        return ocProductOptionValueMapper.selectByPrimaryKey(productOptionValueId);
    }

    @Override
    public int updateByPrimaryKeySelective(OcProductOptionValue record) {
        return ocProductOptionValueMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(OcProductOptionValue record) {
        return ocProductOptionValueMapper.updateByPrimaryKey(record);
    }

}
