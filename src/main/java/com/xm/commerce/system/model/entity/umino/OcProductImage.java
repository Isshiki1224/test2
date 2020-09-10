package com.xm.commerce.system.model.entity.umino;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@TableName("product_image")
public class OcProductImage {
    
    @TableId(type = IdType.AUTO)
    private Integer productImageId;

    
    private Integer productId;

    
    private String image;

    
    private Integer sortOrder;

    
//    private Boolean isRotate;
//
//
//    private Integer productOptionValueId;
}