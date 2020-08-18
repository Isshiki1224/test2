package com.xm.commerce.system.annotation;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Component
@Mapper
public @interface MultiDatasourceMapper {

    String value() default "ecommerceDatasource";
}
