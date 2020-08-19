package com.xm.commerce.system.mapper.ecommerce;

import com.xm.commerce.system.entity.ecommerce.Site;

public interface SiteMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Site record);

    int insertSelective(Site record);

    Site selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Site record);

    int updateByPrimaryKey(Site record);
}