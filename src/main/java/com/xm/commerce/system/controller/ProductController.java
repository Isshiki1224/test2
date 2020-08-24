package com.xm.commerce.system.controller;

import com.xm.commerce.system.model.entity.ecommerce.ProductStore;
import com.xm.commerce.system.model.request.CategoryRequest;
import com.xm.commerce.system.model.response.ResponseData;

import com.xm.commerce.system.service.ProductStoreService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController

public class ProductController {
    @Resource
    ProductStoreService productStoreService;

    @PostMapping("/product/save")
    public ResponseData addProduct(@RequestBody ProductStore productStore){
        if (productStore.getId() == null){
            productStoreService.insertSelective(productStore);
        }else{
            productStoreService.updateByPrimaryKey(productStore);
        }
        return new ResponseData("", 200);
    }

    @GetMapping("/products")
    public ResponseData products(@RequestBody CategoryRequest categoryRequest) {
        // todo 分页
        List<ProductStore> productStores = productStoreService.selectByCategory(categoryRequest);
        return new ResponseData("", 200, productStores);
    }

    @GetMapping("/product/{id}")
    public ResponseData getProduct(@PathVariable Integer id) {
        ProductStore productStore = productStoreService.selectByPrimaryKey(id);
        return new ResponseData("", 200, productStore);
    }

    @PostMapping("/products")
    public ResponseData deleteProducts(@RequestBody List<Integer> ids) {
        productStoreService.deleteByBatch(ids);
        return new ResponseData("删除成功", 200);
    }

    @PostMapping("/product/{id}")
    public ResponseData deleteProduct(@PathVariable Integer id) {
        productStoreService.deleteByPrimaryKey(id);
        return new ResponseData("删除成功", 200);
    }




}
