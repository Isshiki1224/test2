package com.xm.commerce.system.model.entity.umino;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ApiModel(value="com-xm-commerce-system-model-entity-umino-OcProductOptionValue")
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OcProductOptionValue {
    @ApiModelProperty(value="")
    @TableId(type = IdType.AUTO)
    private Integer productOptionValueId;

    @ApiModelProperty(value="")
    private Integer productOptionId;

    @ApiModelProperty(value="")
    private Integer productId;

    @ApiModelProperty(value="")
    private Integer optionId;

    @ApiModelProperty(value="")
    private Integer optionValueId;

    @ApiModelProperty(value="")
    private Integer quantity;

    @ApiModelProperty(value="")
    private Boolean subtract;

    @ApiModelProperty(value="")
    private BigDecimal price;

    @ApiModelProperty(value="")
    private String pricePrefix;

    @ApiModelProperty(value="")
    private Integer points;

    @ApiModelProperty(value="")
    private String pointsPrefix;

    @ApiModelProperty(value="")
    private BigDecimal weight;

    @ApiModelProperty(value="")
    private String weightPrefix;
}