package com.xm.commerce.system.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ApiModel(value = "com-xm-commerce-system-entity-OcOptionValueDescription")
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OcOptionValueDescription {
    @ApiModelProperty(value = "")
    private Integer optionValueId;

    @ApiModelProperty(value = "")
    private Integer languageId;

    @ApiModelProperty(value = "")
    private Integer optionId;

    @ApiModelProperty(value = "")
    private String name;
}