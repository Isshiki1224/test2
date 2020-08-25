package com.xm.commerce.system.service;

import com.xm.commerce.security.util.CurrentUserUtils;
import com.xm.commerce.system.mapper.ecommerce.ProductStoreMapper;
import com.xm.commerce.system.model.entity.ecommerce.ProductStore;
import com.xm.commerce.system.model.entity.ecommerce.User;
import com.xm.commerce.system.model.request.CategoryRequest;
import com.xm.commerce.system.util.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Date;
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
                InputStream inputStream = new URL(s).openStream();


//                File file = new File(EcommerceConstant.FILE_UPLOAD_PATH + user.getId() + "." + user.getUsername() + s.substring(s.lastIndexOf("/")));
//                if (!file.getParentFile().exists()) {
//                    boolean mkdir = file.getParentFile().mkdir();
//                    if (mkdir) {
//                        boolean newFile = file.createNewFile();
//                        log.info("网络图片{}转路径{}{}", s, file.getPath(), (newFile ? "成功" : "失败"));
//                    } else {
//                        throw new ResourceNotFoundException(ImmutableMap.of("文件夹创建失败", false));
//                    }
//                } else {
//                    boolean newFile = file.createNewFile();
//                    log.info("网络图片{" + s + "}转路径{" + file.getPath() + "}" + (newFile ? "成功" : "失败"));
//                }
                sb.append(sb).append(",");
            }
        }

        if (record.getMetaTagTitle() == null || "".equals(record.getMetaTagTitle())) {
            record.setMetaTagTitle(record.getProductName());
        }
        record.setUploadOpencart(false);
        record.setUploadShopify(false);
        record.setDataAdded(new Date());
        record.setDataModified(new Date());
        return productStoreMapper.insertSelective(record);
    }


    public ProductStore selectByPrimaryKey(Integer id) {
        return productStoreMapper.selectByPrimaryKey(id);
    }


    public int updateByPrimaryKeySelective(ProductStore record) {
        if (record.getMetaTagTitle() == null) {
            record.setMetaTagTitle(record.getProductName());
        }
        record.setDataModified(new Date());
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





