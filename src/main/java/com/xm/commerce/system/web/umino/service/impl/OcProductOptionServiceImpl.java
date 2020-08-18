package com.xm.commerce.system.web.umino.service.impl;

import com.xm.commerce.system.web.umino.service.OcProductOptionService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.xm.commerce.system.web.umino.mapper.OcProductOptionMapper;
import com.xm.commerce.system.entity.OcProductOption;

@Service
public class OcProductOptionServiceImpl implements OcProductOptionService {

    @Resource
    private OcProductOptionMapper ocProductOptionMapper;

    @Override
    public int deleteByPrimaryKey(Integer productOptionId) {
        return ocProductOptionMapper.deleteByPrimaryKey(productOptionId);
    }

    @Override
    public int insert(OcProductOption record) {
        return ocProductOptionMapper.insert(record);
    }

    @Override
    public int insertSelective(OcProductOption record) {
        return ocProductOptionMapper.insertSelective(record);
    }

    @Override
    public OcProductOption selectByPrimaryKey(Integer productOptionId) {
        return ocProductOptionMapper.selectByPrimaryKey(productOptionId);
    }

    @Override
    public int updateByPrimaryKeySelective(OcProductOption record) {
        return ocProductOptionMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(OcProductOption record) {
        return ocProductOptionMapper.updateByPrimaryKey(record);
    }

}
