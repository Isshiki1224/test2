package com.xm.commerce.system.mapper.ecommerce;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xm.commerce.system.model.entity.ecommerce.EcommerceProductStore;
import com.xm.commerce.system.model.request.CategoryRequest;import org.apache.ibatis.annotations.Param;import java.util.List;

public interface ProductStoreMapper extends BaseMapper<EcommerceProductStore> {


    List<EcommerceProductStore> selectByCategory(CategoryRequest categoryRequest);

    int deleteByBatch(@Param("ids") List<Integer> ids);


    List<EcommerceProductStore> selectByName(String productName);


    int updateByPrimaryKeySelective(EcommerceProductStore record);


}