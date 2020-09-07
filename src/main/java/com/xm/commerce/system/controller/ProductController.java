package com.xm.commerce.system.controller;

import com.google.common.collect.ImmutableMap;
import com.xm.commerce.common.exception.ResourceNotFoundException;
import com.xm.commerce.security.util.CurrentUserUtils;
import com.xm.commerce.system.model.dto.FileUploadDto;
import com.xm.commerce.system.model.entity.ecommerce.EcommerceProductStore;
import com.xm.commerce.system.model.entity.ecommerce.EcommerceUser;
import com.xm.commerce.system.model.request.CategoryRequest;
import com.xm.commerce.system.model.response.ProductResponse;
import com.xm.commerce.system.model.response.ResponseCode;
import com.xm.commerce.system.model.response.ResponseData;
import com.xm.commerce.system.service.ProductStoreService;
import com.xm.commerce.system.util.FileUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;

@RestController

public class ProductController {
    @Resource
    ProductStoreService productStoreService;
    @Resource
    FileUtil fileUtil;
    @Resource
    CurrentUserUtils currentUserUtils;

    @PostMapping("/product/save")
    public ResponseData addProduct(@RequestBody EcommerceProductStore ecommerceProductStore) throws Exception {
        if (ecommerceProductStore.getId() == null){
            productStoreService.insertSelective(ecommerceProductStore);
        }else{
            productStoreService.updateByPrimaryKeySelective(ecommerceProductStore);
        }
        return new ResponseData("", 200);
    }

    @GetMapping("/products")
    public ResponseData products(CategoryRequest categoryRequest) {
        // todo 分页
        List<ProductResponse> responses = productStoreService.selectByCategory(categoryRequest);
        return new ResponseData("", 200, responses);
    }

    @GetMapping("/product/{id}")
    public ResponseData getProduct(@PathVariable Integer id) {
//        EcommerceProductStore productStore = productStoreService.selectByPrimaryKey(id);
        ProductResponse productResponse = productStoreService.selectRespByPrimaryKey(id);
        return new ResponseData("", 200, productResponse);
    }

    @PostMapping("/products")
    public ResponseData deleteProducts(@RequestBody List<Integer> ids) {
        if (productStoreService.deleteByBatch(ids) == 0) {
            throw new ResourceNotFoundException(ImmutableMap.of("删除失败", ids));
        }
        return new ResponseData("删除成功", 200);
    }

    @PostMapping("/product/{id}")
    public ResponseData deleteProduct(@PathVariable Integer id) {
        productStoreService.deleteByPrimaryKey(id);
        return new ResponseData("删除成功", 200);
    }

    @PostMapping("/product/uploadImage")
    public ResponseData uploadImage(@RequestBody MultipartFile file) throws Exception {
        EcommerceUser currentEcommerceUser = currentUserUtils.getCurrentUser();
        FileUploadDto fileUploadDto = fileUtil.fileUpload(file, currentEcommerceUser.getUsername() + "/");
        return new ResponseData("上传成功", ResponseCode.SUCCESS, fileUploadDto);
    }
}
