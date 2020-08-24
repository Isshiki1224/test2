package com.xm.commerce.system.service;

import com.github.pagehelper.PageHelper;
import com.oracle.tools.packager.Log;
import com.xm.commerce.system.model.request.CategoryRequest;
import com.xm.commerce.system.util.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.xm.commerce.system.mapper.ecommerce.ProductStoreMapper;
import com.xm.commerce.system.model.entity.ecommerce.ProductStore;

import java.util.List;

@Slf4j
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
        if (record.getMetaTagTitle() == null){
            record.setMetaTagTitle(record.getProductName());
        }
        record.setUploadOpencart(false);
        record.setUploadShopify(false);
        record.setDataAdded(DateUtil.dateNow());
        record.setDataModified(DateUtil.dateNow());
        return productStoreMapper.insertSelective(record);
    }


    public ProductStore selectByPrimaryKey(Integer id) {
        return productStoreMapper.selectByPrimaryKey(id);
    }


    public int updateByPrimaryKeySelective(ProductStore record) {
        if (record.getMetaTagTitle() == null){
            record.setMetaTagTitle(record.getProductName());
        }
        record.setDataModified(DateUtil.dateNow());
        return productStoreMapper.updateByPrimaryKeySelective(record);
    }


    public int updateByPrimaryKey(ProductStore record) {
        return productStoreMapper.updateByPrimaryKey(record);
    }

    public List<ProductStore> selectByCategory(CategoryRequest categoryRequest) {
        List<ProductStore> productStores = null;
//        if (categoryRequest.getPage() != null && categoryRequest.getPageSize() != null) {
//            PageHelper.startPage(categoryRequest.getPage(), categoryRequest.getPageSize());
//            productStores = productStoreMapper.selectByCategory(categoryRequest);
//        }
        productStores = productStoreMapper.selectByCategory(categoryRequest);
        log.info(productStores.toString());
        return productStores;
    }

    public int deleteByBatch(List<Integer> ids) {
        if (ids.isEmpty()) {
            return 0;
        }
        return productStoreMapper.deleteByBatch(ids);
    }
}





