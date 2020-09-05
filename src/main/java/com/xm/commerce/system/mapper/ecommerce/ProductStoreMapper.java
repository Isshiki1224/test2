package com.xm.commerce.system.mapper.ecommerce;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xm.commerce.system.model.entity.ecommerce.EcommerceProductStore;
import com.xm.commerce.system.model.request.CategoryRequest;
import org.apache.catalina.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProductStoreMapper extends BaseMapper<EcommerceProductStore> {


    List<EcommerceProductStore> selectByCategory(CategoryRequest categoryRequest);

    int deleteByBatch(@Param("ids") List<Integer> ids);


    List<EcommerceProductStore> selectByNameAndUid(@Param("productName") String productName, @Param("uid") Integer uid);


    int updateByPrimaryKeySelective(EcommerceProductStore record);


    List<EcommerceProductStore> selectByIds(@Param("ids") List<Integer> ids);

    int insertSelective(EcommerceProductStore record);

}