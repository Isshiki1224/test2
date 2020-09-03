package com.xm.commerce.system.controller;

import com.xm.commerce.common.exception.CurrentUserException;
import com.xm.commerce.security.util.CurrentUserUtils;
import com.xm.commerce.system.model.entity.ecommerce.EcommerceSite;
import com.xm.commerce.system.model.entity.ecommerce.EcommerceUser;
import com.xm.commerce.system.model.response.ResponseData;
import com.xm.commerce.system.service.SiteService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class SiteController {
    @Resource
    SiteService siteService;
    @Resource
    CurrentUserUtils currentUserUtils;

    @PostMapping("/site/add")
    public ResponseData addOrUpdate(@RequestBody EcommerceSite ecommerceSite) {

        if (ecommerceSite.getId() == null) {
            siteService.insertSelective(ecommerceSite);
        } else {
            siteService.updateByPrimaryKeySelective(ecommerceSite);
        }
        return new ResponseData("新增成功", 200);
    }

    @GetMapping("/site/{id}")
    public ResponseData getSite(@PathVariable Integer id) {
        EcommerceSite ecommerceSite = siteService.selectByPrimaryKey(id);
        return new ResponseData("", 200, ecommerceSite);
    }

    @GetMapping("/sites")
    public ResponseData sites(Integer siteCategory) {
        EcommerceUser currentUser = currentUserUtils.getCurrentUser();
        if (null == currentUser){
            throw new CurrentUserException();
        }
        List<EcommerceSite> ecommerceSites = siteService.selectAll(siteCategory, currentUser.getId());
        return new ResponseData("", 200, ecommerceSites);
    }

    @PostMapping("/site/{id}")
    public ResponseData delete(@PathVariable Integer id) {
        siteService.deleteByPrimaryKey(id);
        return new ResponseData("删除成功", 200);
    }

}
