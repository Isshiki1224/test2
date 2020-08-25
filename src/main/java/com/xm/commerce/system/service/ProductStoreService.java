package com.xm.commerce.system.service;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Sets;
import com.xm.commerce.common.exception.FileUploadException;
import com.xm.commerce.security.util.CurrentUserUtils;
import com.xm.commerce.system.mapper.ecommerce.ProductStoreMapper;
import com.xm.commerce.system.model.dto.FileUploadDto;
import com.xm.commerce.system.model.dto.PictureDto;
import com.xm.commerce.system.model.entity.ecommerce.ProductStore;
import com.xm.commerce.system.model.entity.ecommerce.User;
import com.xm.commerce.system.model.request.CategoryRequest;
import com.xm.commerce.system.model.response.ProductResponse;
import com.xm.commerce.system.util.FileUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.xm.commerce.system.constant.EcommerceConstant.SUPPORTED_PIC_SUFFIX;

@Slf4j
@Service
public class ProductStoreService {

    @Resource
    private ProductStoreMapper productStoreMapper;
    @Resource
    private CurrentUserUtils currentUserUtils;
    @Resource
    private FileUtil fileUtil;
    @Value("${ftp.ip}")
    private String ip;


    public int deleteByPrimaryKey(Integer id) {
        return productStoreMapper.deleteByPrimaryKey(id);
    }


    public int insert(ProductStore record) {
        return productStoreMapper.insert(record);
    }


    public int insertSelective(ProductStore record) throws Exception {

        User user = currentUserUtils.getCurrentUser();
        StringBuilder sb = new StringBuilder();
        String image = record.getImage();
        List<PictureDto> pictureDtoList = new ArrayList<>();
        String[] imgs = image.split(",");
        Set<String> imgSet = Sets.newHashSet(imgs);
        for (String s : imgs) {
            if (!s.startsWith("http://" + ip)) {
                InputStream inputStream = new URL(s).openStream();
                byte[] bytes = IOUtils.toByteArray(inputStream);
                pictureDtoList.add(new PictureDto(bytes, s));
                String extension = FilenameUtils.getExtension(s);
                if (Arrays.asList(SUPPORTED_PIC_SUFFIX).contains(extension)) {
                    throw new FileUploadException(ImmutableMap.of("格式不正确", extension));
                }
                imgSet.remove(s);
            }
        }
        List<FileUploadDto> fileUploadDtos = fileUtil.fileUploadByBytes(pictureDtoList, user.getUsername() + "/");
        imgSet.addAll(fileUploadDtos.stream().map(FileUploadDto::getUrl).collect(Collectors.toSet()));

        if (record.getMetaTagTitle() == null || "".equals(record.getMetaTagTitle())) {
            record.setMetaTagTitle(record.getProductName());
        }
        record.setUploadOpencart(false);
        record.setUploadShopify(false);
        record.setImage(String.join(",", imgSet));
        record.setDataAdded(new Date());
        record.setDataModified(new Date());
        return productStoreMapper.insertSelective(record);
    }


    public ProductStore selectByPrimaryKey(Integer id) {
        return productStoreMapper.selectByPrimaryKey(id);
    }

    public ProductResponse selectRespByPrimaryKey(Integer id) {
        ProductStore productStore = productStoreMapper.selectByPrimaryKey(id);
        return new ProductResponse(productStore);
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

    public List<ProductResponse> selectByCategory(CategoryRequest categoryRequest) {
        List<ProductStore> productStores = null;
//        if (categoryRequest.getPage() != null && categoryRequest.getPageSize() != null) {
//            PageHelper.startPage(categoryRequest.getPage(), categoryRequest.getPageSize());
//            productStores = productStoreMapper.selectByCategory(categoryRequest);
//        }
        productStores = productStoreMapper.selectByCategory(categoryRequest);
        List<ProductResponse> responses = new ArrayList<>();
        productStores.forEach(productStore -> {
            ProductResponse productResponse = new ProductResponse(productStore);
            responses.add(productResponse);
        });

        return responses;
    }

    public int deleteByBatch(List<Integer> ids) {
        if (ids.isEmpty()) {
            return 0;
        }
        return productStoreMapper.deleteByBatch(ids);
    }
}





