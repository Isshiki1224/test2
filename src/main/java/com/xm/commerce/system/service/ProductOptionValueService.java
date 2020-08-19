package com.xm.commerce.system.service;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.xm.commerce.system.entity.umino.ProductOptionValue;
import com.xm.commerce.system.mapper.umino.ProductOptionValueMapper;

@Service
public class ProductOptionValueService {

	@Resource
	private ProductOptionValueMapper productOptionValueMapper;


	public int deleteByPrimaryKey(Integer productOptionValueId) {
		return productOptionValueMapper.deleteByPrimaryKey(productOptionValueId);
	}


	public int insert(ProductOptionValue record) {
		return productOptionValueMapper.insert(record);
	}


	public int insertSelective(ProductOptionValue record) {
		return productOptionValueMapper.insertSelective(record);
	}


	public ProductOptionValue selectByPrimaryKey(Integer productOptionValueId) {
		return productOptionValueMapper.selectByPrimaryKey(productOptionValueId);
	}


	public int updateByPrimaryKeySelective(ProductOptionValue record) {
		return productOptionValueMapper.updateByPrimaryKeySelective(record);
	}


	public int updateByPrimaryKey(ProductOptionValue record) {
		return productOptionValueMapper.updateByPrimaryKey(record);
	}

}

