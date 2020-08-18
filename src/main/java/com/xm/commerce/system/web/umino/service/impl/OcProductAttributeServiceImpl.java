package com.xm.commerce.system.web.umino.service.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.xm.commerce.system.web.umino.mapper.OcProductAttributeMapper;
import com.xm.commerce.system.entity.OcProductAttribute;
import com.xm.commerce.system.web.umino.service.OcProductAttributeService;
@Service
public class OcProductAttributeServiceImpl implements OcProductAttributeService{

    @Resource
    private OcProductAttributeMapper ocProductAttributeMapper;

    @Override
    public int deleteByPrimaryKey(Integer productId,Integer attributeId,Integer languageId) {
        return ocProductAttributeMapper.deleteByPrimaryKey(productId,attributeId,languageId);
    }

    @Override
    public int insert(OcProductAttribute record) {
        return ocProductAttributeMapper.insert(record);
    }

    @Override
    public int insertSelective(OcProductAttribute record) {
        return ocProductAttributeMapper.insertSelective(record);
    }

    @Override
    public OcProductAttribute selectByPrimaryKey(Integer productId,Integer attributeId,Integer languageId) {
        return ocProductAttributeMapper.selectByPrimaryKey(productId,attributeId,languageId);
    }

    @Override
    public int updateByPrimaryKeySelective(OcProductAttribute record) {
        return ocProductAttributeMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(OcProductAttribute record) {
        return ocProductAttributeMapper.updateByPrimaryKey(record);
    }

}
