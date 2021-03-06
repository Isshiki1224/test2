package com.xm.commerce.system.model.entity.umino;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@TableName("product_option_value")
public class OcProductOptionValue {
    
    @TableId(type = IdType.AUTO)
    private Integer productOptionValueId;

    
    private Integer productOptionId;

    
    private Integer productId;

    
    private Integer optionId;

    
    private Integer optionValueId;

    
    private Integer quantity;

    
    private Boolean subtract;

    
    private BigDecimal price;

    
    private String pricePrefix;

    
    private Integer points;

    
    private String pointsPrefix;

    
    private BigDecimal weight;

    
    private String weightPrefix;
}