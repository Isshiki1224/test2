package com.xm.commerce.system.service;

import com.xm.commerce.system.mapper.ecommerce.CategorieMapper;
import com.xm.commerce.system.model.entity.ecommerce.Categorie;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

	private final CategorieMapper categorieMapper;

	public CategoryService(CategorieMapper categorieMapper) {
		this.categorieMapper = categorieMapper;
	}

	public List<Categorie> searchCategory(String txt) {
		return categorieMapper.findCategorieByName(txt);
	}
}
