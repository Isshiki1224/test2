package com.xm.commerce.system.model.response;

import com.xm.commerce.system.model.entity.ecommerce.EcommerceProductStore;
import com.xm.commerce.system.model.entity.ecommerce.EcommerceSite;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class UploadTaskResponse {

    private String taskId;
    private Date taskTime;
    private Integer taskStatus;
    private EcommerceSite site;
    private Integer uid;
    private String username;
    private List<EcommerceProductStore> productList;

}
