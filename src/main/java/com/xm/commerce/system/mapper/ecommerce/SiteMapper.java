package com.xm.commerce.system.mapper.ecommerce;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xm.commerce.system.model.entity.ecommerce.EcommerceSite;

import java.util.List;

public interface SiteMapper extends BaseMapper<EcommerceSite> {


    List<EcommerceSite> selectAll(Integer siteCategory);
}