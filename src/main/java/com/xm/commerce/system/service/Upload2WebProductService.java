package com.xm.commerce.system.service;


import com.xm.commerce.system.model.entity.ecommerce.ProductStore;
import com.xm.commerce.system.model.entity.umino.*;
import com.xm.commerce.system.mapper.umino.*;
import com.xm.commerce.system.util.DateUtil;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

@Service
public class Upload2WebProductService {
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

    @SuppressWarnings(value = {"rawtypes"})
    @Transactional(rollbackFor = Exception.class)
    public boolean upload2OpenCart(Integer id){

        // todo
        ProductStore productStore = null;

        Integer productId = insertProduct(productStore);
        insertProductDescription(productStore, productId);
        insertProductImage(productStore, productId);
        insertCategory(productStore, productId);

        String options = productStore.getProductOptions();
        JSONObject jsonObject = new JSONObject(options);
        Iterator keys = jsonObject.keys();
        while (keys.hasNext()) {
            String key = (String) keys.next();
            System.out.println("key= " + key);
            JSONArray optionValueArray = new JSONArray(jsonObject.get(key));
            OptionDescription optionDescription = optionDescriptionMapper.selectByNameAndLanguageId(key, 1);
            if (null == optionDescription) {
                Option option = Option.builder()
                        .type("select")
                        .sortOrder(0)
                        .build();
                optionMapper.insert(option);
                Integer optionId = option.getOptionId();
                optionDescription = OptionDescription.builder()
                        .optionId(optionId)
                        .languageId(1)
                        .name(key)
                        .build();
                optionDescriptionMapper.insert(optionDescription);
                productToOptionValue(productStore, productId, optionValueArray, optionId);
            } else {
                Integer optionId = optionDescription.getOptionId();
                productToOptionValue(productStore, productId, optionValueArray, optionId);
            }

        }


        return true;
    }

    private void productToOptionValue(ProductStore ProductStore, Integer productId, JSONArray optionValueArray, Integer optionId) {
        ProductOption ProductOption = insertProductOption(productId, optionId);
        Integer productOptionId = ProductOption.getProductOptionId();
        optionValueArray.forEach(optionValue -> {
            OptionValueDescription optionValueDescription = optionValueDescriptionMapper.selectByNameAndLanguageId(String.valueOf(optionValue), 1);
            if (null == optionValueDescription) {
                OptionValue OptionValue = insertOptionValue(optionId);
                Integer optionValueId = OptionValue.getOptionValueId();
                insertOptionValueDescription(optionId, (String) optionValue);
                insertProductOptionValue(ProductStore, productId, optionId, productOptionId, optionValueId);
            } else {
                Integer optionValueId = optionValueDescription.getOptionValueId();
                insertProductOptionValue(ProductStore, productId, optionId, productOptionId, optionValueId);
            }
        });
    }

    private void insertOptionValueDescription(Integer optionId, String optionValue) {
        OptionValueDescription optionValueDescription = OptionValueDescription.builder()
                .languageId(1)
                .name(optionValue)
                .optionId(optionId)
                .build();
        optionValueDescriptionMapper.insert(optionValueDescription);
    }

    private OptionValue insertOptionValue(Integer optionId) {
        OptionValue optionValue = OptionValue.builder()
                .image("")
                .optionId(optionId)
                .sortOrder(0)
                .build();
        optionValueMapper.insert(optionValue);

        return optionValue;
    }

    private ProductOption insertProductOption(Integer productId, Integer optionId) {
        ProductOption productOption = ProductOption.builder()
                .productId(productId)
                .required(true)
                .optionId(optionId)
                .value("")
                .build();
        productOptionMapper.insertSelective(productOption);
        return productOption;
    }

    private void insertProductOptionValue(ProductStore ProductStore, Integer productId, Integer optionId, Integer productOptionId, Integer optionValueId) {
        ProductOptionValue productOptionValue = ProductOptionValue.builder()
                .productOptionId(productOptionId)
                .productId(productId)
                .optionId(optionId)
                .optionValueId(optionValueId)
                .quantity(ProductStore.getQuantity())
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

    private void insertCategory(ProductStore productStore, Integer productId) {
        CategoryDescription categoryDescription = categoryDescriptionMapper.selectByNameAndLanguageId(productStore.getCategory(), 1);
        if (null == categoryDescription) {
            Category category = Category.builder()
                    .top(true)
                    .parentId(0)
                    .column(1)
                    .sortOrder(0)
                    .dateAdded(DateUtil.dateNow())
                    .dateModified(DateUtil.dateNow())
                    .status(true)
                    .build();
            categoryMapper.insertSelective(category);

            Integer categoryId = category.getCategoryId();
            productToCategoryMapper.insertSelective(new ProductToCategory(productId, categoryId));

            categoryDescription = CategoryDescription.builder()
                    .categoryId(categoryId)
                    .languageId(1)
                    .name(productStore.getCategory())
                    .description("")
                    .metaTitle(productStore.getCategory())
                    .metaDescription("")
                    .metaKeyword("")
                    .build();
            categoryDescriptionMapper.insert(categoryDescription);
        } else {
            productToCategoryMapper.insertSelective(new ProductToCategory(productId, categoryDescription.getCategoryId()));
        }
    }

    private void insertProductImage(ProductStore ProductStore, Integer productId) {
        String imageStr = ProductStore.getImage();
        String[] split = imageStr.split(",");
        List<String> images = Arrays.asList(split);
        images.forEach(image -> {
            ProductImage productImage = ProductImage.builder()
                    .productId(productId)
                    .image(image)
                    .isRotate(true)
                    .sortOrder(0)
                    .build();
            productImageMapper.insertSelective(productImage);
        });
    }

    private void insertProductDescription(ProductStore ProductStore, Integer productId) {
        ProductDescription productDescription = ProductDescription.builder()
                .name(ProductStore.getProductName())
                .description(ProductStore.getDescription())
                .tag(ProductStore.getMetaTagTitle())
                .metaTitle(ProductStore.getMetaTagTitle())
                .metaDescription(ProductStore.getMetaTagTitle())
                .metaKeyword(ProductStore.getMetaTagTitle())
                .productId(productId)
                .languageId(1)
                .build();
        productDescriptionMapper.insert(productDescription);
    }

    private Integer insertProduct(ProductStore productStore) {
        Product product = Product.builder()
                .model(productStore.getModel())
                .sku(productStore.getSku())
                .mpn(productStore.getMpn())
                .quantity(productStore.getQuantity())
                .price(productStore.getPrice())
                .manufacturerId(0)
                .taxClassId(0)
                .status(true)
                .stockStatusId(7)
                .build();
        productMapper.insert(product);

        return product.getProductId();
    }

}
