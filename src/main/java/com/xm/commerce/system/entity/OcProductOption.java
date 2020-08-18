package com.xm.commerce.system.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ApiModel(value="com-xm-commerce-system-entity-OcProductOption")
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OcProductOption {
    @ApiModelProperty(value="")
    private Integer productOptionId;

    @ApiModelProperty(value="")
    private Integer productId;

    @ApiModelProperty(value="")
    private Integer optionId;

    @ApiModelProperty(value="")
    private String value;

    @ApiModelProperty(value="")
    private Boolean required;
}