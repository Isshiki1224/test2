package com.xm.commerce.system.service;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.xm.commerce.system.entity.umino.ProductToCategory;
import com.xm.commerce.system.mapper.umino.ProductToCategoryMapper;

@Service
public class ProductToCategoryService {

	@Resource
	private ProductToCategoryMapper productToCategoryMapper;


	public int deleteByPrimaryKey(Integer productId, Integer categoryId) {
		return productToCategoryMapper.deleteByPrimaryKey(productId, categoryId);
	}


	public int insert(ProductToCategory record) {
		return productToCategoryMapper.insert(record);
	}


	public int insertSelective(ProductToCategory record) {
		return productToCategoryMapper.insertSelective(record);
	}

}

