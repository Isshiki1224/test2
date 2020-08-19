package com.xm.commerce.system.entity.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseData {

    public String msg;
    public int statusCode;
    public Object data;

    public ResponseData(String msg, int statusCode) {
        this.msg = msg;
        this.statusCode = statusCode;
    }

}
