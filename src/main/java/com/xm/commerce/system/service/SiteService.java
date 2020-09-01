package com.xm.commerce.system.service;

import com.baomidou.dynamic.datasource.DynamicRoutingDataSource;
import com.baomidou.dynamic.datasource.creator.BasicDataSourceCreator;
import com.baomidou.dynamic.datasource.creator.DataSourceCreator;
import com.baomidou.dynamic.datasource.spring.boot.autoconfigure.DataSourceProperty;
import com.google.common.collect.ImmutableMap;
import com.xm.commerce.common.datasource.util.LoadDataSourceUtil;
import com.xm.commerce.common.exception.ResourceNotFoundException;
import com.xm.commerce.system.model.entity.ecommerce.DataSourceConfig;
import com.xm.commerce.system.model.entity.ecommerce.EcommerceSite;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import javax.sql.DataSource;

import com.xm.commerce.system.mapper.ecommerce.SiteMapper;

import java.util.List;
import java.util.Set;

@Slf4j
@Service
public class SiteService {

    @Resource
    private SiteMapper siteMapper;
    @Resource
    private LoadDataSourceUtil loadDataSourceUtil;

    public int deleteByPrimaryKey(Integer id) {
        return siteMapper.deleteById(id);
    }

    public int insertSelective(EcommerceSite record) {
        if (record.getSiteCategory()){
            if (loadDataSourceUtil.add(record)) {
                return siteMapper.insert(record);
            }
            log.info("站点连接已存在");
        }
        return siteMapper.insert(record);
    }

    public EcommerceSite selectByPrimaryKey(Integer id) {
        return siteMapper.selectById(id);
    }

    public int updateByPrimaryKeySelective(EcommerceSite record) {
        if (record.getSiteCategory()){
            loadDataSourceUtil.add(record);
            return siteMapper.updateById(record);
        }
        return siteMapper.updateById(record);
    }

    public List<EcommerceSite> selectAll(Integer siteCategory) {
        return siteMapper.selectAll(siteCategory);
    }
}



