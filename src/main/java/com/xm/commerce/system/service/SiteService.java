package com.xm.commerce.system.service;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.xm.commerce.system.mapper.ecommerce.SiteMapper;
import com.xm.commerce.system.model.entity.ecommerce.Site;
@Service
public class SiteService{

    @Resource
    private SiteMapper siteMapper;

    
    public int deleteByPrimaryKey(Integer id) {
        return siteMapper.deleteByPrimaryKey(id);
    }

    
    public int insert(Site record) {
        return siteMapper.insert(record);
    }

    
    public int insertSelective(Site record) {
        return siteMapper.insertSelective(record);
    }

    
    public Site selectByPrimaryKey(Integer id) {
        return siteMapper.selectByPrimaryKey(id);
    }

    
    public int updateByPrimaryKeySelective(Site record) {
        return siteMapper.updateByPrimaryKeySelective(record);
    }

    
    public int updateByPrimaryKey(Site record) {
        return siteMapper.updateByPrimaryKey(record);
    }

}
