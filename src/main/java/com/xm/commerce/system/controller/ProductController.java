//package com.xm.commerce.system.controller;
//
//import com.xm.commerce.system.entity.OcProductStore;
//import com.xm.commerce.system.entity.request.CategoryRequest;
//import com.xm.commerce.system.entity.response.ResponseData;
//import com.xm.commerce.system.ecommerce.service.OcProductStoreService;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RestController;
//
//import javax.annotation.Resource;
//
//@RestController
//
//public class ProductController {
//    @Resource
//    OcProductStoreService ocProductStoreService;
//
//    @PostMapping("/product/add")
//    public ResponseData addProduct(@RequestBody OcProductStore ocProductStore){
//        ocProductStoreService.insertSelective(ocProductStore);
//        return new ResponseData("", 200);
//    }
//
//    @PostMapping("/products")
//    public ResponseData products(@RequestBody CategoryRequest categoryRequest){
//        //todo  8.18 分类分页搜索
//        ocProductStoreService.selectByCategory(categoryRequest);
//        return new ResponseData("", 200);
//    }
//
//
//}
