package com.xm.commerce.system.model.entity.umino;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("category")
public class OcCategory {
    @TableId(type = IdType.AUTO)
    private Integer categoryId;

    private String image;

    private Integer parentId;

    private Boolean top;

    private Integer column;

    private Integer sortOrder;

    private Boolean status;

    private Date dateAdded;

    private Date dateModified;

//    private String secondaryImage;
//
//    private String alternativeImage;
//
//    private Boolean isFeatured;
}