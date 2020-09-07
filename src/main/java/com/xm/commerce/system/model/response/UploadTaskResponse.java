package com.xm.commerce.system.model.response;

import com.xm.commerce.system.model.dto.UploadTaskDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class UploadTaskResponse{

    private String taskId;
    private String taskTime;
    private Integer taskStatus;
    private Integer uid;
    private String username;
    private String siteName;
    private Integer all;
    private Integer already;
    private Integer status;
    private List<UploadTaskDto> productStores;

}
