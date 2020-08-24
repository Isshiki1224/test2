package com.xm.commerce.system.model.entity.ecommerce;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ApiModel(value = "com-xm-commerce-system-model-entity-ecommerce-ProductStore")
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductStore {
    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
    private Integer id;

    /**
     * 产品名称
     */
    @ApiModelProperty(value = "产品名称")
    private String productName;

    /**
     * 产品描述
     */
    @ApiModelProperty(value = "产品描述")
    private String description;

    /**
     * 标签
     */
    @ApiModelProperty(value = "标签")
    private String metaTagTitle;

    /**
     * model
     */
    @ApiModelProperty(value = "model")
    private String model;

    /**
     * SKU
     */
    @ApiModelProperty(value = "SKU")
    private String sku;

    /**
     * 谷歌分类
     */
    @ApiModelProperty(value = "谷歌分类")
    private String category;

    /**
     * MPN
     */
    @ApiModelProperty(value = "MPN")
    private String mpn;

    /**
     * 价格
     */
    @ApiModelProperty(value = "价格")
    private BigDecimal price;

    /**
     * 库存
     */
    @ApiModelProperty(value = "库存")
    private Integer quantity;

    /**
     * 图片
     */
    @ApiModelProperty(value = "图片")
    private String image;

    /**
     * 产品链接
     */
    @ApiModelProperty(value = "产品链接")
    private String productLink;

    /**
     * 数据来源
     */
    @ApiModelProperty(value = "数据来源")
    private String productSource;

    /**
     * 商品属性
     */
    @ApiModelProperty(value = "商品属性")
    private String productOptions;

    /**
     * 入库时间
     */
    @ApiModelProperty(value = "入库时间")
    private Date dataAdded;

    /**
     * 修改时间
     */
    @ApiModelProperty(value = "修改时间")
    private Date dataModified;

    /**
     * 上传到shopify
     */
    @ApiModelProperty(value = "上传到shopify")
    private Boolean uploadShopify;

    /**
     * 上传到opencart
     */
    @ApiModelProperty(value = "上传到opencart")
    private Boolean uploadOpencart;

    /**
     * shopify上传者
     */
    @ApiModelProperty(value = "shopify上传者")
    private Integer uploadShopifyBy;

    /**
     * opencart上传者
     */
    @ApiModelProperty(value = "opencart上传者")
    private Integer uploadOpencartBy;


}