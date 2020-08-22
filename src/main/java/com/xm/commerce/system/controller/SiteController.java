package com.xm.commerce.system.controller;

import com.xm.commerce.system.model.entity.ecommerce.Site;
import com.xm.commerce.system.model.response.ResponseCode;
import com.xm.commerce.system.model.response.ResponseData;
import com.xm.commerce.system.service.SiteService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class SiteController {
    @Resource
    SiteService siteService;

    @PostMapping("/site/add")
    public ResponseData add(@RequestBody Site site){
        return new ResponseData("", ResponseCode.SUCCESS);
    }

}
