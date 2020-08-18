package com.xm.commerce.system.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ApiModel(value="com-xm-commerce-system-entity-OcOption")
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OcOption {
    @ApiModelProperty(value="")
    private Integer optionId;

    @ApiModelProperty(value="")
    private String type;

    @ApiModelProperty(value="")
    private Integer sortOrder;
}