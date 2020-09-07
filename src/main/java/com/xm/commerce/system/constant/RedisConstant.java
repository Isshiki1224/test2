package com.xm.commerce.system.constant;

public interface RedisConstant {

//    static String PRODUCT_NAME = "productList";

    String UPLOAD_TASK_LIST_KEY = "ecommerce:upload:task:queue";

    String UPLOAD_TASK_PREFIX = "ecommerce:upload:task:";

    String UPLOAD_TASK_SINGLE_PREFIX = "ecommerce:upload:single:task:";

    String UPLOAD_TASK_BY_USER = "ecommerce:upload:user:";

    String OPENCART_TOKEN = "ecommerce:opencart:token:";
}
