package com.xm.commerce.system.service;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.xm.commerce.system.mapper.umino.ProductOptionMapper;
import com.xm.commerce.system.entity.umino.ProductOption;

@Service
public class ProductOptionService {

	@Resource
	private ProductOptionMapper productOptionMapper;


	public int deleteByPrimaryKey(Integer productOptionId) {
		return productOptionMapper.deleteByPrimaryKey(productOptionId);
	}


	public int insert(ProductOption record) {
		return productOptionMapper.insert(record);
	}


	public int insertSelective(ProductOption record) {
		return productOptionMapper.insertSelective(record);
	}


	public ProductOption selectByPrimaryKey(Integer productOptionId) {
		return productOptionMapper.selectByPrimaryKey(productOptionId);
	}


	public int updateByPrimaryKeySelective(ProductOption record) {
		return productOptionMapper.updateByPrimaryKeySelective(record);
	}


	public int updateByPrimaryKey(ProductOption record) {
		return productOptionMapper.updateByPrimaryKey(record);
	}

}

