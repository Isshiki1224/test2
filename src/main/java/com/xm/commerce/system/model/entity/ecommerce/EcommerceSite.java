package com.xm.commerce.system.model.entity.ecommerce;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ApiModel(value = "com-xm-commerce-system-model-entity-ecommerce-EcommerceSite")
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EcommerceSite {
    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
    @TableId(type = IdType.AUTO)
    private Integer id;


    /**
     * 用户id
     */
    @ApiModelProperty(value = "用户ID")
    private Integer uid;

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
     * opencart账号
     */
    @ApiModelProperty(value = "opencart账号")
    private String account;

    /**
     * opencart密码
     */
    @ApiModelProperty(value = "opencart密码")
    private String password;

    /**
     * 数据库名称
     */
    @ApiModelProperty(value = "数据库名称")
    private String dbName;

    /**
     * 数据库用户名
     */
    @ApiModelProperty(value = "数据库用户名")
    private String dbUsername;

    /**
     * 数据库密码
     */
    @ApiModelProperty(value = "数据库密码")
    private String dbPassword;

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