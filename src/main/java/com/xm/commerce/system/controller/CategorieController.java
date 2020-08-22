package com.xm.commerce.system.controller;

import com.xm.commerce.system.model.entity.ecommerce.Categorie;
import com.xm.commerce.system.model.response.ResponseCode;
import com.xm.commerce.system.model.response.ResponseData;
import com.xm.commerce.system.service.CategoryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("category")
public class CategorieController {

	private final CategoryService categoryService;

	public CategorieController(CategoryService categoryService) {
		this.categoryService = categoryService;
	}

	@GetMapping("searchCategory")
	public ResponseData searchCategoryByName(String name) {
		List<Categorie> categories = categoryService.searchCategory(name);
		return new ResponseData(ResponseCode.SUCCESS, categories);
	}
}
