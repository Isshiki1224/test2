package com.xm.commerce.system.service;

import com.xm.commerce.system.entity.*;
import com.xm.commerce.system.util.DateUtil;
import com.xm.commerce.system.web.umino.service.*;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
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
    OcProductService ocProductService;
    @Resource
    OcProductDescriptionService ocProductDescriptionService;
    @Resource
    OcProductImageService ocProductImageService;
    @Resource
    OcCategoryService ocCategoryService;
    @Resource
    OcProductToCategoryService ocProductToCategoryService;
    @Resource
    OcCategoryDescriptionService ocCategoryDescriptionService;
    @Resource
    OcOptionDescriptionService ocOptionDescriptionService;
    @Resource
    OcProductOptionService ocProductOptionService;
    @Resource
    OcOptionService ocOptionService;
    @Resource
    OcOptionValueService ocOptionValueService;
    @Resource
    OcProductOptionValueService ocProductOptionValueService;
    @Resource
    OcOptionValueDescriptionService ocOptionValueDescriptionService;

    @SuppressWarnings(value = {"unchecked", "rawtypes"})
    @Transactional(rollbackFor = Exception.class)
    public boolean upload2OpenCart(Integer id){

        OcProductStore ocProductStore = null;

        Integer productId = insertProduct(ocProductStore);
        insertOcProductDescription(ocProductStore, productId);
        insertOcProductImage(ocProductStore, productId);
        insertCategory(ocProductStore, productId);

        String options = ocProductStore.getProductOptions();
        JSONObject jsonObject = JSONObject.fromObject(options);
        Iterator keys = jsonObject.keys();
        while (keys.hasNext()) {
            String key = (String) keys.next();
            System.out.println("key= " + key);
            JSONArray optionValueArray = JSONArray.fromObject(jsonObject.get(key));
            OcOptionDescription ocOptionDescription = ocOptionDescriptionService.selectByNameAndLanguage(key, 1);
            if (null == ocOptionDescription) {
                OcOption ocOption = OcOption.builder()
                        .type("select")
                        .sortOrder(0)
                        .build();
                ocOptionService.insert(ocOption);
                Integer optionId = ocOption.getOptionId();
                ocOptionDescription = OcOptionDescription.builder()
                        .optionId(optionId)
                        .languageId(1)
                        .name(key)
                        .build();
                ocOptionDescriptionService.insert(ocOptionDescription);
                productToOptionValue(ocProductStore, productId, optionValueArray, optionId);
            } else {
                Integer optionId = ocOptionDescription.getOptionId();
                productToOptionValue(ocProductStore, productId, optionValueArray, optionId);
            }

        }


        return true;
    }

    private void productToOptionValue(OcProductStore ocProductStore, Integer productId, JSONArray optionValueArray, Integer optionId) {
        OcProductOption ocProductOption = insertOcProductOption(productId, optionId);
        Integer productOptionId = ocProductOption.getProductOptionId();
        optionValueArray.forEach(optionValue -> {
            OcOptionValueDescription ocOptionValueDescription = ocOptionValueDescriptionService.selectByNameAndLanguage((String) optionValue, 1);
            if (null == ocOptionValueDescription) {
                OcOptionValue ocOptionValue = insertOcOptionValue(optionId);
                Integer optionValueId = ocOptionValue.getOptionValueId();
                insertOptionValueDescription(optionId, (String) optionValue);
                insertProductOptionValue(ocProductStore, productId, optionId, productOptionId, optionValueId);
            } else {
                Integer optionValueId = ocOptionValueDescription.getOptionValueId();
                insertProductOptionValue(ocProductStore, productId, optionId, productOptionId, optionValueId);
            }
        });
    }

    private void insertOptionValueDescription(Integer optionId, String optionValue) {
        OcOptionValueDescription ocOptionValueDescription;
        ocOptionValueDescription = OcOptionValueDescription.builder()
                .languageId(1)
                .name(optionValue)
                .optionId(optionId)
                .build();
        ocOptionValueDescriptionService.insert(ocOptionValueDescription);
    }

    private OcOptionValue insertOcOptionValue(Integer optionId) {
        OcOptionValue ocOptionValue = OcOptionValue.builder()
                .image("")
                .optionId(optionId)
                .sortOrder(0)
                .build();
        ocOptionValueService.insert(ocOptionValue);

        return ocOptionValue;
    }

    private OcProductOption insertOcProductOption(Integer productId, Integer optionId) {
        OcProductOption ocProductOption = OcProductOption.builder()
                .productId(productId)
                .required(true)
                .optionId(optionId)
                .value("")
                .build();
        ocProductOptionService.insertSelective(ocProductOption);
        return ocProductOption;
    }

    private void insertProductOptionValue(OcProductStore ocProductStore, Integer productId, Integer optionId, Integer productOptionId, Integer optionValueId) {
        OcProductOptionValue ocProductOptionValue = OcProductOptionValue.builder()
                .productOptionId(productOptionId)
                .productId(productId)
                .optionId(optionId)
                .optionValueId(optionValueId)
                .quantity(ocProductStore.getQuantity())
                .subtract(true)
                .price(BigDecimal.valueOf(0))
                .pricePrefix("+")
                .points(0)
                .pointsPrefix("+")
                .weight(BigDecimal.valueOf(0.00000000))
                .weightPrefix("+")
                .build();
        ocProductOptionValueService.insert(ocProductOptionValue);
    }

    private void insertCategory(OcProductStore ocProductStore, Integer productId) {
        OcCategoryDescription ocCategoryDescription = ocCategoryDescriptionService.selectByNameAndLanguage(ocProductStore.getCategory(), 1);
        if (null == ocCategoryDescription) {
            OcCategory ocCategory = OcCategory.builder()
                    .top(true)
                    .parentId(0)
                    .column(1)
                    .sortOrder(0)
                    .dateAdded(DateUtil.dateNow())
                    .dateModified(DateUtil.dateNow())
                    .status(true)
                    .build();
            ocCategoryService.insertSelective(ocCategory);

            Integer categoryId = ocCategory.getCategoryId();
            ocProductToCategoryService.insertSelective(new OcProductToCategory(productId, categoryId));

            ocCategoryDescription = OcCategoryDescription.builder()
                    .categoryId(categoryId)
                    .languageId(1)
                    .name(ocProductStore.getCategory())
                    .description("")
                    .metaTitle(ocProductStore.getCategory())
                    .metaDescription("")
                    .metaKeyword("")
                    .build();
            ocCategoryDescriptionService.insert(ocCategoryDescription);
        } else {
            ocProductToCategoryService.insertSelective(new OcProductToCategory(productId, ocCategoryDescription.getCategoryId()));
        }
    }

    private void insertOcProductImage(OcProductStore ocProductStore, Integer productId) {
        String imageStr = ocProductStore.getImage();
        String[] split = imageStr.split(",");
        List<String> images = Arrays.asList(split);
        images.forEach(image -> {
            OcProductImage ocProductImage = OcProductImage.builder()
                    .productId(productId)
                    .image(image)
                    .isRotate(true)
                    .sortOrder(0)
                    .build();
            ocProductImageService.insertSelective(ocProductImage);
        });
    }

    private void insertOcProductDescription(OcProductStore ocProductStore, Integer productId) {
        OcProductDescription ocProductDescription = OcProductDescription.builder()
                .name(ocProductStore.getProductName())
                .description(ocProductStore.getDescription())
                .tag(ocProductStore.getMetaTagTitle())
                .metaTitle(ocProductStore.getMetaTagTitle())
                .metaDescription(ocProductStore.getMetaTagTitle())
                .metaKeyword(ocProductStore.getMetaTagTitle())
                .productId(productId)
                .languageId(1)
                .build();
        ocProductDescriptionService.insert(ocProductDescription);
    }

    private Integer insertProduct(OcProductStore ocProductStore) {
        OcProduct ocProduct = OcProduct.builder()
                .model(ocProductStore.getModel())
                .sku(ocProductStore.getSku())
                .mpn(ocProductStore.getMpn())
                .quantity(ocProductStore.getQuantity())
                .price(ocProductStore.getPrice())
                .manufacturerId(0)
                .taxClassId(0)
                .status(true)
                .stockStatusId(7)
                .build();
        ocProductService.insert(ocProduct);

        return ocProduct.getProductId();
    }

}
