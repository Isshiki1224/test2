package com.xm.commerce.system.service;

import com.xm.commerce.common.datasource.util.LoadDataSourceUtil;
import com.xm.commerce.common.exception.CurrentUserException;
import com.xm.commerce.security.util.CurrentUserUtils;
import com.xm.commerce.system.mapper.ecommerce.SiteMapper;
import com.xm.commerce.system.model.entity.ecommerce.EcommerceSite;
import com.xm.commerce.system.model.entity.ecommerce.EcommerceUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

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
        if (null == user) {
            throw new CurrentUserException();
        }
        record.setUid(user.getId());
        CopyOnWriteArraySet<String> now = loadDataSourceUtil.now();
        if (record.getSiteCategory()) {
            if (!now.contains(record.getDbName())) {
                if (loadDataSourceUtil.add(record)) {
                    return siteMapper.insertSelective(record);
                } else {
                    throw new RuntimeException();
                }
            }
        }
        return siteMapper.insertSelective(record);
    }

    public EcommerceSite selectByPrimaryKey(Integer id) {
        return siteMapper.selectById(id);
    }

    public int updateByPrimaryKeySelective(EcommerceSite record) {
        EcommerceUser user = currentUserUtils.getCurrentUser();
        if (null == user) {
            throw new CurrentUserException();
        }
        CopyOnWriteArraySet<String> now = loadDataSourceUtil.now();
        record.setUid(user.getId());

        if (record.getSiteCategory()) {
            if (!now.contains(record.getDbName())) {
                if (loadDataSourceUtil.add(record)) {
                    return siteMapper.updateByPrimaryKeySelective(record);
                } else {
                    throw new RuntimeException();
                }
            }
        }
        return siteMapper.updateByPrimaryKeySelective(record);
    }

    public List<EcommerceSite> selectAll(Integer siteCategory, Integer uid) {
        return siteMapper.selectAll(siteCategory, uid);
    }
}



