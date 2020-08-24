package com.xm.commerce.system.controller;

import com.xm.commerce.system.model.entity.ecommerce.Site;
import com.xm.commerce.system.model.response.ResponseData;
import com.xm.commerce.system.service.SiteService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class SiteController {
    @Resource
    SiteService siteService;

    @PostMapping("/site/add")
    public ResponseData addOrUpdate(@RequestBody Site site) {
        if (site.getId() == null) {
            siteService.insertSelective(site);
        } else {
            siteService.updateByPrimaryKeySelective(site);
        }
        return new ResponseData("新增成功", 200);
    }

    @GetMapping("/site/{id}")
    public ResponseData getSite(@PathVariable Integer id) {
        Site site = siteService.selectByPrimaryKey(id);
        return new ResponseData("", 200, site);
    }

    @GetMapping("/sites")
    public ResponseData sites(Integer siteCategory) {
        List<Site> sites = siteService.selectAll(siteCategory);
        return new ResponseData("", 200, sites);
    }

    @PostMapping("/site/{id}")
    public ResponseData delete(@PathVariable Integer id) {
        siteService.deleteByPrimaryKey(id);
        return new ResponseData("删除成功", 200);
    }

}
