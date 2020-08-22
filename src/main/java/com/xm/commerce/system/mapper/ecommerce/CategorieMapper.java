package com.xm.commerce.system.mapper.ecommerce;

import com.xm.commerce.system.model.entity.ecommerce.Categorie;

import java.util.List;

public interface CategorieMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Categorie record);

    int insertSelective(Categorie record);

    Categorie selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Categorie record);

    int updateByPrimaryKey(Categorie record);

    List<Categorie> findCategorieByName(String name);
}