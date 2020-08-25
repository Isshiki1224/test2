package com.xm.commerce.system.service;

import com.github.pagehelper.PageHelper;
import com.google.common.collect.ImmutableMap;
import com.oracle.tools.packager.Log;
import com.xm.commerce.common.exception.ResourceNotFoundException;
import com.xm.commerce.security.util.CurrentUserUtils;
import com.xm.commerce.system.constant.EcommerceConstant;
import com.xm.commerce.system.model.entity.ecommerce.User;
import com.xm.commerce.system.model.request.CategoryRequest;
import com.xm.commerce.system.util.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import com.xm.commerce.system.mapper.ecommerce.ProductStoreMapper;
import com.xm.commerce.system.model.entity.ecommerce.ProductStore;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Slf4j
@Service
public class ProductStoreService {

    @Resource
    private ProductStoreMapper productStoreMapper;
    @Resource
    CurrentUserUtils currentUserUtils;


    public int deleteByPrimaryKey(Integer id) {
        return productStoreMapper.deleteByPrimaryKey(id);
    }


    public int insert(ProductStore record) {
        return productStoreMapper.insert(record);
    }


    public int insertSelective(ProductStore record) throws IOException {

        User user = currentUserUtils.getCurrentUser();
        StringBuilder sb = new StringBuilder();
        String image = record.getImage();
        for (String s : image.split(",")) {
            if (s.startsWith("http")) {
                File file = new File(EcommerceConstant.FILE_UPLOAD_PATH + user.getId() + "." + user.getUsername() + s.substring(s.lastIndexOf("/")));
                if (!file.getParentFile().exists()) {
                    boolean mkdir = file.getParentFile().mkdir();
                    if (mkdir) {
                        boolean newFile = file.createNewFile();
                        log.info("网络图片{" + s + "}转路径{" + file.getPath() + "}" + (newFile ? "成功" : "失败"));
                    } else {
                        throw new ResourceNotFoundException(ImmutableMap.of("文件夹创建失败", false));
                    }
                } else {
                    boolean newFile = file.createNewFile();
                    log.info("网络图片{" + s + "}转路径{" + file.getPath() + "}" + (newFile ? "成功" : "失败"));
                }
                sb.append(sb).append(",");
            }
        }

        if (record.getMetaTagTitle() == null || "".equals(record.getMetaTagTitle())) {
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
        if (record.getMetaTagTitle() == null) {
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





