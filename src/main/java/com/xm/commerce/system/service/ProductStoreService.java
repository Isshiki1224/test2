package com.xm.commerce.system.service;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.xm.commerce.system.mapper.ecommerce.ProductStoreMapper;
import com.xm.commerce.system.entity.ecommerce.ProductStore;
@Service
public class ProductStoreService{

    @Resource
    private ProductStoreMapper productStoreMapper;

    
    public int deleteByPrimaryKey(Integer id) {
        return productStoreMapper.deleteByPrimaryKey(id);
    }

    
    public int insert(ProductStore record) {
        return productStoreMapper.insert(record);
    }

    
    public int insertSelective(ProductStore record) {
        return productStoreMapper.insertSelective(record);
    }

    
    public ProductStore selectByPrimaryKey(Integer id) {
        return productStoreMapper.selectByPrimaryKey(id);
    }

    
    public int updateByPrimaryKeySelective(ProductStore record) {
        return productStoreMapper.updateByPrimaryKeySelective(record);
    }

    
    public int updateByPrimaryKey(ProductStore record) {
        return productStoreMapper.updateByPrimaryKey(record);
    }

}
