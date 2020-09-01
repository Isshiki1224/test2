package com.xm.commerce.system.controller;

import com.xm.commerce.common.exception.ResourceNotFoundException;
import com.xm.commerce.security.util.CurrentUserUtils;
import com.xm.commerce.system.model.entity.ecommerce.EcommerceProductStore;
import com.xm.commerce.system.model.entity.ecommerce.EcommerceUser;
import com.xm.commerce.system.model.request.UploadRequest;
import com.xm.commerce.system.model.response.ProductResponse;
import com.xm.commerce.system.model.response.ResponseCode;
import com.xm.commerce.system.model.response.ResponseData;
import com.xm.commerce.system.service.ProductStoreService;
import com.xm.commerce.system.service.Upload2WebProductService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.IOException;
import java.nio.channels.Channel;
import java.util.Map;

@RestController
@RequestMapping("/upload")
public class Upload2WebProductController {
    @Resource
    Upload2WebProductService upload2WebProductService;
    @Resource
    CurrentUserUtils currentUserUtils;
    @Resource
    ProductStoreService productStoreService;


    private EcommerceUser getCurrentUser(){
        return currentUserUtils.getCurrentUser();
    }

    /**
     * upload 2 opencart
     */
    @PostMapping("/toOpenCart")
    public ResponseData upload2OpenCart(@RequestBody UploadRequest uploadRequest) throws Exception {
        EcommerceUser currentUser = getCurrentUser();
        if (currentUser == null ){
            throw new ResourceNotFoundException();
        }
        Map<String, Object> tokenAndCookies = upload2WebProductService.login2OpenCart(uploadRequest);
        EcommerceProductStore productStore = upload2WebProductService.uploadPic2OpenCart(uploadRequest, tokenAndCookies);
        boolean result = upload2WebProductService.upload2OpenCart(productStore, currentUser,uploadRequest.getSiteId());
        return new ResponseData(productStore.getProductName() + "商品入站成功", ResponseCode.SUCCESS );
    }


    /**
     * upload 2 shopify
     */
    @PostMapping("/toShopify")
    public ResponseData upload2Shopify(@RequestBody UploadRequest uploadRequest) throws Exception {
        EcommerceUser currentUser = getCurrentUser();
        ProductResponse productResponse = productStoreService.selectRespByPrimaryKey(uploadRequest.getProductId());
        boolean result = upload2WebProductService.upload2Shopify(uploadRequest, currentUser);
        return new ResponseData(productResponse.getProductName() + "商品入站成功", ResponseCode.SUCCESS);
    }

}
