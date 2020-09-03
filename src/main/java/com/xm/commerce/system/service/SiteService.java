package com.xm.commerce.system.service;

import com.baomidou.dynamic.datasource.DynamicRoutingDataSource;
import com.baomidou.dynamic.datasource.creator.BasicDataSourceCreator;
import com.baomidou.dynamic.datasource.creator.DataSourceCreator;
import com.baomidou.dynamic.datasource.spring.boot.autoconfigure.DataSourceProperty;
import com.google.common.collect.ImmutableMap;
import com.xm.commerce.common.datasource.util.LoadDataSourceUtil;
import com.xm.commerce.common.exception.CurrentUserException;
import com.xm.commerce.common.exception.ResourceNotFoundException;
import com.xm.commerce.security.util.CurrentUserUtils;
import com.xm.commerce.system.model.entity.ecommerce.DataSourceConfig;
import com.xm.commerce.system.model.entity.ecommerce.EcommerceSite;
import com.xm.commerce.system.model.entity.ecommerce.EcommerceUser;
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
    @Resource
    private CurrentUserUtils currentUserUtils;

    public int deleteByPrimaryKey(Integer id) {
        return siteMapper.deleteById(id);
    }

    public int insertSelective(EcommerceSite record) {
        EcommerceUser user = currentUserUtils.getCurrentUser();
        if (null == user){
            throw new CurrentUserException();
        }
        if (record.getSiteCategory()){
            if (loadDataSourceUtil.add(record)) {
                return siteMapper.insert(record);
            }
            log.info("站点连接已存在");
        }
        record.setUid(user.getId());
        return siteMapper.insertSelective(record);
    }

    public EcommerceSite selectByPrimaryKey(Integer id) {
        return siteMapper.selectById(id);
    }

    public int updateByPrimaryKeySelective(EcommerceSite record) {
        EcommerceUser user = currentUserUtils.getCurrentUser();
        if (null == user){
            throw new CurrentUserException();
        }
        record.setUid(user.getId());
        if (record.getSiteCategory()){
            loadDataSourceUtil.add(record);
            return siteMapper.updateByPrimaryKeySelective(record);
        }
        return siteMapper.updateByPrimaryKeySelective(record);
    }

    public List<EcommerceSite> selectAll(Integer siteCategory, Integer uid) {
        return siteMapper.selectAll(siteCategory, uid);
    }
}



