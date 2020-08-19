package com.xm.commerce.system.service;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.xm.commerce.system.mapper.umino.ProductAttributeMapper;
import com.xm.commerce.system.entity.umino.ProductAttribute;

@Service
public class ProductAttributeService {

	@Resource
	private ProductAttributeMapper productAttributeMapper;


	public int deleteByPrimaryKey(Integer productId, Integer attributeId, Integer languageId) {
		return productAttributeMapper.deleteByPrimaryKey(productId, attributeId, languageId);
	}


	public int insert(ProductAttribute record) {
		return productAttributeMapper.insert(record);
	}


	public int insertSelective(ProductAttribute record) {
		return productAttributeMapper.insertSelective(record);
	}


	public ProductAttribute selectByPrimaryKey(Integer productId, Integer attributeId, Integer languageId) {
		return productAttributeMapper.selectByPrimaryKey(productId, attributeId, languageId);
	}


	public int updateByPrimaryKeySelective(ProductAttribute record) {
		return productAttributeMapper.updateByPrimaryKeySelective(record);
	}


	public int updateByPrimaryKey(ProductAttribute record) {
		return productAttributeMapper.updateByPrimaryKey(record);
	}

}

