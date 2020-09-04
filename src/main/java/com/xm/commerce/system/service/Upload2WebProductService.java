package com.xm.commerce.system.service;


import com.baomidou.dynamic.datasource.annotation.DS;
import com.google.common.collect.ImmutableMap;
import com.xm.commerce.common.datasource.util.LoadDataSourceUtil;
import com.xm.commerce.common.exception.CurrentUserException;
import com.xm.commerce.common.exception.FileUploadException;
import com.xm.commerce.common.exception.ResourceNotFoundException;
import com.xm.commerce.common.exception.SiteNotFoundException;
import com.xm.commerce.security.util.CurrentUserUtils;
import com.xm.commerce.system.constant.RedisConstant;
import com.xm.commerce.system.mapper.ecommerce.CategorieMapper;
import com.xm.commerce.system.mapper.ecommerce.ProductStoreMapper;
import com.xm.commerce.system.mapper.ecommerce.SiteMapper;
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
import com.xm.commerce.system.model.dto.UploadTaskDto;
import com.xm.commerce.system.model.entity.ecommerce.EcommerceCategory;
import com.xm.commerce.system.model.entity.ecommerce.EcommerceProductStore;
import com.xm.commerce.system.model.entity.ecommerce.EcommerceSite;
import com.xm.commerce.system.model.entity.ecommerce.EcommerceUser;
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
import com.xm.commerce.system.model.request.UploadRequest;
import com.xm.commerce.system.model.request.UploadTaskRequest;
import com.xm.commerce.system.model.response.UploadTaskResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.time.DateUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

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
    CategorieMapper categorieMapper;
    @Resource
    RestTemplate restTemplate;
    @Resource
    LoadDataSourceUtil loadDataSourceUtil;
    @Resource
    Upload2WebProductService upload2WebProductService;
    @Resource
    RedisTemplate<String, Object> redisTemplate;
    @Resource
    CurrentUserUtils currentUserUtils;

    private static final String SHOPIFY_URL = "https://bestrylife.myshopify.com/admin/api/2020-07/products.json";
    private static final String SHOPIFY_TOKEN = "Basic ZDM1MWU0ZDkzNzY1ZmYzNDc1Y2I1MGVjYjM0MDIzYjU6c2hwcGFfYTUyMTdmYTU0YWQ0NWEwN2JhNjVkZWQxN2VhYWJmYzM=";
    private static final String OPEN_CART_REDIRECT = "https://www.asmater.com/admin/index.php?route=common/login";
    private static final String DIRECTORY_NOT_EXIST = "Warning: Directory does not exist!";

    public EcommerceUser getUser(){
        EcommerceUser currentUser = currentUserUtils.getCurrentUser();
        if (null == currentUser) {
            throw new CurrentUserException();
        }
        return currentUser;
    }

    public void BatchUpload2OpenCart(UploadTaskRequest request) throws Exception {

        EcommerceUser currentUser = getUser();
        List<EcommerceProductStore> productsList = productStoreMapper.selectByIds(request.getIds());
        EcommerceSite site = siteMapper.selectById(request.getSiteId());
        String taskId = currentUser.getId() + UUID.randomUUID().toString();
        UploadTaskResponse build = UploadTaskResponse.builder()
                .taskId(taskId)
                .taskTime(new Date())
                .uid(currentUser.getId())
                .taskStatus(0)
                .username(currentUser.getUsername())
                .siteName(site.getSiteName())
                .productStores(productsList)
                .build();

        List<UploadTaskDto> uploadTaskDto = getUploadTaskDto(build, site);


//        Map<String, String> taskMap = BeanUtils.describe(build);

//        redisTemplate.opsForHash().putAll(build.getTaskId(), taskMap);
        redisTemplate.opsForSet().add(String.valueOf(currentUser.getId()), RedisConstant.UPLOAD_TASK_PREFIX + taskId);
        redisTemplate.opsForValue().set(RedisConstant.UPLOAD_TASK_PREFIX + taskId, build);

    }


    private List<UploadTaskDto> getUploadTaskDto(UploadTaskResponse uploadTaskResponse, EcommerceSite site){
        List<EcommerceProductStore> productsList = uploadTaskResponse.getProductStores();
        List<UploadTaskDto> uploadTaskDtoList = new ArrayList<>();
        for (EcommerceProductStore productStore : productsList) {
            String id = UUID.randomUUID().toString();
            UploadTaskDto uploadTaskDto = UploadTaskDto.builder()
                    .id(id)
                    .site(site)
                    .taskId(uploadTaskResponse.getTaskId())
                    .taskStatus(uploadTaskResponse.getTaskStatus())
                    .uid(getUser().getId())
                    .username(currentUserUtils.getCurrentUsername())
                    .productStore(productStore)
                    .build();
            redisTemplate.opsForValue().set(RedisConstant.UPLOAD_TASK_SINGLE_PREFIX + id, uploadTaskDto);
            redisTemplate.opsForList().rightPush(RedisConstant.UPLOAD_TASK_LIST_KEY, id);
            uploadTaskDtoList.add(uploadTaskDto);

        }
        return uploadTaskDtoList;
    }




    @SuppressWarnings(value = {"rawtypes"})
    public boolean upload2OpenCart(EcommerceProductStore productStore, EcommerceUser currentUser, Integer siteId) {

        EcommerceSite site = siteMapper.selectById(siteId);
        Set<String> now = loadDataSourceUtil.now();
        for (String s : now) {
            if (!s.equals(site.getDbName())) {
                loadDataSourceUtil.add(site);
            }
        }
        log.info("当前连接的数据源" + loadDataSourceUtil.now().toString());
        String siteDbName = site.getDbName();
        // todo
//        Integer productId = upload2WebProductService.insertProduct(siteDbName);
        Integer productId = upload2WebProductService.insertProduct(productStore, siteDbName);
        upload2WebProductService.insertProductDescription(productStore, productId, siteDbName);
        upload2WebProductService.insertProductImage(productStore, productId, siteDbName);
        upload2WebProductService.insertCategorie(productStore, productId, siteDbName);

        String options = productStore.getProductOptions();
        JSONObject jsonObject = new JSONObject(options);
        Iterator keys = jsonObject.keys();
        while (keys.hasNext()) {
            String key = (String) keys.next();
            System.out.println("key= " + key);
//            JSONArray optionValueArray = new JSONArray(jsonObject.get(key));
            JSONArray optionValueArray = jsonObject.getJSONArray(key);
            upload2WebProductService.insertOptions(productStore, productId, key, optionValueArray, siteDbName);
        }
        productStoreMapper.updateByPrimaryKeySelective(EcommerceProductStore.builder()
                .id(productStore.getId())
                .uploadOpencartBy(currentUser.getId())
                .uploadOpencart(true)
                .build());
        log.info("入站成功");
        return true;
    }

    @DS("#siteDbName")
    public void insertOptions(EcommerceProductStore productStore, Integer productId, String key, JSONArray optionValueArray, String siteDbName) {
        List<OcOptionDescription> optionDescriptions = optionDescriptionMapper.selectByNameAndLanguageId(key, 1);
        if (null == optionDescriptions || optionDescriptions.isEmpty()) {
            OcOption option = OcOption.builder()
                    .type("select")
                    .sortOrder(0)
                    .build();
            optionMapper.insert(option);
            Integer optionId = option.getOptionId();
            OcOptionDescription optionDescription = OcOptionDescription.builder()
                    .optionId(optionId)
                    .languageId(1)
                    .name(key)
                    .build();
            optionDescriptionMapper.insert(optionDescription);
            productToOptionValue(productStore, productId, optionValueArray, optionId, siteDbName);
        } else {
            Integer optionId = optionDescriptions.get(0).getOptionId();
            productToOptionValue(productStore, productId, optionValueArray, optionId, siteDbName);
        }
    }

    @DS("#siteDbName")
    public void productToOptionValue(EcommerceProductStore EcommerceProductStore, Integer productId, JSONArray optionValueArray, Integer optionId, String siteDbName) {
        OcProductOption ProductOption = upload2WebProductService.insertProductOption(productId, optionId, siteDbName);
        Integer productOptionId = ProductOption.getProductOptionId();
        optionValueArray.forEach(optionValue -> {
            List<OcOptionValueDescription> optionValueDescriptions = optionValueDescriptionMapper.selectByNameAndLanguageId(String.valueOf(optionValue), 1);
            if (null == optionValueDescriptions || optionValueDescriptions.isEmpty()) {
                OcOptionValue OptionValue = upload2WebProductService.insertOptionValue(optionId, siteDbName);
                Integer optionValueId = OptionValue.getOptionValueId();
                upload2WebProductService.insertOptionValueDescription(optionId, (String) optionValue, optionValueId, siteDbName);
                upload2WebProductService.insertProductOptionValue(EcommerceProductStore, productId, optionId, productOptionId, optionValueId, siteDbName);
            } else {
                Integer optionValueId = optionValueDescriptions.get(0).getOptionValueId();
                upload2WebProductService.insertProductOptionValue(EcommerceProductStore, productId, optionId, productOptionId, optionValueId, siteDbName);
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


    public void insertCategorie(EcommerceProductStore productStore, Integer productId, String siteDbName) {
//        EcommerceCategorie categorie = categorieMapper.selectByName(productStore.getCategory());
//        if (categorie.getParentId() != null) {
//            EcommerceCategorie categorie1 = categorieMapper.selectByPrimaryKey(categorie.getParentId());
//            if (categorie1.getParentId() != null) {
//                EcommerceCategorie categorie2 = categorieMapper.selectByPrimaryKey(categorie1.getParentId());
//                if (categorie2.getParentId() == null) {
//                    insertCategory(categorie2.getName(), productId);
//                } else {
//                    throw new ResourceNotFoundException();
//                }
//            } else {
//                insertCategory(categorie1.getName(), productId);
//            }
//        } else {
//            insertCategory(categorie.getName(), productId);
//        }
        EcommerceCategory categorie = categorieMapper.selectByName(productStore.getCategory());

        if (categorie.getParentId() != null) {
            EcommerceCategory categorie1 = categorieMapper.selectById(categorie.getParentId());
            if (categorie1.getParentId() != null) {
                EcommerceCategory categorie2 = categorieMapper.selectById(categorie1.getParentId());
                if (categorie2.getParentId() == null) {
                    upload2WebProductService.insertCategory(categorie.getName(), productId, categorie.getParentId(), siteDbName);
                    upload2WebProductService.insertCategory(categorie1.getName(), productId, categorie1.getParentId(), siteDbName);
                    upload2WebProductService.insertCategory(categorie2.getName(), productId, 0, siteDbName);
                } else {
                    throw new ResourceNotFoundException();
                }
            } else {
                upload2WebProductService.insertCategory(categorie1.getName(), productId, 0, siteDbName);
                upload2WebProductService.insertCategory(categorie.getName(), productId, categorie.getParentId(), siteDbName);
            }
        } else {
            upload2WebProductService.insertCategory(categorie.getName(), productId, 0, siteDbName);
        }

    }

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

        //todo  改成IDurl 形式参数
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


    @Autowired
    DataSource dataSource;

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

    public boolean upload2Shopify(UploadRequest uploadRequest, EcommerceUser currentUser) throws Exception {

        Integer productId = uploadRequest.getProductId();
        Integer siteId = uploadRequest.getSiteId();
        EcommerceSite site = siteMapper.selectById(siteId);
        if (site.getApi() == null) {
            throw new SiteNotFoundException();
        }

        String authorization = site.getApiKey() + ":" + site.getApiPassword();
        Base64 base64 = new Base64();
        String base64Token = base64.encodeToString(authorization.getBytes(StandardCharsets.UTF_8));
        String token = "Basic " + base64Token;


        EcommerceProductStore productStore = productStoreMapper.selectById(productId);
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

//        List<Object> lists = new ArrayList<>();

        JSONArray lists = new JSONArray();
        options.forEach(map -> lists.put(map.get("values")));

        JSONArray variantsList = getVariantsList(lists);

        List<Object> result = Collections.singletonList(variantsList);

        List<Map<String, Object>> variants = new ArrayList<>();
        Object o = result.get(0);
        JSONArray array = (JSONArray) o;
        List<Map<String, Object>> finalVariants = variants;
        array.forEach(s -> {
            Map<String, Object> variant = new HashMap<>();
            if (s instanceof JSONArray) {
                JSONArray ss = (JSONArray) s;
                for (int i = 0; i < ss.length(); i++) {
                    variant.put("option" + (i + 1), ss.get(i));
                }
            } else {
                variant.put("option1", s);
            }
            variant.put("price", productStore.getPrice());
            variant.put("inventory_quantity", productStore.getQuantity());
            variant.put("sku", productStore.getSku());
            finalVariants.add(variant);
        });

        variants = variants.stream().distinct().collect(Collectors.toList());


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
        log.info("bbbbb===" + options.toString());
        log.info("aaaaaaa=====" + variants.toString());

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
//        httpHeaders.set("Authorization", SHOPIFY_TOKEN);
        httpHeaders.set("Authorization", token);
        HttpEntity<Map<String, Map<String, Object>>> request = new HttpEntity<>(productParam, httpHeaders);
        ResponseEntity<String> resp;

        try {
            resp = restTemplate.postForEntity(site.getApi(), request, String.class);
        } catch (Exception e) {
            log.info("cccccc===" + e.getMessage());
            throw new SiteNotFoundException(ImmutableMap.of(productStore.getProductName() + "商品入站站点信息错误", site.getApi()));
        }

        if (!resp.getStatusCode().equals(HttpStatus.CREATED)) {
            throw new FileUploadException();
        }
        productStoreMapper.updateById(EcommerceProductStore.builder()
                .id(productId)
                .uploadShopifyBy(currentUser.getId())
                .uploadShopify(true)
                .build());
        log.info(resp.toString());
        return true;
    }

    public JSONArray getVariantsList(JSONArray lists) {
        JSONArray result = new JSONArray();
        if (lists.length() == 0) {
            return null;
        } else if (lists.length() == 1) {
            return new JSONArray(lists.get(0).toString());
        } else {
            JSONArray options1 = (JSONArray) lists.get(0);
            JSONArray options2 = (JSONArray) lists.get(1);
            log.info(options1.toString());
            log.info(options2.toString());
            options1.forEach(option1 -> {
                options2.forEach(option2 -> {
                    JSONArray temp = new JSONArray();
                    if (option2 instanceof JSONArray) {
                        ((JSONArray) option2).forEach(temp::put);
                        temp.put(option1);
                    } else {
                        temp.put(option1);
                        temp.put(option2);
                    }
                    result.put(temp);
                });
            });
            lists.remove(0);
            lists.remove(0);
            lists.put(result);
            return getVariantsList(lists);
        }
    }


    public EcommerceProductStore uploadPic2OpenCart(UploadRequest uploadRequest, Map<String, Object> tokenAndCookies) throws IOException {

        Integer productId = uploadRequest.getProductId();
        Integer siteId = uploadRequest.getSiteId();
        EcommerceProductStore productStore = productStoreMapper.selectById(productId);
        if (productStore == null) {
            throw new ResourceNotFoundException();
        }
        EcommerceSite site = siteMapper.selectById(siteId);
        if (site == null) {
            throw new ResourceNotFoundException();
        }

        String domain = site.getDomain();
        String directory = site.getSiteName();
//        String url = domain + "?route=common/filemanager/upload&user_token=" + tokenAndCookies.get("token") + "&directory=" + directory;
        String url = domain + "?route=common/filemanager/upload&user_token=" + tokenAndCookies.get("token");
        String createDirectory = domain + "?route=common/filemanager/folder&user_token=" + tokenAndCookies.get("token") + "&directory=";
        String cookie = "";
        if (tokenAndCookies.get("Cookie") instanceof List<?>) {
            List<?> cookies = (List<?>) tokenAndCookies.get("Cookie");
            StringBuilder stringBuilder = new StringBuilder();
            cookies.forEach(o -> stringBuilder.append(o).append("; "));
            log.info(stringBuilder.toString());
            cookie = stringBuilder.toString().substring(0, stringBuilder.toString().lastIndexOf("; "));
            log.info(cookie);
        }
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Cookie", cookie);
        httpHeaders.set("content-type", "multipart/form-data; boundary=----WebKitFormBoundaryz3cn3BLSkyswVbLY");
        httpHeaders.set("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_4) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/84.0.4147.125 Safari/537.36");
        httpHeaders.set("accept", "application/json, text/javascript, */*; q=0.01");


        String image = productStore.getImage();
        StringBuilder sb = new StringBuilder();
//        String tempDir = "temp" + System.currentTimeMillis();
        FileSystemResource fileSystemResource = null;
        String[] split = image.split(",");
        Set<String> imgSet = new HashSet<>();
        for (String s : split) {
            fileSystemResource = imageUrl2FSR(s);
            MultiValueMap<String, Object> uploadParam = new LinkedMultiValueMap<>();
            uploadParam.add("file[]", fileSystemResource);
            HttpEntity request = new HttpEntity(uploadParam, httpHeaders);
            uploadAndCreated(url, request, productStore.getProductName());
//            sb.append("catalog/").append(s).append(",");
//            imgSet.add("catelog" + s.substring(s.lastIndexOf("/")));
            imgSet.add("catalog" + fileSystemResource.getPath().substring(fileSystemResource.getPath().lastIndexOf("/")));
            boolean result = fileSystemResource.getFile().delete();
            log.info("临时文件删除" + (result ? "成功" : "失败"));
        }
//        productStore.setImage(sb.toString().substring(0, sb.toString().lastIndexOf("/")));
        log.info("opencart图片路径: " + imgSet);
        productStore.setImage(String.join(",", imgSet));
        return productStore;
    }

    public void uploadAndCreated(String url, HttpEntity request, String name) throws IOException {
        String body = null;
        try {
            body = restTemplate.postForEntity(url, request, String.class).getBody();
        } catch (Exception e) {
            throw new SiteNotFoundException(ImmutableMap.of(name + "商品入站站点信息错误", ""));
        }
        if (body != null) {
            log.info(body);
            try {
                JSONObject jsonObject = new JSONObject(body);
                boolean success = jsonObject.has("success");
                if (!success) {
//                    if (DIRECTORY_NOT_EXIST.equals(jsonObject.get("error"))){
//                        log.info("directory不存在，尝试创建文件夹");
//                        MultiValueMap<String, Object> directoryParam = new LinkedMultiValueMap<>();
//                        directoryParam.add("folder", directory);
//                        HttpEntity<MultiValueMap<String, Object>> folderRequest = new HttpEntity<>(directoryParam, httpHeaders);
//                        String folderBody = restTemplate.postForEntity(createDirectory, folderRequest, String.class).getBody();
//                        JSONObject folderJson = new JSONObject(folderBody);
//                        boolean isCreated = folderJson.has("success");
//                        if (folderBody != null){
//                            if (!isCreated){
//                                log.info("文件夹创建失败");
//                                throw new CreateFolderException();
//                            }
//                            log.info("文件夹创建成功，尝试上传图片");
//                            uploadAndCreated(directory, url, createDirectory, httpHeaders, uploadParam, tempDir, s);
//                        }
//                    } else{
//                        log.info("图片上传失败");
//                        throw new FileUploadException();
//                    }
                    log.info("图片上传失败");
                    throw new FileUploadException();
                }
                log.info("图片上传成功");
            } catch (Exception e) {
                log.info("图片上传失败");
                throw new FileUploadException();
            }
        }
    }

    public void deleteFolder(File file) {
        File[] files = file.listFiles();
        if (files != null) {
            for (File temp : files) {
                if (file.isDirectory()) {
                    deleteFolder(temp);
                } else {
                    boolean delete = file.delete();
                    log.info("内部文件删除" + (delete ? "成功" : "失败"));
                }
            }
        }
        boolean delete = file.delete();
        log.info("临时目录{" + file.getPath() + "}删除" + (delete ? "成功" : "失败"));
    }

    public static FileSystemResource imageUrl2FSR(String path) throws IOException {
        HttpURLConnection httpUrl = (HttpURLConnection) new URL(path).openConnection();
        httpUrl.connect();
        File file = File.createTempFile(path.substring(path.lastIndexOf("/") + 1, path.lastIndexOf(".")), path.substring(path.lastIndexOf(".")));
        try (InputStream ins = httpUrl.getInputStream()) {
//            File file = new File(System.getProperty("java.io.tmpdir") + File.separator + tempDir + File.separator + path.substring(path.lastIndexOf("/")));
//            if (!file.getParentFile().exists()) {
//                boolean mkdir = file.getParentFile().mkdir();
//                log.info("临时文件目录{" + file.getPath() + "}创建" + (mkdir ? "成功" : "失败"));
//            }
            log.info("临时文件{" + file.getPath() + "}创建成功");
//            if (file.exists()) {
//                return new FileSystemResource(file);
//            }
            try (OutputStream os = new FileOutputStream(file)) {
                int bytesRead;
                int len = ins.available();
                byte[] buffer = new byte[len];
                while ((bytesRead = ins.read(buffer, 0, len)) != -1) {
                    os.write(buffer, 0, bytesRead);
                }
            }
            return new FileSystemResource(file);
        }
    }

    public Map<String, Object> login2OpenCart(UploadRequest uploadRequest) {

        Integer siteId = uploadRequest.getSiteId();
        EcommerceSite site = siteMapper.selectById(siteId);
        String domain = site.getDomain();
        String url = domain + "?route=common/login";
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("content-type", "multipart/form-data; boundary=----WebKitFormBoundaryz3cn3BLSkyswVbLY");
        httpHeaders.set("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9");
        httpHeaders.set("user-agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_4) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/84.0.4147.135 Safari/537.36");

        MultiValueMap<String, String> loginParam = new LinkedMultiValueMap<>();
        loginParam.add("username", site.getAccount());
        loginParam.add("password", site.getPassword());
        loginParam.add("redirect", OPEN_CART_REDIRECT);

        HttpEntity request = new HttpEntity(loginParam, httpHeaders);
        ResponseEntity<String> response = null;
        try {
            response = restTemplate.postForEntity(url, request, String.class);
        } catch (Exception e) {
            log.info(e.getMessage());
            throw new SiteNotFoundException(ImmutableMap.of("站点错误", ""));
        }
        URI location = response.getHeaders().getLocation();
        List<String> cookie = response.getHeaders().get("Set-Cookie");
        String token;
        HashMap<String, Object> result = new HashMap<>();
        if (location != null && cookie != null) {
            log.info("登陆成功");
            String path = location.toString();
            token = path.substring(path.lastIndexOf("user_token=") + "user_token=".length());
            log.info(token);
            log.info(cookie.toString());
            result.put("token", token);
            result.put("Cookie", cookie);
        } else {
            throw new CurrentUserException();
        }
        return result;
    }


    public Map<String, Object> login2OpenCart(EcommerceSite site) {


        String domain = site.getDomain();
        String url = domain + "?route=common/login";
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("content-type", "multipart/form-data; boundary=----WebKitFormBoundaryz3cn3BLSkyswVbLY");
        httpHeaders.set("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9");
        httpHeaders.set("user-agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_4) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/84.0.4147.135 Safari/537.36");

        MultiValueMap<String, String> loginParam = new LinkedMultiValueMap<>();
        loginParam.add("username", site.getAccount());
        loginParam.add("password", site.getPassword());
        loginParam.add("redirect", OPEN_CART_REDIRECT);

        HttpEntity request = new HttpEntity(loginParam, httpHeaders);
        ResponseEntity<String> response = null;
        try {
            response = restTemplate.postForEntity(url, request, String.class);
        } catch (Exception e) {
            log.info(e.getMessage());
            throw new SiteNotFoundException(ImmutableMap.of("站点错误", ""));
        }
        URI location = response.getHeaders().getLocation();
        List<String> cookie = response.getHeaders().get("Set-Cookie");
        String token;
        HashMap<String, Object> result = new HashMap<>();
        if (location != null && cookie != null) {
            log.info("登陆成功");
            String path = location.toString();
            token = path.substring(path.lastIndexOf("user_token=") + "user_token=".length());
            log.info(token);
            log.info(cookie.toString());
            result.put("token", token);
            result.put("Cookie", cookie);
        } else {
            throw new CurrentUserException();
        }
        return result;
    }

    public boolean upload2Shopify(EcommerceProductStore productStore, EcommerceSite site, Integer uid) throws Exception {

//        Integer productId = uploadRequest.getProductId();
//        Integer siteId = uploadRequest.getSiteId();
//        EcommerceSite site = siteMapper.selectById(siteId);
        if (site.getApi() == null) {
            throw new SiteNotFoundException();
        }

        String authorization = site.getApiKey() + ":" + site.getApiPassword();
        Base64 base64 = new Base64();
        String base64Token = base64.encodeToString(authorization.getBytes(StandardCharsets.UTF_8));
        String token = "Basic " + base64Token;
        log.info("authorization= " + token);


//        EcommerceProductStore productStore = productStoreMapper.selectById(productId);
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

//        List<Object> lists = new ArrayList<>();

        JSONArray lists = new JSONArray();
        options.forEach(map -> lists.put(map.get("values")));

        JSONArray variantsList = getVariantsList(lists);

        List<Object> result = Collections.singletonList(variantsList);

        List<Map<String, Object>> variants = new ArrayList<>();
        Object o = result.get(0);
        JSONArray array = (JSONArray) o;
        List<Map<String, Object>> finalVariants = variants;
        array.forEach(s -> {
            Map<String, Object> variant = new HashMap<>();
            if (s instanceof JSONArray) {
                JSONArray ss = (JSONArray) s;
                for (int i = 0; i < ss.length(); i++) {
                    variant.put("option" + (i + 1), ss.get(i));
                }
            } else {
                variant.put("option1", s);
            }
            variant.put("price", productStore.getPrice());
            variant.put("inventory_quantity", productStore.getQuantity());
            variant.put("sku", productStore.getSku());
            finalVariants.add(variant);
        });

        variants = variants.stream().distinct().collect(Collectors.toList());


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

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
//        httpHeaders.set("Authorization", SHOPIFY_TOKEN);
        httpHeaders.set("Authorization", token);
        HttpEntity<Map<String, Map<String, Object>>> request = new HttpEntity<>(productParam, httpHeaders);
        ResponseEntity<String> resp;

        try {
            resp = restTemplate.postForEntity(site.getApi(), request, String.class);
        } catch (Exception e) {
            log.info("resttemplate异常===" + e.getMessage());
            throw new SiteNotFoundException(ImmutableMap.of(productStore.getProductName() + "商品入站站点信息错误", site.getApi()));
        }

        if (!resp.getStatusCode().equals(HttpStatus.CREATED)) {
            throw new FileUploadException();
        }
        productStoreMapper.updateById(EcommerceProductStore.builder()
                .id(productStore.getId())
                .uploadShopifyBy(uid)
                .uploadShopify(true)
                .build());
        log.info("入站成功");
        return true;
    }

    public EcommerceProductStore uploadPic2OpenCart(EcommerceProductStore productStore, EcommerceSite site, Map<String, Object> tokenAndCookies) throws IOException {

//        Integer productId = uploadRequest.getProductId();
//        Integer siteId = uploadRequest.getSiteId();
//        EcommerceProductStore productStore = productStoreMapper.selectById(productId);
//        if (productStore == null) {
//            throw new ResourceNotFoundException();
//        }
//        EcommerceSite site = siteMapper.selectById(siteId);
//        if (site == null) {
//            throw new ResourceNotFoundException();
//        }

        String domain = site.getDomain();
        String directory = site.getSiteName();
//        String url = domain + "?route=common/filemanager/upload&user_token=" + tokenAndCookies.get("token") + "&directory=" + directory;
        String url = domain + "?route=common/filemanager/upload&user_token=" + tokenAndCookies.get("token");
        String createDirectory = domain + "?route=common/filemanager/folder&user_token=" + tokenAndCookies.get("token") + "&directory=";
        String cookie = "";
        if (tokenAndCookies.get("Cookie") instanceof List<?>) {
            List<?> cookies = (List<?>) tokenAndCookies.get("Cookie");
            StringBuilder stringBuilder = new StringBuilder();
            cookies.forEach(o -> stringBuilder.append(o).append("; "));
            log.info(stringBuilder.toString());
            cookie = stringBuilder.toString().substring(0, stringBuilder.toString().lastIndexOf("; "));
            log.info(cookie);
        }
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Cookie", cookie);
        httpHeaders.set("content-type", "multipart/form-data; boundary=----WebKitFormBoundaryz3cn3BLSkyswVbLY");
        httpHeaders.set("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_4) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/84.0.4147.125 Safari/537.36");
        httpHeaders.set("accept", "application/json, text/javascript, */*; q=0.01");


        String image = productStore.getImage();
        StringBuilder sb = new StringBuilder();
//        String tempDir = "temp" + System.currentTimeMillis();
        FileSystemResource fileSystemResource = null;
        String[] split = image.split(",");
        Set<String> imgSet = new HashSet<>();
        for (String s : split) {
            fileSystemResource = imageUrl2FSR(s);
            MultiValueMap<String, Object> uploadParam = new LinkedMultiValueMap<>();
            uploadParam.add("file[]", fileSystemResource);
            HttpEntity request = new HttpEntity(uploadParam, httpHeaders);
            uploadAndCreated(url, request, productStore.getProductName());
//            sb.append("catalog/").append(s).append(",");
//            imgSet.add("catelog" + s.substring(s.lastIndexOf("/")));
            imgSet.add("catalog" + fileSystemResource.getPath().substring(fileSystemResource.getPath().lastIndexOf("/")));
            boolean result = fileSystemResource.getFile().delete();
            log.info("临时文件删除" + (result ? "成功" : "失败"));
        }
//        productStore.setImage(sb.toString().substring(0, sb.toString().lastIndexOf("/")));
        log.info("opencart图片路径: " + imgSet);
        productStore.setImage(String.join(",", imgSet));
        return productStore;
    }

    public boolean upload2OpenCart(EcommerceProductStore productStore, Integer uid, EcommerceSite site) {

//        EcommerceSite site = siteMapper.selectById(siteId);
        Set<String> now = loadDataSourceUtil.now();
        for (String s : now) {
            if (!s.equals(site.getDbName())) {
                loadDataSourceUtil.add(site);
            }
        }
        log.info("当前连接的数据源" + loadDataSourceUtil.now().toString());
        String siteDbName = site.getDbName();
        // todo
//        Integer productId = upload2WebProductService.insertProduct(siteDbName);
        Integer productId = upload2WebProductService.insertProduct(productStore, siteDbName);
        upload2WebProductService.insertProductDescription(productStore, productId, siteDbName);
        upload2WebProductService.insertProductImage(productStore, productId, siteDbName);
        upload2WebProductService.insertCategorie(productStore, productId, siteDbName);

        String options = productStore.getProductOptions();
        JSONObject jsonObject = new JSONObject(options);
        Iterator keys = jsonObject.keys();
        while (keys.hasNext()) {
            String key = (String) keys.next();
            System.out.println("key= " + key);
//            JSONArray optionValueArray = new JSONArray(jsonObject.get(key));
            JSONArray optionValueArray = jsonObject.getJSONArray(key);
            upload2WebProductService.insertOptions(productStore, productId, key, optionValueArray, siteDbName);
        }
        productStoreMapper.updateByPrimaryKeySelective(EcommerceProductStore.builder()
                .id(productStore.getId())
                .uploadOpencartBy(uid)
                .uploadOpencart(true)
                .build());
        log.info("入站成功");
        return true;
    }

    public List<UploadTaskResponse> getUploadTask() {
        List<UploadTaskResponse> responses = new ArrayList<>();
        EcommerceUser user = getUser();
        Set<Object> members = redisTemplate.opsForSet().members(String.valueOf(user.getId()));
        if (members == null){
            return null;
        }
        for (Object member : members) {
            UploadTaskResponse uploadTaskResponse = (UploadTaskResponse)redisTemplate.opsForValue().get(member);
            responses.add(uploadTaskResponse);
        }
        return responses;
    }
}
