package com.xm.commerce.system.web.umino.service.impl;

import com.xm.commerce.system.web.umino.service.OcProductDescriptionService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.xm.commerce.system.entity.OcProductDescription;
import com.xm.commerce.system.web.umino.mapper.OcProductDescriptionMapper;

@Service
public class OcProductDescriptionServiceImpl implements OcProductDescriptionService {

    @Resource
    private OcProductDescriptionMapper ocProductDescriptionMapper;

    @Override
    public int deleteByPrimaryKey(Integer productId,Integer languageId) {
        return ocProductDescriptionMapper.deleteByPrimaryKey(productId,languageId);
    }

    @Override
    public int insert(OcProductDescription record) {
        return ocProductDescriptionMapper.insert(record);
    }

    @Override
    public int insertSelective(OcProductDescription record) {
        return ocProductDescriptionMapper.insertSelective(record);
    }

    @Override
    public OcProductDescription selectByPrimaryKey(Integer productId,Integer languageId) {
        return ocProductDescriptionMapper.selectByPrimaryKey(productId,languageId);
    }

    @Override
    public int updateByPrimaryKeySelective(OcProductDescription record) {
        return ocProductDescriptionMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(OcProductDescription record) {
        return ocProductDescriptionMapper.updateByPrimaryKey(record);
    }

}
