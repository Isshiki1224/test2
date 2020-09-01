package com.xm.commerce.system.model.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.xm.commerce.system.model.entity.ecommerce.EcommerceProductStore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
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

    /**
     * 入库时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date dataAdded;

    // todo cause NPE
    public ProductResponse(EcommerceProductStore ecommerceProductStore) {
        this.id = ecommerceProductStore.getId();
        this.productName = ecommerceProductStore.getProductName();
        this.description = ecommerceProductStore.getDescription();
        this.metaTagTitle = ecommerceProductStore.getProductName();
        this.model = ecommerceProductStore.getModel();
        this.sku = ecommerceProductStore.getSku();
        this.category = ecommerceProductStore.getCategory();
        this.mpn = ecommerceProductStore.getMpn();
        this.price = ecommerceProductStore.getPrice();
        this.quantity = ecommerceProductStore.getQuantity();
        this.image = Arrays.asList(ecommerceProductStore.getImage().split(","));
        this.productSource = ecommerceProductStore.getProductSource();
        this.productOptions = ecommerceProductStore.getProductOptions();
        this.dataAdded = ecommerceProductStore.getDataAdded();

    }
}
