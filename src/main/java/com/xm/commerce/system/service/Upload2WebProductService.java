package com.xm.commerce.system.service;


import com.xm.commerce.system.mapper.ecommerce.ProductStoreMapper;
import com.xm.commerce.system.mapper.ecommerce.SiteMapper;
import com.xm.commerce.system.model.entity.ecommerce.ProductStore;
import com.xm.commerce.system.model.entity.ecommerce.Site;
import com.xm.commerce.system.model.entity.umino.*;
import com.xm.commerce.system.mapper.umino.*;
import com.xm.commerce.system.util.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

@Slf4j
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
    @Resource
    ProductStoreMapper productStoreMapper;
    @Resource
    SiteMapper siteMapper;
    @Resource
    RestTemplate restTemplate;

    private static final String shopifyUrl = "https://bestrylife.myshopify.com/admin/api/2020-07/products.json";
    private static final String shopifyToken = "Basic ZDM1MWU0ZDkzNzY1ZmYzNDc1Y2I1MGVjYjM0MDIzYjU6c2hwcGFfYTUyMTdmYTU0YWQ0NWEwN2JhNjVkZWQxN2VhYWJmYzM=";
    private static final String openCartRedirect = "https://www.asmater.com/admin/index.php?route=common/login";

    @SuppressWarnings(value = {"rawtypes"})
    @Transactional(rollbackFor = Exception.class)
    public boolean upload2OpenCart(Integer id) {



        // todo
        ProductStore productStore = productStoreMapper.selectByPrimaryKey(id);

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

    public boolean upload2Shopify(Integer productId) {
        ProductStore productStore = productStoreMapper.selectByPrimaryKey(productId);
        String productOptions = productStore.getProductOptions();
        JSONObject jsonObject = new JSONObject(productOptions);
        Iterator<String> keys = jsonObject.keys();
        List<Map<String, Object>> options = new ArrayList<>();
        while (keys.hasNext()) {
            String name = keys.next();
            Object optionsValue = jsonObject.get(name);
            Map<String, Object> option = new HashMap<>();
            option.put("name", name);
            option.put("values", optionsValue);
            options.add(option);
        }

        List<Object> lists = new ArrayList<>();
        options.forEach(map -> lists.addAll(Collections.singletonList(map.get("values"))));

        List<Object> result = getVariantsList(lists);

        List<Map<String, Object>> variants = new ArrayList<>();
        if (result != null) {
            Object o = result.get(0);
            if (o instanceof List<?>) {
                List<?> array = (ArrayList<?>) o;
                array.forEach(s -> {
                    Map<String, Object> variant = new HashMap<>();
                    Object[] ss = (Object[]) s;
                    for (int i = 0; i < ss.length; i++) {
                        variant.put("option" + (i + 1), ss[i]);
                    }
                    variant.put("price", productStore.getPrice());
                    variant.put("inventory_quantity", productStore.getQuantity());
                    variant.put("sku", productStore.getSku());
                    variants.add(variant);
                });
            }
        }

        List<Map<String, String>> images = new ArrayList<>();
        for (String image : productStore.getImage().split(",")) {
            Map<String, String> temp = new HashMap<>();
            temp.put("src", image);
            images.add(temp);
        }

        Map<String, Map<String, Object>> productParam = new HashMap<>();
        Map<String, Object> mapParam = new HashMap<>();
        mapParam.put("title", productStore.getProductName());
        mapParam.put("body_html", productStore.getDescription());
        mapParam.put("vendor", "Bestrylife");
        mapParam.put("product_type", productStore.getCategory());
        mapParam.put("tags", productStore.getCategory());
        mapParam.put("images", images);
        mapParam.put("variants", variants);
        mapParam.put("options", options);
        productParam.put("product", mapParam);
        log.info(productParam.toString());

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.set("Authorization", shopifyToken);
        HttpEntity<Map<String, Map<String, Object>>> request = new HttpEntity<>(productParam, httpHeaders);
        String body = restTemplate.postForEntity(shopifyUrl, request, String.class).getBody();
        log.info(body);
        return true;
    }

    private List<Object> getVariantsList(List<Object> lists) {
        List<Object> result = new ArrayList<>();
        if (lists.size() == 0) {
            return null;
        } else if (lists.size() == 1) {
            return lists;
        } else {
            JSONArray options1 = (JSONArray) lists.get(0);
            JSONArray options2 = (JSONArray) lists.get(1);
            options1.forEach(option1 -> {
                options2.forEach(option2 -> {
                    Object[] temp = {option1, option2};
                    result.add(temp);
                });
            });
            lists.remove(0);
            lists.remove(0);
            lists.add(result);
            return getVariantsList(lists);
        }
    }


    public boolean uploadPic2OpenCart(Integer siteId){



        Site site = siteMapper.selectByPrimaryKey(siteId);
        String domain = site.getDomain();
//        String url = domain + "?route=common/login";
        String url = domain + "?route=common/filemanager/upload&directory=Toys&user_token=Ek0QgvIcB0HCLCKhVYrASmaZvSX9EqzU";
        HttpHeaders httpHeaders = new HttpHeaders();

        httpHeaders.setContentType(MediaType.MULTIPART_FORM_DATA);
        MultiValueMap<String, String> loginParam = new LinkedMultiValueMap<>();
        loginParam.add("username", site.getAccount());
        loginParam.add("password", site.getPassword());
        loginParam.add("redirect", openCartRedirect);
        HttpEntity request = new HttpEntity(loginParam, httpHeaders);
        String body = restTemplate.postForEntity(url, request, String.class).getBody();
        log.info(body);


        return false;

    }

}
