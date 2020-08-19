package com.xm.commerce.system.service;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.xm.commerce.system.entity.umino.ProductImage;
import com.xm.commerce.system.mapper.umino.ProductImageMapper;

@Service
public class ProductImageService {

	@Resource
	private ProductImageMapper productImageMapper;


	public int deleteByPrimaryKey(Integer productImageId) {
		return productImageMapper.deleteByPrimaryKey(productImageId);
	}


	public int insert(ProductImage record) {
		return productImageMapper.insert(record);
	}


	public int insertSelective(ProductImage record) {
		return productImageMapper.insertSelective(record);
	}


	public ProductImage selectByPrimaryKey(Integer productImageId) {
		return productImageMapper.selectByPrimaryKey(productImageId);
	}


	public int updateByPrimaryKeySelective(ProductImage record) {
		return productImageMapper.updateByPrimaryKeySelective(record);
	}


	public int updateByPrimaryKey(ProductImage record) {
		return productImageMapper.updateByPrimaryKey(record);
	}

}

