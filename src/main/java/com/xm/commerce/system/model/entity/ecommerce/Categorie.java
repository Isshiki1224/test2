package com.xm.commerce.system.model.entity.ecommerce;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Categorie {
    private Integer id;

    private String name;

    private Integer parentId;

    private String detail;
}