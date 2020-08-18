package com.xm.commerce.system.local.umino.service.impl;

import com.github.pagehelper.PageHelper;
import com.xm.commerce.system.request.CategoryRequest;
import com.xm.commerce.system.response.OcProductStoreResponse;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.xm.commerce.system.entity.OcProductStore;
import com.xm.commerce.system.local.umino.mapper.OcProductStoreMapper;
import com.xm.commerce.system.local.umino.service.OcProductStoreService;

import java.util.List;

@Service
public class OcProductStoreServiceImpl implements OcProductStoreService {

    @Resource
    private OcProductStoreMapper ocProductStoreMapper;

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return ocProductStoreMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(OcProductStore record) {
        return ocProductStoreMapper.insert(record);
    }

    @Override
    public int insertSelective(OcProductStore record) {
        return ocProductStoreMapper.insertSelective(record);
    }

    @Override
    public OcProductStore selectByPrimaryKey(Integer id) {
        return ocProductStoreMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(OcProductStore record) {
        return ocProductStoreMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(OcProductStore record) {
        return ocProductStoreMapper.updateByPrimaryKey(record);
    }

    @Override
    public List<OcProductStoreResponse> selectByCategory(CategoryRequest categoryRequest) {
        PageHelper.startPage(categoryRequest.getPage(), categoryRequest.getPageSize());
        return ocProductStoreMapper.selectByCategory(categoryRequest);
    }

}





