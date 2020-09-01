package com.xm.commerce.system.mapper.ecommerce;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xm.commerce.system.model.entity.ecommerce.EcommerceCategory;

import java.util.List;

public interface CategorieMapper extends BaseMapper<EcommerceCategory> {


    List<EcommerceCategory> findCategorieByName(String name);

    EcommerceCategory selectByName(String name);
}