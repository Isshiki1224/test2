package com.xm.commerce.system.request;

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
    private Boolean byAddTime;
    private Boolean byPrice;

}
