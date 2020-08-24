package com.xm.commerce.system.model.entity.ecommerce;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ApiModel(value = "com-xm-commerce-system-model-entity-ecommerce-Site")
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
    @ApiModelProperty(value = "主键")
    private Integer id;

    /**
     * 站点名称
     */
    @ApiModelProperty(value = "站点名称")
    private String siteName;

    /**
     * 站点类型
     */
    @ApiModelProperty(value = "站点类型")
    private Boolean siteCategory;

    /**
     * ip
     */
    @ApiModelProperty(value = "ip")
    private String ip;

    /**
     * 域名
     */
    @ApiModelProperty(value = "域名")
    private String domain;

    /**
     * 端口
     */
    @ApiModelProperty(value = "端口")
    private String port;

    /**
     * 账号
     */
    @ApiModelProperty(value = "账号")
    private String account;

    /**
     * 密码
     */
    @ApiModelProperty(value = "密码")
    private String password;

    /**
     * shopify  api
     */
    @ApiModelProperty(value = "shopify  api")
    private String api;

    /**
     * shopify 密钥
     */
    @ApiModelProperty(value = "shopify 密钥")
    private String apiKey;

    /**
     * shopify 密码
     */
    @ApiModelProperty(value = "shopify 密码")
    private String apiPassword;
}