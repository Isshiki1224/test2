package com.xm.commerce.system.model.entity.umino;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ApiModel(value="com-xm-commerce-system-model-entity-umino-OcProductOption")
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OcProductOption {
    @ApiModelProperty(value="")
    @TableId(type = IdType.AUTO)
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