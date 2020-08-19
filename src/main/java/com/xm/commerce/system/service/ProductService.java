package com.xm.commerce.system.service;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.xm.commerce.system.entity.umino.Product;
import com.xm.commerce.system.mapper.umino.ProductMapper;

@Service
public class ProductService {

	@Resource
	private ProductMapper productMapper;


	public int deleteByPrimaryKey(Integer productId) {
		return productMapper.deleteByPrimaryKey(productId);
	}


	public int insert(Product record) {
		return productMapper.insert(record);
	}


	public int insertSelective(Product record) {
		return productMapper.insertSelective(record);
	}


	public Product selectByPrimaryKey(Integer productId) {
		return productMapper.selectByPrimaryKey(productId);
	}


	public int updateByPrimaryKeySelective(Product record) {
		return productMapper.updateByPrimaryKeySelective(record);
	}


	public int updateByPrimaryKey(Product record) {
		return productMapper.updateByPrimaryKey(record);
	}

}

