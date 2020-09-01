package com.xm.commerce.system.model.entity.umino;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ApiModel(value="com-xm-commerce-system-model-entity-umino-OcProductToCategory")
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OcProductToCategory {
    @ApiModelProperty(value="")
    private Integer productId;

    @ApiModelProperty(value="")
    private Integer categoryId;
}