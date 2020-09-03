package com.xm.commerce.system.model.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryRequest {

    private Integer page;
    private Integer pageSize;
    private String category;
    private Integer minPrice;
    private Integer maxPrice;
    private String productName;
    private Integer byAddTime;
    private Integer byPrice;
    private Integer uid;

}
