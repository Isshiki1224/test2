package com.xm.commerce.system.web.umino.service.impl;

import com.xm.commerce.system.web.umino.service.OcProductService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.xm.commerce.system.web.umino.mapper.OcProductMapper;
import com.xm.commerce.system.entity.OcProduct;

@Service
public class OcProductServiceImpl implements OcProductService {

    @Resource
    private OcProductMapper ocProductMapper;

    @Override
    public int deleteByPrimaryKey(Integer productId) {
        return ocProductMapper.deleteByPrimaryKey(productId);
    }

    @Override
    public int insert(OcProduct record) {
        return ocProductMapper.insert(record);
    }

    @Override
    public int insertSelective(OcProduct record) {
        return ocProductMapper.insertSelective(record);
    }

    @Override
    public OcProduct selectByPrimaryKey(Integer productId) {
        return ocProductMapper.selectByPrimaryKey(productId);
    }

    @Override
    public int updateByPrimaryKeySelective(OcProduct record) {
        return ocProductMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(OcProduct record) {
        return ocProductMapper.updateByPrimaryKey(record);
    }

}
