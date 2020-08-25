package com.xm.commerce.system.model.response;

import com.xm.commerce.system.model.entity.ecommerce.ProductStore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductResponse implements Serializable {

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
    private List<String> image;

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


    public ProductResponse(ProductStore productStore) {
        this.id = productStore.getId();
        this.productName = productStore.getProductName();
        this.description = productStore.getDescription();
        this.metaTagTitle = productStore.getProductName();
        this.model = productStore.getModel();
        this.sku = productStore.getSku();
        this.category = productStore.getCategory();
        this.mpn = productStore.getMpn();
        this.price = productStore.getPrice();
        this.quantity = productStore.getQuantity();
        this.image = Arrays.asList(productStore.getImage().split(","));
        this.productSource = productStore.getProductSource();
        this.productOptions = productStore.getProductOptions();
    }
}
