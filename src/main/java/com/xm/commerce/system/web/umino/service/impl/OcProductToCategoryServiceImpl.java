package com.xm.commerce.system.web.umino.service.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.xm.commerce.system.web.umino.mapper.OcProductToCategoryMapper;
import com.xm.commerce.system.entity.OcProductToCategory;
import com.xm.commerce.system.web.umino.service.OcProductToCategoryService;
@Service
public class OcProductToCategoryServiceImpl implements OcProductToCategoryService{

    @Resource
    private OcProductToCategoryMapper ocProductToCategoryMapper;

    @Override
    public int deleteByPrimaryKey(Integer productId,Integer categoryId) {
        return ocProductToCategoryMapper.deleteByPrimaryKey(productId,categoryId);
    }

    @Override
    public int insert(OcProductToCategory record) {
        return ocProductToCategoryMapper.insert(record);
    }

    @Override
    public int insertSelective(OcProductToCategory record) {
        return ocProductToCategoryMapper.insertSelective(record);
    }

}
