package com.xm.commerce.system.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ApiModel(value="com-xm-commerce-system-entity-OcProductAttribute")
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OcProductAttribute {
    @ApiModelProperty(value="")
    private Integer productId;

    @ApiModelProperty(value="")
    private Integer attributeId;

    @ApiModelProperty(value="")
    private Integer languageId;

    @ApiModelProperty(value="")
    private String text;
}