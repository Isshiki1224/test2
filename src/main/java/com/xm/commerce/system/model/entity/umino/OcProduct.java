package com.xm.commerce.system.model.entity.umino;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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


@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("product")
public class OcProduct {
    
    @TableId(type = IdType.AUTO)
    private Integer productId;

    
    private String model;

    
    private String sku;

    
    private String upc;

    
    private String ean;

    
    private String jan;

    
    private String isbn;

    
    private String mpn;

    
    private String location;

    
    private Integer quantity;

    
    private Integer stockStatusId;

    
    private String image;

    
    private Integer manufacturerId;

    
    private Boolean shipping;

    
    private BigDecimal price;

    
    private Integer points;

    
    private Integer taxClassId;

    
    private Date dateAvailable;

    
    private BigDecimal weight;

    
    private Integer weightClassId;

    
    private BigDecimal length;

    
    private BigDecimal width;

    
    private BigDecimal height;

    
    private Integer lengthClassId;

    
    private Boolean subtract;

    
    private Integer minimum;

    
    private Integer sortOrder;

    
    private Boolean status;

    
    private Integer viewed;

    
    private Date dateAdded;

    
    private Date dateModified;
}