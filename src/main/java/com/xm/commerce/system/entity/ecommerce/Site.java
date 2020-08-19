package com.xm.commerce.system.entity.ecommerce;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Site {
    /**
    * 主键
    */
    private Integer id;

    /**
    * 站点名称
    */
    private String siteName;

    /**
    * 站点类型
    */
    private Boolean siteCategory;

    /**
    * ip
    */
    private String ip;

    /**
    * 域名
    */
    private String domain;

    /**
    * 端口
    */
    private String port;

    /**
    * 账号
    */
    private String account;

    /**
    * 密码
    */
    private String password;

    /**
    * shopify  api
    */
    private String api;

    /**
    * opencart token
    */
    private String userToken;
}