package com.xm.commerce.system.service;


import com.google.common.collect.ImmutableMap;
import com.xm.commerce.common.exception.*;
import com.xm.commerce.system.mapper.ecommerce.CategorieMapper;
import com.xm.commerce.system.mapper.ecommerce.ProductStoreMapper;
import com.xm.commerce.system.mapper.ecommerce.SiteMapper;
import com.xm.commerce.system.mapper.umino.*;
import com.xm.commerce.system.model.entity.ecommerce.Categorie;
import com.xm.commerce.system.model.entity.ecommerce.ProductStore;
import com.xm.commerce.system.model.entity.ecommerce.Site;
import com.xm.commerce.system.model.entity.ecommerce.User;
import com.xm.commerce.system.model.entity.umino.*;
import com.xm.commerce.system.model.request.UploadRequest;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.io.*;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
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
    CategorieMapper categorieMapper;
    @Resource
    RestTemplate restTemplate;

    private static final String SHOPIFY_URL = "https://bestrylife.myshopify.com/admin/api/2020-07/products.json";
    private static final String SHOPIFY_TOKEN = "Basic ZDM1MWU0ZDkzNzY1ZmYzNDc1Y2I1MGVjYjM0MDIzYjU6c2hwcGFfYTUyMTdmYTU0YWQ0NWEwN2JhNjVkZWQxN2VhYWJmYzM=";
    private static final String OPEN_CART_REDIRECT = "https://www.asmater.com/admin/index.php?route=common/login";
    private static final String DIRECTORY_NOT_EXIST = "Warning: Directory does not exist!";

    @SuppressWarnings(value = {"rawtypes"})
    @Transactional(rollbackFor = Exception.class)
    public boolean upload2OpenCart(ProductStore productStore, User currentUser, Integer siteId) {

        // todo

        Integer productId = insertProduct(productStore);
        insertProductDescription(productStore, productId);
        insertProductImage(productStore, productId);
        insertCategorie(productStore, productId);

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
        productStoreMapper.updateByPrimaryKeySelective(ProductStore.builder()
                .id(productId)
                .uploadOpencartBy(currentUser.getId())
                .uploadOpencart(true)
                .build());

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

    private void insertCategorie(ProductStore productStore, Integer productId) {
        Categorie categorie = categorieMapper.selectByName(productStore.getCategory());
        if (categorie.getParentId() != null) {
            Categorie categorie1 = categorieMapper.selectByPrimaryKey(categorie.getParentId());
            if (categorie1.getParentId() != null) {
                Categorie categorie2 = categorieMapper.selectByPrimaryKey(categorie1.getParentId());
                if (categorie2.getParentId() == null) {
                    insertCategory(categorie2.getName(), productId);
                } else {
                    throw new ResourceNotFoundException();
                }
            } else {
                insertCategory(categorie1.getName(), productId);
            }
        } else {
            insertCategory(categorie.getName(), productId);
        }

    }

    private void insertCategory(String name, Integer productId) {
        CategoryDescription categoryDescription = categoryDescriptionMapper.selectByNameAndLanguageId(name, 1);
        if (null == categoryDescription) {
            Category category = Category.builder()
                    .top(true)
                    .parentId(0)
                    .column(1)
                    .sortOrder(0)
                    .dateAdded(new Date())
                    .dateModified(new Date())
                    .status(true)
                    .build();
            categoryMapper.insertSelective(category);

            Integer categoryId = category.getCategoryId();
            productToCategoryMapper.insertSelective(new ProductToCategory(productId, categoryId));

            categoryDescription = CategoryDescription.builder()
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
            productToCategoryMapper.insertSelective(new ProductToCategory(productId, categoryDescription.getCategoryId()));
        }
    }

    private void insertProductImage(ProductStore ProductStore, Integer productId) {

        //todo  改成IDurl 形式参数
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

    public boolean upload2Shopify(UploadRequest uploadRequest, User currentUser) throws Exception {

        Integer productId = uploadRequest.getProductId();
        Integer siteId = uploadRequest.getSiteId();
        Site site = siteMapper.selectByPrimaryKey(siteId);
        if (site.getApi() == null) {
            throw new SiteNotFoundException();
        }

//        String authorization = site.getApiKey() + ":" + site.getApiPassword();
//        Base64 base64 = new Base64();
//        String base64Token = base64.encodeToString(authorization.getBytes("UTF-8"));
//        String token = "Basic " + base64Token;


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

//        List<Object> lists = new ArrayList<>();

        JSONArray lists = new JSONArray();
        options.forEach(map -> lists.put(map.get("values")));

        JSONArray variantsList = getVariantsList(lists);

        List<Object> result = Collections.singletonList(variantsList);
        log.info("adlfasjsdlfkjs===" + result.toString());
        List<Map<String, Object>> variants = new ArrayList<>();
        Object o = result.get(0);
        JSONArray array = (JSONArray) o;
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
            variants.add(variant);
        });


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
        httpHeaders.set("Authorization", SHOPIFY_TOKEN);
        HttpEntity<Map<String, Map<String, Object>>> request = new HttpEntity<>(productParam, httpHeaders);
        ResponseEntity<String> resp;

        try {
            resp = restTemplate.postForEntity(site.getApi(), request, String.class);
        } catch (Exception e) {
            throw new SiteNotFoundException(ImmutableMap.of(productStore.getProductName() + "商品入站站点信息错误", site.getApi()));
        }

        if (!resp.getStatusCode().equals(HttpStatus.CREATED)) {
            throw new FileUploadException();
        }
        productStoreMapper.updateByPrimaryKeySelective(ProductStore.builder()
                .id(productId)
                .uploadShopifyBy(currentUser.getId())
                .uploadShopify(true)
                .build());
        log.info(resp.toString());
        return true;
    }

    private JSONArray getVariantsList(JSONArray lists) {
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


    public ProductStore uploadPic2OpenCart(UploadRequest uploadRequest, Map<String, Object> tokenAndCookies) throws IOException {

        Integer productId = uploadRequest.getProductId();
        Integer siteId = uploadRequest.getSiteId();
        ProductStore productStore = productStoreMapper.selectByPrimaryKey(productId);
        Site site = siteMapper.selectByPrimaryKey(siteId);
        String domain = site.getDomain();
        String directory = site.getSiteName();
        String url = domain + "?route=common/filemanager/upload&user_token=" + tokenAndCookies.get("token") + "&directory=" + directory;
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

        for (String s : image.split(",")) {
            fileSystemResource = imageUrl2FSR(s);
            MultiValueMap<String, Object> uploadParam = new LinkedMultiValueMap<>();
            uploadParam.add("file[]", fileSystemResource);
            HttpEntity request = new HttpEntity(uploadParam, httpHeaders);
            uploadAndCreated(url, request);
            sb.append("catalog/").append(s).append(",");
            boolean result = fileSystemResource.getFile().delete();
            log.info("临时文件删除" + (result ? "成功" : "失败"));
        }
        productStore.setImage(sb.toString().substring(0, sb.toString().lastIndexOf(",")));

        return productStore;
    }

    private void uploadAndCreated(String url, HttpEntity request) throws IOException {
        String body = restTemplate.postForEntity(url, request, String.class).getBody();
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

    private void deleteFolder(File file) {
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
            if (file.exists()) {
                return new FileSystemResource(file);
            }
            try (OutputStream os = new FileOutputStream(file)) {
                int bytesRead;
                int len = 8192;
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
        Site site = siteMapper.selectByPrimaryKey(siteId);
        String domain = site.getDomain();
        String url = domain + "?route=common/login";
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("content-type", "multipart/form-data; boundary=----WebKitFormBoundaryz3cn3BLSkyswVbLY");
        httpHeaders.set("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_4) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/84.0.4147.125 Safari/537.36");
        httpHeaders.set("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9");

        MultiValueMap<String, String> loginParam = new LinkedMultiValueMap<>();
        loginParam.add("username", site.getAccount());
        loginParam.add("password", site.getPassword());
        loginParam.add("redirect", OPEN_CART_REDIRECT);

        HttpEntity request = new HttpEntity(loginParam, httpHeaders);
        ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);
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

    public void checkUploaded(UploadRequest uploadRequest) {
        Integer productId = uploadRequest.getProductId();
        ProductStore productStore = productStoreMapper.selectByPrimaryKey(productId);
        Site site = siteMapper.selectByPrimaryKey(uploadRequest.getSiteId());
        if ((productStore.getUploadOpencart() && site.getSiteCategory()) || (productStore.getUploadShopify() && !site.getSiteCategory())) {
            throw new ProductAlreadyUploadException();
        }
    }
}
