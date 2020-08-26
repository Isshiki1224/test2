package com.xm.commerce.system.controller;

import com.xm.commerce.security.util.CurrentUserUtils;
import com.xm.commerce.system.model.entity.ecommerce.ProductStore;
import com.xm.commerce.system.model.entity.ecommerce.User;
import com.xm.commerce.system.model.request.UploadRequest;
import com.xm.commerce.system.model.response.ProductResponse;
import com.xm.commerce.system.model.response.ResponseCode;
import com.xm.commerce.system.model.response.ResponseData;
import com.xm.commerce.system.service.ProductStoreService;
import com.xm.commerce.system.service.Upload2WebProductService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.IOException;
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


    private User getCurrentUser(){
        return currentUserUtils.getCurrentUser();
    }

    /**
     * upload 2 opencart
     */
    @PostMapping("/toOpenCart")
    public ResponseData upload2OpenCart(@RequestBody UploadRequest uploadRequest) throws IOException {
        User currentUser = getCurrentUser();
        Map<String, Object> tokenAndCookies = upload2WebProductService.login2OpenCart(uploadRequest);
        ProductStore productStore = upload2WebProductService.uploadPic2OpenCart(uploadRequest, tokenAndCookies);
        boolean result = upload2WebProductService.upload2OpenCart(productStore, currentUser,uploadRequest.getSiteId());
        return new ResponseData("入站成功", ResponseCode.SUCCESS );
    }

    /**
     * upload 2 shopify
     */
    @PostMapping("/toShopify")
    public ResponseData upload2Shopify(@RequestBody UploadRequest uploadRequest) throws Exception {
        User currentUser = getCurrentUser();
        ProductResponse productResponse = productStoreService.selectRespByPrimaryKey(uploadRequest.getProductId());
        boolean result = upload2WebProductService.upload2Shopify(uploadRequest, currentUser);
        return new ResponseData(productResponse.getProductName() + "商品入站成功", ResponseCode.SUCCESS);
    }

}
