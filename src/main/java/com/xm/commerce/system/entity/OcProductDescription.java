package com.xm.commerce.system.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ApiModel(value="com-xm-commerce-system-entity-OcProductDescription")
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OcProductDescription {
    @ApiModelProperty(value="")
    private Integer productId;

    @ApiModelProperty(value="")
    private Integer languageId;

    @ApiModelProperty(value="")
    private String name;

    @ApiModelProperty(value="")
    private String description;

    @ApiModelProperty(value="")
    private String tag;

    @ApiModelProperty(value="")
    private String metaTitle;

    @ApiModelProperty(value="")
    private String metaDescription;

    @ApiModelProperty(value="")
    private String metaKeyword;
}