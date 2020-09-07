package com.xm.commerce.system.service;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.xm.commerce.system.mapper.umino.CategoryDescriptionMapper;
import com.xm.commerce.system.mapper.umino.CategoryMapper;
import com.xm.commerce.system.mapper.umino.OptionDescriptionMapper;
import com.xm.commerce.system.mapper.umino.OptionMapper;
import com.xm.commerce.system.mapper.umino.OptionValueDescriptionMapper;
import com.xm.commerce.system.mapper.umino.OptionValueMapper;
import com.xm.commerce.system.mapper.umino.ProductDescriptionMapper;
import com.xm.commerce.system.mapper.umino.ProductImageMapper;
import com.xm.commerce.system.mapper.umino.ProductMapper;
import com.xm.commerce.system.mapper.umino.ProductOptionMapper;
import com.xm.commerce.system.mapper.umino.ProductOptionValueMapper;
import com.xm.commerce.system.mapper.umino.ProductToCategoryMapper;
import com.xm.commerce.system.model.entity.ecommerce.EcommerceProductStore;
import com.xm.commerce.system.model.entity.umino.OcCategory;
import com.xm.commerce.system.model.entity.umino.OcCategoryDescription;
import com.xm.commerce.system.model.entity.umino.OcOption;
import com.xm.commerce.system.model.entity.umino.OcOptionDescription;
import com.xm.commerce.system.model.entity.umino.OcOptionValue;
import com.xm.commerce.system.model.entity.umino.OcOptionValueDescription;
import com.xm.commerce.system.model.entity.umino.OcProduct;
import com.xm.commerce.system.model.entity.umino.OcProductDescription;
import com.xm.commerce.system.model.entity.umino.OcProductImage;
import com.xm.commerce.system.model.entity.umino.OcProductOption;
import com.xm.commerce.system.model.entity.umino.OcProductOptionValue;
import com.xm.commerce.system.model.entity.umino.OcProductToCategory;
import org.json.JSONArray;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class ProductDynamicDbService {
	@Resource
	ProductMapper productMapper;
	@Resource
	ProductDescriptionMapper productDescriptionMapper;
	@Resource
	ProductImageMapper productImageMapper;
	@Resource
	CategoryMapper categoryMapper;
	@Resource
	ProductToCategoryMapper productToCategoryMapper;
	@Resource
	CategoryDescriptionMapper categoryDescriptionMapper;
	@Resource
	OptionDescriptionMapper optionDescriptionMapper;
	@Resource
	ProductOptionMapper productOptionMapper;
	@Resource
	OptionMapper optionMapper;
	@Resource
	OptionValueMapper optionValueMapper;
	@Resource
	ProductOptionValueMapper productOptionValueMapper;
	@Resource
	OptionValueDescriptionMapper optionValueDescriptionMapper;

	@DS("#siteDbName")
	public void insertCategory(String name, Integer productId, Integer parentId, String siteDbName) {
		List<OcCategoryDescription> categoryDescriptions = categoryDescriptionMapper.selectByNameAndLanguageId(name, 1);
		if (null == categoryDescriptions || categoryDescriptions.isEmpty()) {
			OcCategory category = OcCategory.builder()
					.top(true)
					.parentId(parentId)
					.column(1)
					.sortOrder(0)
					.dateAdded(new Date())
					.dateModified(new Date())
					.status(true)
					.build();
			categoryMapper.insert(category);

			Integer categoryId = category.getCategoryId();
			productToCategoryMapper.insert(new OcProductToCategory(productId, categoryId));

			OcCategoryDescription categoryDescription = OcCategoryDescription.builder()
					.categoryId(categoryId)
					.languageId(1)
					.name(name)
					.description("")
					.metaTitle(name)
					.metaDescription("")
					.metaKeyword("")
					.build();
			categoryDescriptionMapper.insert(categoryDescription);
		} else {
			productToCategoryMapper.insert(new OcProductToCategory(productId, categoryDescriptions.get(0).getCategoryId()));
		}
	}

	@DS("#siteDbName")
	public void insertProductImage(EcommerceProductStore EcommerceProductStore, Integer productId, String siteDbName) {
		String imageStr = EcommerceProductStore.getImage();
		String[] split = imageStr.split(",");
		List<String> images = Arrays.asList(split);
		images.forEach(image -> {
			OcProductImage productImage = OcProductImage.builder()
					.productId(productId)
					.image(image)
					.isRotate(true)
					.sortOrder(0)
					.build();
			productImageMapper.insert(productImage);
		});
	}

	@DS("#siteDbName")
	public void insertProductDescription(EcommerceProductStore EcommerceProductStore, Integer productId, String siteDbName) {
		OcProductDescription productDescription = OcProductDescription.builder()
				.name(EcommerceProductStore.getProductName())
				.description(EcommerceProductStore.getDescription())
				.tag(EcommerceProductStore.getMetaTagTitle())
				.metaTitle(EcommerceProductStore.getMetaTagTitle())
				.metaDescription(EcommerceProductStore.getMetaTagTitle())
				.metaKeyword(EcommerceProductStore.getMetaTagTitle())
				.productId(productId)
				.languageId(1)
				.build();
		productDescriptionMapper.insert(productDescription);
	}

	@DS("#siteDbName")
	public OcProductOption insertProductOption(Integer productId, Integer optionId, String siteDbName) {
		OcProductOption productOption = OcProductOption.builder()
				.productId(productId)
				.required(true)
				.optionId(optionId)
				.value("")
				.build();
		productOptionMapper.insert(productOption);
		return productOption;
	}

	@DS("#siteDbName")
	public void insertProductOptionValue(EcommerceProductStore EcommerceProductStore, Integer productId, Integer optionId, Integer productOptionId, Integer optionValueId, String siteDbName) {
		OcProductOptionValue productOptionValue = OcProductOptionValue.builder()
				.productOptionId(productOptionId)
				.productId(productId)
				.optionId(optionId)
				.optionValueId(optionValueId)
				.quantity(EcommerceProductStore.getQuantity())
				.subtract(true)
				.price(BigDecimal.valueOf(0))
				.pricePrefix("+")
				.points(0)
				.pointsPrefix("+")
				.weight(BigDecimal.valueOf(0.00000000))
				.weightPrefix("+")
				.build();
		productOptionValueMapper.insert(productOptionValue);
	}

	@DS("#siteDbName")
	public Integer insertProduct(EcommerceProductStore productStore, String siteDbName) {
		OcProduct product = OcProduct.builder()
				.model(productStore.getModel())
				.sku(productStore.getSku())
				.upc("")
				.ean("")
				.jan("")
				.isbn("")
				.mpn(productStore.getMpn())
				.location("")
				.quantity(productStore.getQuantity())
				.stockStatusId(6)
				.image(productStore.getImage().split(",")[0])
				.price(productStore.getPrice())
				.manufacturerId(0)
				.shipping(true)
				.points(0)
				.taxClassId(0)
				.dateAvailable(new Date())
				.weight(BigDecimal.valueOf(0))
				.weightClassId(1)
				.length(BigDecimal.valueOf(0))
				.width(BigDecimal.valueOf(0))
				.height(BigDecimal.valueOf(0))
				.lengthClassId(0)
				.subtract(true)
				.minimum(1)
				.sortOrder(0)
				.status(true)
				.viewed(0)
				.dateAdded(new Date())
				.dateModified(new Date())
				.build();
		productMapper.insert(product);
		return product.getProductId();
	}

	@DS("#siteDbName")
	public void insertOptions(EcommerceProductStore productStore, Integer productId, String key, JSONArray optionValueArray, String siteDbName) {
		List<OcOptionDescription> optionDescriptions = optionDescriptionMapper.selectByNameAndLanguageId(key, 1);
		Integer optionId;
		if (null == optionDescriptions || optionDescriptions.isEmpty()) {
			OcOption option = OcOption.builder()
					.type("select")
					.sortOrder(0)
					.build();
			optionMapper.insert(option);
			optionId = option.getOptionId();
			OcOptionDescription optionDescription = OcOptionDescription.builder()
					.optionId(optionId)
					.languageId(1)
					.name(key)
					.build();
			optionDescriptionMapper.insert(optionDescription);
		} else {
			optionId = optionDescriptions.get(0).getOptionId();
		}
		OcProductOption ProductOption = insertProductOption(productId, optionId, siteDbName);
		Integer productOptionId = ProductOption.getProductOptionId();
		optionValueArray.forEach(optionValue -> {
			List<OcOptionValueDescription> optionValueDescriptions = optionValueDescriptionMapper.selectByNameAndLanguageId(String.valueOf(optionValue), 1);
			if (null == optionValueDescriptions || optionValueDescriptions.isEmpty()) {
				OcOptionValue OptionValue = insertOptionValue(optionId, siteDbName);
				Integer optionValueId = OptionValue.getOptionValueId();
				insertOptionValueDescription(optionId, (String) optionValue, optionValueId, siteDbName);
				insertProductOptionValue(productStore, productId, optionId, productOptionId, optionValueId, siteDbName);
			} else {
				Integer optionValueId = optionValueDescriptions.get(0).getOptionValueId();
				insertProductOptionValue(productStore, productId, optionId, productOptionId, optionValueId, siteDbName);
			}
		});
	}

	@DS("#siteDbName")
	public void insertOptionValueDescription(Integer optionId, String optionValue, Integer optionValueId, String siteDbName) {
		OcOptionValueDescription optionValueDescription = OcOptionValueDescription.builder()
				.languageId(1)
				.name(optionValue)
				.optionId(optionId)
				.optionValueId(optionValueId)
				.build();
		optionValueDescriptionMapper.insert(optionValueDescription);
	}

	@DS("#siteDbName")
	public OcOptionValue insertOptionValue(Integer optionId, String siteDbName) {
		OcOptionValue optionValue = OcOptionValue.builder()
				.image("")
				.optionId(optionId)
				.sortOrder(0)
				.build();
		optionValueMapper.insert(optionValue);
		return optionValue;
	}

	@DS("#siteDbName")
	public boolean isSkuNotExist(String sku, String siteDbName) {
		LambdaQueryWrapper<OcProduct> wrapper = Wrappers.lambdaQuery();
		wrapper.eq(OcProduct::getSku, sku);
		List<OcProduct> ocProducts = productMapper.selectList(wrapper);
		return ocProducts.isEmpty();
	}
}
