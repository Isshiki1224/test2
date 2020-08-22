package com.xm.commerce.system.controller;

import com.xm.commerce.system.model.entity.ecommerce.ProductStore;
import com.xm.commerce.system.model.request.Upload2OpenCartRequest;
import com.xm.commerce.system.model.response.ResponseCode;
import com.xm.commerce.system.model.response.ResponseData;
import com.xm.commerce.system.service.Upload2WebProductService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/upload")
public class Upload2WebProductController {
    @Resource
    Upload2WebProductService upload2WebProductService;

    /**
     * upload 2 opencart
     */
    @PostMapping("/toOpenCart")
    public ResponseData upload2OpenCart(@RequestBody Upload2OpenCartRequest upload2OpenCartRequest) throws IOException {
        Map<String, Object> tokenAndCookies = upload2WebProductService.login2OpenCart(upload2OpenCartRequest);
        ProductStore productStore = upload2WebProductService.uploadPic2OpenCart(upload2OpenCartRequest, tokenAndCookies);
        boolean result = upload2WebProductService.upload2OpenCart(productStore);
        return new ResponseData("上传成功", 200 );
    }

    /**
     * upload 2 shopify
     */
    @PostMapping("/toShopify/{productId}")
    public ResponseData upload2Shopify(@PathVariable Integer productId) {
        boolean result = upload2WebProductService.upload2Shopify(productId);
        return new ResponseData("", ResponseCode.SUCCESS);
    }

}
