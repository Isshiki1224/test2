package com.xm.commerce.common.datasource;


import com.baomidou.dynamic.datasource.processor.DsProcessor;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInvocation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

@Slf4j
public class MyDsProcessor extends DsProcessor {

    private static final String PARAM_PREFIX = "#param";

    @Override
    public boolean matches(String key) {
        return key.startsWith(PARAM_PREFIX);
    }

    @Override
    public String doDetermineDatasource(MethodInvocation methodInvocation, String key) {
        Method method = methodInvocation.getMethod();
        Parameter[] parameters = method.getParameters();
        for (Parameter parameter : parameters) {
            if (parameter.getName().equals(key.substring(PARAM_PREFIX.length() + 1))){
                return parameter.getName();
            }
        }
        log.info("没有符合的数据源");
        return null;
    }
}
