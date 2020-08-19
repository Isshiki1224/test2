package com.xm.commerce.system.entity.ecommerce;

import java.math.BigDecimal;
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
public class ProductStore {
    /**
     * 主键
     */
    private Integer id;

    /**
     * 产品名称
     */
    private String productName;

    /**
     * 产品描述
     */
    private String description;

    /**
     * 标签
     */
    private String metaTagTitle;

    /**
     * model
     */
    private String model;

    /**
     * SKU
     */
    private String sku;

    /**
     * 谷歌分类
     */
    private String category;

    /**
     * MPN
     */
    private String mpn;

    /**
     * 价格
     */
    private BigDecimal price;

    /**
     * 库存
     */
    private Integer quantity;

    /**
     * 图片
     */
    private String image;

    /**
     * 产品链接
     */
    private String productLink;

    /**
     * 数据来源
     */
    private String productSource;

    /**
     * 商品属性
     */
    private String productOptions;
}