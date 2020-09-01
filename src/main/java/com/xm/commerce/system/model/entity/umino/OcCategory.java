package com.xm.commerce.system.model.entity.umino;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ApiModel(value="com-xm-commerce-system-model-entity-umino-OcCategory")
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OcCategory {
    @ApiModelProperty(value="")
    @TableId(type = IdType.AUTO)
    private Integer categoryId;

    @ApiModelProperty(value="")
    private String image;

    @ApiModelProperty(value="")
    private Integer parentId;

    @ApiModelProperty(value="")
    private Boolean top;

    @ApiModelProperty(value="")
    private Integer column;

    @ApiModelProperty(value="")
    private Integer sortOrder;

    @ApiModelProperty(value="")
    private Boolean status;

    @ApiModelProperty(value="")
    private Date dateAdded;

    @ApiModelProperty(value="")
    private Date dateModified;

    @ApiModelProperty(value="")
    private String secondaryImage;

    @ApiModelProperty(value="")
    private String alternativeImage;

    @ApiModelProperty(value="")
    private Boolean isFeatured;
}