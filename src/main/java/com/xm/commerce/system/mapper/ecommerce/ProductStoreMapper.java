package com.xm.commerce.system.mapper.ecommerce;

import com.xm.commerce.system.model.entity.ecommerce.ProductStore;
import com.xm.commerce.system.model.request.CategoryRequest;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProductStoreMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ProductStore record);

    int insertSelective(ProductStore record);

    ProductStore selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ProductStore record);

    int updateByPrimaryKey(ProductStore record);

    List<ProductStore> selectByCategory(CategoryRequest categoryRequest);

    int deleteByBatch(@Param("ids") List<Integer> ids);
}