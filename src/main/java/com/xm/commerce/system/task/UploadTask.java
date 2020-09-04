package com.xm.commerce.system.task;

import com.xm.commerce.system.constant.RedisConstant;
import com.xm.commerce.system.model.dto.UploadTaskDto;
import com.xm.commerce.system.model.entity.ecommerce.EcommerceProductStore;
import com.xm.commerce.system.model.entity.ecommerce.EcommerceSite;
import com.xm.commerce.system.model.response.UploadTaskResponse;
import com.xm.commerce.system.service.Upload2WebProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class UploadTask {
    @Resource
    RedisTemplate<String, Object> redisTemplate;
    @Resource
    Upload2WebProductService upload2WebProductService;
    @Resource
    UploadTaskWebSocket uploadTaskWebSocket;


    @Async
    public void taskExecute(Object key) throws Exception {

        String singleKey = RedisConstant.UPLOAD_TASK_SINGLE_PREFIX + key;
        UploadTaskDto uploadTaskDto = (UploadTaskDto) redisTemplate.opsForValue().get(singleKey);
        if (uploadTaskDto == null) {
            throw new RuntimeException();
        }
        String taskKey = RedisConstant.UPLOAD_TASK_PREFIX + uploadTaskDto.getTaskId();
        UploadTaskResponse uploadTaskResponse = (UploadTaskResponse) redisTemplate.opsForValue().get(taskKey);
        if (uploadTaskResponse == null) {
            throw new RuntimeException();
        }
        uploadTaskResponse.setTaskStatus(1);
        redisTemplate.opsForValue().set(taskKey, uploadTaskResponse);
        List<EcommerceProductStore> productStores = uploadTaskResponse.getProductStores();
        EcommerceSite site = uploadTaskDto.getSite();
        EcommerceProductStore productStore = uploadTaskDto.getProductStore();
        Integer uid = uploadTaskDto.getUid();
        if (site.getSiteCategory()) {
            Map<String, Object> tokenAndCookie = upload2WebProductService.login2OpenCart(site);
            productStore = upload2WebProductService.uploadPic2OpenCart(productStore, site, tokenAndCookie);
            boolean result = upload2WebProductService.upload2OpenCart(productStore, uid, site);
            if (result) {
                productStore.setUploadOpencart(true);
                sendMessage(uploadTaskDto, singleKey,taskKey, productStore);
            }
        } else {
            boolean result = upload2WebProductService.upload2Shopify(productStore, site, uid);
            if (result){
                productStore.setUploadShopify(true);
                sendMessage(uploadTaskDto, singleKey,taskKey, productStore);
            }
        }
        uploadTaskDto.setTaskStatus(1);
        redisTemplate.opsForValue().set(singleKey, uploadTaskDto);

//        String key = RedisConstant.UPLOAD_TASK_PREFIX + taskId;
//        UploadTaskResponse uploadTaskResponse = (UploadTaskResponse) redisTemplate.opsForValue().get(key);
//        if (uploadTaskResponse == null) {
//            throw new RuntimeException();
//        }
//        uploadTaskResponse.setTaskStatus(1);
//        redisTemplate.opsForValue().set(key, uploadTaskResponse);
//        Integer uid = uploadTaskResponse.getUid();
//        EcommerceSite site = uploadTaskResponse.getSite();
//        List<EcommerceProductStore> productList = uploadTaskResponse.getProductList();
//        for (int i = 0; i < productList.size(); i++) {
//            EcommerceProductStore productStore = productList.get(i);
//            if (site.getSiteCategory()) {
//                Map<String, Object> tokenAndCookie = upload2WebProductService.login2OpenCart(site);
//                productStore = upload2WebProductService.uploadPic2OpenCart(productStore, site, tokenAndCookie);
//                boolean result = upload2WebProductService.upload2OpenCart(productStore, uid, site);
//                if (result) {
//                    productStore.setUploadOpencart(true);
//                    productList.set(i, productStore);
//                    uploadTaskResponse.setProductList(productList);
//                    redisTemplate.opsForValue().set(key, uploadTaskResponse);
//                    uploadTaskResponse = (UploadTaskResponse)redisTemplate.opsForValue().get(key);
//                    if (uploadTaskResponse == null) {
//                        throw new RuntimeException();
//                    }
//                    uploadTaskWebSocket.sendMessage(uploadTaskResponse, uploadTaskResponse.getUsername());
//                }
//            } else {
//                boolean result = upload2WebProductService.upload2Shopify(productStore, site, uid);
//                if (result) {
//                    productStore.setUploadShopify(true);
//                    productList.set(i, productStore);
//                    uploadTaskResponse.setProductList(productList);
//                    redisTemplate.opsForValue().set(key, uploadTaskResponse);
//                    uploadTaskResponse = (UploadTaskResponse)redisTemplate.opsForValue().get(key);
//                    if (uploadTaskResponse == null) {
//                        throw new RuntimeException();
//                    }
//                    uploadTaskWebSocket.sendMessage(uploadTaskResponse, uploadTaskResponse.getUsername());
//                }
//            }
//        }
//        uploadTaskResponse.setTaskStatus(1);
//        redisTemplate.opsForValue().set(key, uploadTaskResponse);
//        uploadTaskResponse = (UploadTaskResponse)redisTemplate.opsForValue().get(key);
//        if (uploadTaskResponse == null) {
//            throw new RuntimeException();
//        }
//        uploadTaskWebSocket.sendMessage(uploadTaskResponse, uploadTaskResponse.getUsername());
//        log.info("前端返回: " + uploadTaskResponse.toString());
    }

    private void sendMessage(UploadTaskDto uploadTaskDto, String singleKey, String taskKey,  EcommerceProductStore productStore) throws Exception {
        redisTemplate.opsForValue().set(singleKey, productStore);
        productStore = (EcommerceProductStore) redisTemplate.opsForValue().get(singleKey);
        if (productStore == null) {
            throw new RuntimeException();
        }
        uploadTaskDto.setProductStore(productStore);
        redisTemplate.opsForValue().set(taskKey, uploadTaskDto);
        uploadTaskDto = (UploadTaskDto)redisTemplate.opsForValue().get(taskKey);
        if (uploadTaskDto == null){
            throw new RuntimeException();
        }
        uploadTaskWebSocket.sendMessage(uploadTaskDto, uploadTaskDto.getUsername());
        log.info("发送给前端的数据：" + uploadTaskDto);
    }
}
