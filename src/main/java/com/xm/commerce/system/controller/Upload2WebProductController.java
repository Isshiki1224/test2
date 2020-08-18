package com.xm.commerce.system.controller;

import com.xm.commerce.system.response.ResponseData;
import com.xm.commerce.system.service.Upload2WebProductService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/upload")
public class Upload2WebProductController {
    @Resource
    Upload2WebProductService upload2WebProductService;

    /**
     * upload 2 opencart
     */
    @PostMapping("/toOpenCart")
    public ResponseData upload2OpenCart(@RequestParam("productId") Integer productId) throws Exception {
        boolean result = upload2WebProductService.upload2OpenCart(productId);
        return new ResponseData();
    }

}
