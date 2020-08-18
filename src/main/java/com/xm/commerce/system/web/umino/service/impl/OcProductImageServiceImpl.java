package com.xm.commerce.system.web.umino.service.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.xm.commerce.system.web.umino.mapper.OcProductImageMapper;
import com.xm.commerce.system.entity.OcProductImage;
import com.xm.commerce.system.web.umino.service.OcProductImageService;
@Service
public class OcProductImageServiceImpl implements OcProductImageService{

    @Resource
    private OcProductImageMapper ocProductImageMapper;

    @Override
    public int deleteByPrimaryKey(Integer productImageId) {
        return ocProductImageMapper.deleteByPrimaryKey(productImageId);
    }

    @Override
    public int insert(OcProductImage record) {
        return ocProductImageMapper.insert(record);
    }

    @Override
    public int insertSelective(OcProductImage record) {
        return ocProductImageMapper.insertSelective(record);
    }

    @Override
    public OcProductImage selectByPrimaryKey(Integer productImageId) {
        return ocProductImageMapper.selectByPrimaryKey(productImageId);
    }

    @Override
    public int updateByPrimaryKeySelective(OcProductImage record) {
        return ocProductImageMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(OcProductImage record) {
        return ocProductImageMapper.updateByPrimaryKey(record);
    }

}
