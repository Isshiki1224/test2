package com.xm.commerce.system.configuration;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@Configuration
@EnableConfigurationProperties
public class DataSourceConfig {

    @Bean(name = "ecommerceDatasource")//注入到这个容器
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource.ecommerce")
    public DataSource ecommerceDatasource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "uminoDatasource")//注入到这个容器
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource.umino")
    public DataSource uminoDatasource() {
        return DataSourceBuilder.create().build();
    }
}
