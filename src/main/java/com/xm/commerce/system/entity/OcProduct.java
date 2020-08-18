package com.xm.commerce.system.entity;

import com.xm.commerce.system.util.DateUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ApiModel(value="com-xm-commerce-system-entity-OcProduct")
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OcProduct {
    @ApiModelProperty(value="")
    private Integer productId;

    @ApiModelProperty(value="")
    private String model;

    @ApiModelProperty(value="")
    private String sku;

    @ApiModelProperty(value="")
    private String upc = "";

    @ApiModelProperty(value="")
    private String ean = "";

    @ApiModelProperty(value="")
    private String jan = "";

    @ApiModelProperty(value="")
    private String isbn = "";

    @ApiModelProperty(value="")
    private String mpn;

    @ApiModelProperty(value="")
    private String location = "";

    @ApiModelProperty(value="")
    private Integer quantity;

    @ApiModelProperty(value="")
    private Integer stockStatusId;

    @ApiModelProperty(value="")
    private String image;

    @ApiModelProperty(value="")
    private Integer manufacturerId;

    @ApiModelProperty(value="")
    private Boolean shipping = true;

    @ApiModelProperty(value="")
    private BigDecimal price;

    @ApiModelProperty(value="")
    private Integer points = 0;

    @ApiModelProperty(value="")
    private Integer taxClassId;

    @ApiModelProperty(value="")
    private Date dateAvailable = DateUtil.dateNow();

    @ApiModelProperty(value="")
    private BigDecimal weight= BigDecimal.valueOf(0);

    @ApiModelProperty(value="")
    private Integer weightClassId = 1;

    @ApiModelProperty(value="")
    private BigDecimal length = BigDecimal.valueOf(0);

    @ApiModelProperty(value="")
    private BigDecimal width = BigDecimal.valueOf(0);

    @ApiModelProperty(value="")
    private BigDecimal height = BigDecimal.valueOf(0);

    @ApiModelProperty(value="")
    private Integer lengthClassId = 1;

    @ApiModelProperty(value="")
    private Boolean subtract = true;

    @ApiModelProperty(value="")
    private Integer minimum = 1;

    @ApiModelProperty(value="")
    private Integer sortOrder = 0;

    @ApiModelProperty(value="")
    private Boolean status;

    @ApiModelProperty(value="")
    private Integer viewed = 0;

    @ApiModelProperty(value="")
    private Date dateAdded = DateUtil.dateNow();

    @ApiModelProperty(value="")
    private Date dateModified = DateUtil.dateNow();
}