package com.xm.commerce.system.model.dto;

import com.xm.commerce.system.model.entity.ecommerce.EcommerceProductStore;
import com.xm.commerce.system.model.entity.ecommerce.EcommerceSite;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UploadTaskDto {

    private String id;
    private String taskId;
    private Integer taskStatus;
    private EcommerceSite site;
    private Integer uid;
    private String username;
    private EcommerceProductStore productStore;
}
