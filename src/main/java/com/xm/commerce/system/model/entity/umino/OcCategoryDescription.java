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

@ApiModel(value="com-xm-commerce-system-model-entity-umino-OcCategoryDescription")
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OcCategoryDescription {
    @ApiModelProperty(value="")
    private Integer categoryId;

    @ApiModelProperty(value="")
    private Integer languageId;

    @ApiModelProperty(value="")
    private String name;

    @ApiModelProperty(value="")
    private String description;

    @ApiModelProperty(value="")
    private String metaTitle;

    @ApiModelProperty(value="")
    private String metaDescription;

    @ApiModelProperty(value="")
    private String metaKeyword;
}