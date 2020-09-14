package com.xm.commerce.system.service;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.xm.commerce.common.exception.CurrentUserException;
import com.xm.commerce.common.exception.FileUploadException;
import com.xm.commerce.common.exception.ProductAlreadyExistException;
import com.xm.commerce.security.util.CurrentUserUtils;
import com.xm.commerce.system.mapper.ecommerce.ProductStoreMapper;
import com.xm.commerce.system.model.dto.FileUploadDto;
import com.xm.commerce.system.model.dto.PictureDto;
import com.xm.commerce.system.model.entity.ecommerce.EcommerceProductStore;
import com.xm.commerce.system.model.entity.ecommerce.EcommerceUser;
import com.xm.commerce.system.model.request.CategoryRequest;
import com.xm.commerce.system.model.response.ProductResponse;
import com.xm.commerce.system.util.FileUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
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
    @Value("${ftp.domain}")
    private String domain;

    public EcommerceUser getUser(){
        EcommerceUser user = currentUserUtils.getCurrentUser();
        if (user == null){
            throw new CurrentUserException();
        }
        return user;
    }

    public int deleteByPrimaryKey(Integer id) {
        return productStoreMapper.deleteById(id);
    }


    public String switch2LocalUrl(String url, String currentUsername, boolean isVerifyExtension) throws Exception {
        byte[] bytes = IOUtils.toByteArray(new URL(url.startsWith("https") ? url : "https:" + url));
        String extension = FilenameUtils.getExtension(url);
        if (isVerifyExtension) {
            if (!Arrays.asList(SUPPORTED_PIC_SUFFIX).contains(extension)) {
                throw new FileUploadException(ImmutableMap.of("格式不正确", extension));
            }
        }
        List<FileUploadDto> fileUploadDtos = fileUtil.fileUploadByByte(new PictureDto(bytes, extension), currentUsername + "/");
        log.info(fileUploadDtos.get(0).getUrl());
        return fileUploadDtos.get(0).getUrl();
    }

    public int insertSelective(EcommerceProductStore record) throws Exception {

        List<EcommerceProductStore> ecommerceProductStores = productStoreMapper.selectByNameAndUid(record.getProductName(), getUser().getId());
        if (null != ecommerceProductStores && !ecommerceProductStores.isEmpty()) {
            throw new ProductAlreadyExistException(ImmutableMap.of("商品已经存在", record.getProductName()));
        }

        if (record.getCategory().contains("&amp;")){
            String replace = record.getCategory().replace("&amp;", "&");
            record.setCategory(replace);
        }

        EcommerceUser ecommerceUser = getUser();
        StringBuilder sb = new StringBuilder();
        String image = record.getImage();
        List<PictureDto> pictureDtoList = new ArrayList<>();
        String[] imgs = image.split(",");
        List<String> imgList = Lists.newArrayList(imgs);
        for (String s : imgs) {
            if (!s.startsWith("http://" + ip)) {
                InputStream inputStream = new URL(s).openStream();
                byte[] bytes = IOUtils.toByteArray(inputStream);
                String extension = FilenameUtils.getExtension(s);
                pictureDtoList.add(new PictureDto(bytes, extension));
                if (!Arrays.asList(SUPPORTED_PIC_SUFFIX).contains(extension)) {
                    throw new FileUploadException(ImmutableMap.of("格式不正确", extension));
                }
                imgList.remove(s);
            }
        }
        List<FileUploadDto> fileUploadDtos = fileUtil.fileUploadByBytes(pictureDtoList, ecommerceUser.getUsername() + "/");
        imgList.addAll(fileUploadDtos.stream().map(FileUploadDto::getUrl).collect(Collectors.toList()));

        log.info("image" + image);

        if (record.getMetaTagTitle() == null || "".equals(record.getMetaTagTitle())) {
            record.setMetaTagTitle(record.getProductName());
        }
        if (StringUtils.isNotBlank(record.getDescription())) {
            try {
                String handledDescription = handleProductDescription(record.getDescription());
                record.setDescription(handledDescription);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        record.setUid(ecommerceUser.getId());
        record.setUploadOpencart(false);
        record.setUploadShopify(false);
        record.setImage(String.join(",", imgList));
        record.setDataAdded(new Date());
        record.setDataModified(new Date());
        return productStoreMapper.insertSelective(record);
    }

    private String handleProductDescription(String description) throws Exception {
        Document document = Jsoup.parse(description);
        Elements images = document.select("img");
        String currentUsername = currentUserUtils.getCurrentUsername();
        for (Element img : images) {
            String originImageUrl = img.attr("src");
            String customizedUrl = switch2LocalUrl(originImageUrl, currentUsername, false);
            img.attr("src", customizedUrl);
        }
        Elements a = document.select("a");
        a.remove();
        return document.body().toString();
    }

    public ProductResponse selectRespByPrimaryKey(Integer id) {
        EcommerceProductStore ecommerceProductStore = productStoreMapper.selectById(id);
        return new ProductResponse(ecommerceProductStore);
    }

    public int updateByPrimaryKeySelective(EcommerceProductStore record) {
        EcommerceUser user = getUser();
        if (record.getMetaTagTitle() == null) {
            record.setMetaTagTitle(record.getProductName());
        }
        record.setUid(user.getId());
        record.setDataModified(new Date());
        return productStoreMapper.updateByPrimaryKeySelective(record);
    }


    public List<ProductResponse> selectByCategory(CategoryRequest categoryRequest) {
        List<EcommerceProductStore> ecommerceProductStores = null;
//        if (categoryRequest.getPage() != null && categoryRequest.getPageSize() != null) {
//            PageHelper.startPage(categoryRequest.getPage(), categoryRequest.getPageSize());
//            ecommerceProductStores = productStoreMapper.selectByCategory(categoryRequest);
//        }
        EcommerceUser user = getUser();
        categoryRequest.setUid(user.getId());
        ecommerceProductStores = productStoreMapper.selectByCategory(categoryRequest);
        List<ProductResponse> responses = new ArrayList<>();
        ecommerceProductStores.forEach(ecommerceProductStore -> {
            ProductResponse productResponse = new ProductResponse(ecommerceProductStore);
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





