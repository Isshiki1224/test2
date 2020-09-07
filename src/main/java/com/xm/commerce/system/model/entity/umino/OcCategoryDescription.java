package com.xm.commerce.system.model.entity.umino;

import io.swagger.annotations.ApiModel;
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
    private Integer categoryId;

    private Integer languageId;

    private String name;

    private String description;

    private String metaTitle;

    private String metaDescription;

    private String metaKeyword;
}