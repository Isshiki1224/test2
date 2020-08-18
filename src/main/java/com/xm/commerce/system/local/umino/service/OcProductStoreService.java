package com.xm.commerce.system.local.umino.service;

import com.xm.commerce.system.entity.OcProductStore;
import com.xm.commerce.system.request.CategoryRequest;
import com.xm.commerce.system.response.OcProductStoreResponse;

import java.util.List;

public interface OcProductStoreService {


    int deleteByPrimaryKey(Integer id);

    int insert(OcProductStore record);

    int insertSelective(OcProductStore record);

    OcProductStore selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(OcProductStore record);

    int updateByPrimaryKey(OcProductStore record);

    List<OcProductStoreResponse> selectByCategory(CategoryRequest categoryRequest);

}





