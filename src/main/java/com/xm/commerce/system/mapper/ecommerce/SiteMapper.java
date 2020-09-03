package com.xm.commerce.system.mapper.ecommerce;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xm.commerce.system.model.entity.ecommerce.EcommerceSite;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SiteMapper extends BaseMapper<EcommerceSite> {


    List<EcommerceSite> selectAll(@Param("siteCategory") Integer siteCategory, @Param("uid") Integer uid);

    int insertSelective(EcommerceSite record);

    int updateByPrimaryKeySelective(EcommerceSite record);

}