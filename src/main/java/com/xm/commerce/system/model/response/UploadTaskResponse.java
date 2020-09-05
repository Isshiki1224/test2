package com.xm.commerce.system.model.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.xm.commerce.system.model.dto.UploadTaskDto;
import com.xm.commerce.system.model.entity.ecommerce.EcommerceProductStore;
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
public class UploadTaskResponse implements Comparable<UploadTaskResponse>{

    private String taskId;
    private String taskTime;
    private Integer taskStatus;
    private Integer uid;
    private String username;
    private String siteName;
    private List<UploadTaskDto> productStores;

    @Override
    public int compareTo(UploadTaskResponse o) {
        return 0;
    }
}
