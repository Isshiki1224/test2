package com.xm.commerce.system.task;

import com.xm.commerce.system.constant.RedisConstant;
import com.xm.commerce.system.model.dto.OpenCartAuthDto;
import com.xm.commerce.system.model.dto.UploadTaskDto;
import com.xm.commerce.system.model.entity.ecommerce.EcommerceProductStore;
import com.xm.commerce.system.model.entity.ecommerce.EcommerceSite;
import com.xm.commerce.system.service.Upload2WebProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Slf4j
@Component
public class UploadTask {
    @Resource
    RedisTemplate<String, Object> redisTemplate;
    @Resource
    Upload2WebProductService upload2WebProductService;
    @Resource
    UploadTaskWebSocket uploadTaskWebSocket;

    /**
     * execute single task
     */
    @Async
    public void taskExecute(Object key) throws Exception {

        // redis single task key & verify task valid
        String singleKey = RedisConstant.UPLOAD_TASK_SINGLE_PREFIX + key;
        UploadTaskDto uploadTaskDto = (UploadTaskDto) redisTemplate.opsForValue().get(singleKey);
        if (uploadTaskDto == null) {
            throw new RuntimeException();
        }
        uploadTaskDto.setTaskStatus(1);
        redisTemplate.opsForValue().set(singleKey, uploadTaskDto);
        uploadTaskWebSocket.sendMessage(uploadTaskDto, uploadTaskDto.getUsername());
        // task execute
        EcommerceSite site = uploadTaskDto.getSite();
        log.info("site info: {}", site);
        EcommerceProductStore productStore = uploadTaskDto.getProductStore();
        log.info("product info: {}", productStore);
        Integer uid = uploadTaskDto.getUid();
        if (site.getSiteCategory()) {
            OpenCartAuthDto openCartAuthDto = (OpenCartAuthDto) redisTemplate.opsForValue().get(RedisConstant.OPENCART_TOKEN + site.getId());
            if (openCartAuthDto == null) {
                openCartAuthDto = upload2WebProductService.login2OpenCart2(site, singleKey);
            }
            productStore = upload2WebProductService.uploadPic2OpenCart2(productStore, site, openCartAuthDto, singleKey);
            boolean result = false;
            try {
                result = upload2WebProductService.upload2OpenCart2(productStore, uid, site);
            } catch (Exception e) {
                e.printStackTrace();
                uploadTaskDto.setErrorMessage(e.getMessage());
                updateSingleTaskStatusAndMessaging(uploadTaskDto, singleKey, productStore, 3);
            }
            if (result) {
                productStore.setUploadOpencart(true);
                updateSingleTaskStatusAndMessaging(uploadTaskDto, singleKey, productStore, 2);
            }
        } else {
            boolean result = false;
            try {
                result = upload2WebProductService.upload2Shopify2(productStore, site, uid, singleKey);
            } catch (Exception e) {
                e.printStackTrace();
                uploadTaskDto.setErrorMessage(e.getMessage());
                updateSingleTaskStatusAndMessaging(uploadTaskDto, singleKey, productStore, 3);
            }
            if (result) {
                productStore.setUploadShopify(true);
                updateSingleTaskStatusAndMessaging(uploadTaskDto, singleKey, productStore, 2);
            }
        }
    }

    private void updateSingleTaskStatusAndMessaging(UploadTaskDto uploadTaskDto, String singleKey, EcommerceProductStore productStore, int taskStatus) throws Exception {
        // update single task status
        uploadTaskDto.setProductStore(productStore);
        uploadTaskDto.setTaskStatus(taskStatus);
        redisTemplate.opsForValue().set(singleKey, uploadTaskDto);
        // messaging
        uploadTaskWebSocket.sendMessage(uploadTaskDto, uploadTaskDto.getUsername());
        log.info("发送给前端的数据：" + uploadTaskDto);
    }


    private boolean isTokenExpire(EcommerceSite site, OpenCartAuthDto openCartAuthDto) {
        return false;
    }


}
