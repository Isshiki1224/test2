package com.xm.commerce.common.config;

import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.unit.DataSize;

import javax.servlet.MultipartConfigElement;

@Configuration
public class MultipartConfig {

    /**
     * 对上传文件的配置
     */
    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        //设置单个附件大小上限值（默认为1M）
        factory.setMaxFileSize(DataSize.ofBytes(10 * 1024 * 1024));
        //设置所有附件的总大小上限值
        factory.setMaxRequestSize(DataSize.ofBytes(10 * 10 * 1024 * 1024));
        return factory.createMultipartConfig();
    }
}
