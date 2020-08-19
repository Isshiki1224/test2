package com.xm.commerce.system.service;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.xm.commerce.system.entity.umino.ProductDescription;
import com.xm.commerce.system.mapper.umino.ProductDescriptionMapper;

@Service
public class ProductDescriptionService {

	@Resource
	private ProductDescriptionMapper productDescriptionMapper;


	public int deleteByPrimaryKey(Integer productId, Integer languageId) {
		return productDescriptionMapper.deleteByPrimaryKey(productId, languageId);
	}


	public int insert(ProductDescription record) {
		return productDescriptionMapper.insert(record);
	}


	public int insertSelective(ProductDescription record) {
		return productDescriptionMapper.insertSelective(record);
	}


	public ProductDescription selectByPrimaryKey(Integer productId, Integer languageId) {
		return productDescriptionMapper.selectByPrimaryKey(productId, languageId);
	}


	public int updateByPrimaryKeySelective(ProductDescription record) {
		return productDescriptionMapper.updateByPrimaryKeySelective(record);
	}


	public int updateByPrimaryKey(ProductDescription record) {
		return productDescriptionMapper.updateByPrimaryKey(record);
	}

}

