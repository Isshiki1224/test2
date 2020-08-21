package com.xm.commerce.system.service;

import com.github.pagehelper.PageHelper;
import com.xm.commerce.system.model.request.CategoryRequest;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.xm.commerce.system.mapper.ecommerce.ProductStoreMapper;
import com.xm.commerce.system.model.entity.ecommerce.ProductStore;

import java.util.List;

@Service
public class ProductStoreService {

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

    public List<ProductStore> selectByCategory(CategoryRequest categoryRequest) {
        List<ProductStore> productStores = null;
        if (categoryRequest.getPage() != null && categoryRequest.getPageSize() != null) {
            PageHelper.startPage(categoryRequest.getPage(), categoryRequest.getPageSize());
            productStores = productStoreMapper.selectByCategory(categoryRequest);
        }

        return productStores;
    }

    public int deleteByBatch(List<Integer> ids) {
        if (ids.isEmpty()){
            return 0;
        }
        return productStoreMapper.deleteByBatch(ids);
    }
}



