package com.xm.commerce.system.controller;

import com.xm.commerce.system.entity.ecommerce.ProductStore;
import com.xm.commerce.system.entity.request.CategoryRequest;
import com.xm.commerce.system.entity.response.ResponseData;

import com.xm.commerce.system.service.ProductStoreService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController

public class ProductController {
    @Resource
    ProductStoreService productStoreService;

    @PostMapping("/product/add")
    public ResponseData addProduct(@RequestBody ProductStore productStore){
        productStoreService.insertSelective(productStore);
        return new ResponseData("", 200);
    }

    @GetMapping("/products")
    public ResponseData products(CategoryRequest categoryRequest) {
        // todo  8.18 分类分页搜索
//        productStoreService.selectByCategory(categoryRequest);
        return new ResponseData("", 200);
    }

    @GetMapping("/product/{id}")
    public ResponseData products(@PathVariable Integer id) {

//        productStoreService.selectByCategory(categoryRequest);
        return new ResponseData("", 200);
    }


}
