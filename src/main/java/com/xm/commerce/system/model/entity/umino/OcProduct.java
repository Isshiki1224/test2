package com.xm.commerce.system.model.entity.umino;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
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

@ApiModel(value="com-xm-commerce-system-model-entity-umino-OcProduct")
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OcProduct {
    @ApiModelProperty(value="")
    @TableId(type = IdType.AUTO)
    private Integer productId;

    @ApiModelProperty(value="")
    private String model;

    @ApiModelProperty(value="")
    private String sku;

    @ApiModelProperty(value="")
    private String upc;

    @ApiModelProperty(value="")
    private String ean;

    @ApiModelProperty(value="")
    private String jan;

    @ApiModelProperty(value="")
    private String isbn;

    @ApiModelProperty(value="")
    private String mpn;

    @ApiModelProperty(value="")
    private String location;

    @ApiModelProperty(value="")
    private Integer quantity;

    @ApiModelProperty(value="")
    private Integer stockStatusId;

    @ApiModelProperty(value="")
    private String image;

    @ApiModelProperty(value="")
    private Integer manufacturerId;

    @ApiModelProperty(value="")
    private Boolean shipping;

    @ApiModelProperty(value="")
    private BigDecimal price;

    @ApiModelProperty(value="")
    private Integer points;

    @ApiModelProperty(value="")
    private Integer taxClassId;

    @ApiModelProperty(value="")
    private Date dateAvailable;

    @ApiModelProperty(value="")
    private BigDecimal weight;

    @ApiModelProperty(value="")
    private Integer weightClassId;

    @ApiModelProperty(value="")
    private BigDecimal length;

    @ApiModelProperty(value="")
    private BigDecimal width;

    @ApiModelProperty(value="")
    private BigDecimal height;

    @ApiModelProperty(value="")
    private Integer lengthClassId;

    @ApiModelProperty(value="")
    private Boolean subtract;

    @ApiModelProperty(value="")
    private Integer minimum;

    @ApiModelProperty(value="")
    private Integer sortOrder;

    @ApiModelProperty(value="")
    private Boolean status;

    @ApiModelProperty(value="")
    private Integer viewed;

    @ApiModelProperty(value="")
    private Date dateAdded;

    @ApiModelProperty(value="")
    private Date dateModified;
}