package com.xm.commerce.system.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ResponseData {

    public String msg;
    public int statusCode;
    public Object data;

    public ResponseData(String msg, int statusCode) {
        this.msg = msg;
        this.statusCode = statusCode;
    }

    public ResponseData(int statusCode, Object data) {
        this.msg = "";
        this.statusCode = statusCode;
        this.data = data;
    }
}
