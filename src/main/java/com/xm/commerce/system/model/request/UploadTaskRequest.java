package com.xm.commerce.system.model.request;

import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
public class UploadTaskRequest {

        private List<Integer>  ids;
        private Integer siteId;
}
