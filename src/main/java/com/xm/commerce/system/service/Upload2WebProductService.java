package com.xm.commerce.system.service;


import com.google.common.collect.ImmutableMap;
import com.xm.commerce.common.datasource.util.LoadDataSourceUtil;
import com.xm.commerce.common.exception.CurrentUserException;
import com.xm.commerce.common.exception.FileUploadException;
import com.xm.commerce.common.exception.ProductSkuAlreadyExistException;
import com.xm.commerce.common.exception.ResourceNotFoundException;
import com.xm.commerce.common.exception.SiteMessageException;
import com.xm.commerce.common.exception.SiteNotFoundException;
import com.xm.commerce.security.util.CurrentUserUtils;
import com.xm.commerce.system.constant.RedisConstant;
import com.xm.commerce.system.mapper.ecommerce.CategorieMapper;
import com.xm.commerce.system.mapper.ecommerce.ProductStoreMapper;
import com.xm.commerce.system.mapper.ecommerce.SiteMapper;
import com.xm.commerce.system.model.dto.OpenCartAuthDto;
import com.xm.commerce.system.model.dto.UploadTaskDto;
import com.xm.commerce.system.model.entity.ecommerce.EcommerceCategory;
import com.xm.commerce.system.model.entity.ecommerce.EcommerceProductStore;
import com.xm.commerce.system.model.entity.ecommerce.EcommerceSite;
import com.xm.commerce.system.model.entity.ecommerce.EcommerceUser;
import com.xm.commerce.system.model.request.UploadRequest;
import com.xm.commerce.system.model.request.UploadTaskRequest;
import com.xm.commerce.system.model.response.UploadTaskResponse;
import com.xm.commerce.system.task.UploadTaskWebSocket;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.json.JSONArray;
import org.json.JSONObject;
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
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
	ProductDynamicDbService productDynamicDbService;
	@Resource
	RedisTemplate<String, Object> redisTemplate;
	@Resource
	CurrentUserUtils currentUserUtils;
	@Resource
	UploadTaskWebSocket uploadTaskWebSocket;

	private static final String SHOPIFY_URL = "https://bestrylife.myshopify.com/admin/api/2020-07/products.json";
	private static final String SHOPIFY_TOKEN = "Basic ZDM1MWU0ZDkzNzY1ZmYzNDc1Y2I1MGVjYjM0MDIzYjU6c2hwcGFfYTUyMTdmYTU0YWQ0NWEwN2JhNjVkZWQxN2VhYWJmYzM=";
	private static final String OPEN_CART_REDIRECT = "https://www.asmater.com/admin/index.php?route=common/login";
	private static final String DIRECTORY_NOT_EXIST = "Warning: Directory does not exist!";

	public EcommerceUser getUser() {
		EcommerceUser currentUser = currentUserUtils.getCurrentUser();
		if (null == currentUser) {
			throw new CurrentUserException();
		}
		return currentUser;
	}

	public void BatchUpload2OpenCart(UploadTaskRequest request) throws Exception {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String format = sdf.format(new Date());

		EcommerceUser currentUser = getUser();
		List<EcommerceProductStore> productsList = productStoreMapper.selectByIds(request.getIds());
		EcommerceSite site = siteMapper.selectById(request.getSiteId());
		String taskId = currentUser.getId() + UUID.randomUUID().toString();
		UploadTaskResponse build = UploadTaskResponse.builder()
				.taskId(taskId)
				.taskTime(format)
				.uid(currentUser.getId())
				.taskStatus(0)
				.username(currentUser.getUsername())
				.siteName(site.getSiteName())
				.build();

		List<UploadTaskDto> uploadTaskDtos = getUploadTaskDto(build, site, productsList);
		build.setProductStores(uploadTaskDtos);


//        Map<String, String> taskMap = BeanUtils.describe(build);

//        redisTemplate.opsForHash().putAll(build.getTaskId(), taskMap);
		redisTemplate.opsForSet().add(RedisConstant.UPLOAD_TASK_BY_USER + currentUser.getId(), RedisConstant.UPLOAD_TASK_PREFIX + taskId);
		redisTemplate.opsForValue().set(RedisConstant.UPLOAD_TASK_PREFIX + taskId, build);

	}

//    public void productToOptionValue(EcommerceProductStore EcommerceProductStore, Integer productId, JSONArray optionValueArray, Integer optionId, String siteDbName) {
//        OcProductOption ProductOption = productDynamicDbService.insertProductOption(productId, optionId, siteDbName);
//        Integer productOptionId = ProductOption.getProductOptionId();
//        optionValueArray.forEach(optionValue -> {
//            List<OcOptionValueDescription> optionValueDescriptions = productDynamicDbService.optionValueDescriptionMapper.selectByNameAndLanguageId(String.valueOf(optionValue), 1);
//            if (null == optionValueDescriptions || optionValueDescriptions.isEmpty()) {
//                OcOptionValue OptionValue = productDynamicDbService.insertOptionValue(optionId, siteDbName);
//                Integer optionValueId = OptionValue.getOptionValueId();
//                productDynamicDbService.insertOptionValueDescription(optionId, (String) optionValue, optionValueId, siteDbName);
//                productDynamicDbService.insertProductOptionValue(EcommerceProductStore, productId, optionId, productOptionId, optionValueId, siteDbName);
//            } else {
//                Integer optionValueId = optionValueDescriptions.get(0).getOptionValueId();
//                productDynamicDbService.insertProductOptionValue(EcommerceProductStore, productId, optionId, productOptionId, optionValueId, siteDbName);
//            }
//        });
//    }

	private List<UploadTaskDto> getUploadTaskDto(UploadTaskResponse uploadTaskResponse, EcommerceSite site, List<EcommerceProductStore> productsList) {
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
//    public boolean upload2OpenCart(EcommerceProductStore productStore, EcommerceUser currentUser, Integer siteId) {
//
//        EcommerceSite site = siteMapper.selectById(siteId);
//        Set<String> now = loadDataSourceUtil.now();
//        for (String s : now) {
//            if (!s.equals(site.getDbName())) {
//                loadDataSourceUtil.add(site);
//            }
//        }
//        log.info("当前连接的数据源" + loadDataSourceUtil.now().toString());
//        String siteDbName = site.getDbName();
//        // todo
////        Integer productId = upload2WebProductService.insertProduct(siteDbName);
//        Integer productId = productDynamicDbService.insertProduct(productStore, siteDbName);
//        productDynamicDbService.insertProductDescription(productStore, productId, siteDbName);
//        productDynamicDbService.insertProductImage(productStore, productId, siteDbName);
//        insertCategorie(productStore, productId, siteDbName);
//
//        String options = productStore.getProductOptions();
//        JSONObject jsonObject = new JSONObject(options);
//        Iterator keys = jsonObject.keys();
//        while (keys.hasNext()) {
//            String key = (String) keys.next();
//            System.out.println("key= " + key);
////            JSONArray optionValueArray = new JSONArray(jsonObject.get(key));
//            JSONArray optionValueArray = jsonObject.getJSONArray(key);
//            productDynamicDbService.insertOptions(productStore, productId, key, optionValueArray, siteDbName);
//        }
//        productStoreMapper.updateByPrimaryKeySelective(EcommerceProductStore.builder()
//                .id(productStore.getId())
//                .uploadOpencartBy(currentUser.getId())
//                .uploadOpencart(true)
//                .build());
//        log.info("入站成功");
//        return true;
//    }

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
					productDynamicDbService.insertCategory(categorie.getName(), productId, categorie.getParentId(), siteDbName);
					productDynamicDbService.insertCategory(categorie1.getName(), productId, categorie1.getParentId(), siteDbName);
					productDynamicDbService.insertCategory(categorie2.getName(), productId, 0, siteDbName);
				} else {
					throw new ResourceNotFoundException();
				}
			} else {
				productDynamicDbService.insertCategory(categorie1.getName(), productId, 0, siteDbName);
				productDynamicDbService.insertCategory(categorie.getName(), productId, categorie.getParentId(), siteDbName);
			}
		} else {
			productDynamicDbService.insertCategory(categorie.getName(), productId, 0, siteDbName);
		}

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
//    public EcommerceProductStore uploadPic2OpenCart(UploadRequest uploadRequest, Map<String, Object> tokenAndCookies) throws Exception {
//
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
//
//        String domain = site.getDomain();
//        String directory = site.getSiteName();
////        String url = domain + "?route=common/filemanager/upload&user_token=" + tokenAndCookies.get("token") + "&directory=" + directory;
//        String url = domain + "?route=common/filemanager/upload&user_token=" + tokenAndCookies.get("token");
//        String createDirectory = domain + "?route=common/filemanager/folder&user_token=" + tokenAndCookies.get("token") + "&directory=";
//        String cookie = "";
//        if (tokenAndCookies.get("Cookie") instanceof List<?>) {
//            List<?> cookies = (List<?>) tokenAndCookies.get("Cookie");
//            StringBuilder stringBuilder = new StringBuilder();
//            cookies.forEach(o -> stringBuilder.append(o).append("; "));
//            log.info(stringBuilder.toString());
//            cookie = stringBuilder.toString().substring(0, stringBuilder.toString().lastIndexOf("; "));
//            log.info(cookie);
//        }
//        HttpHeaders httpHeaders = new HttpHeaders();
//        httpHeaders.set("Cookie", cookie);
//        httpHeaders.set("content-type", "multipart/form-data; boundary=----WebKitFormBoundaryz3cn3BLSkyswVbLY");
//        httpHeaders.set("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_4) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/84.0.4147.125 Safari/537.36");
//        httpHeaders.set("accept", "application/json, text/javascript, */*; q=0.01");
//
//
//        String image = productStore.getImage();
//        StringBuilder sb = new StringBuilder();
////        String tempDir = "temp" + System.currentTimeMillis();
//        FileSystemResource fileSystemResource = null;
//        String[] split = image.split(",");
//        Set<String> imgSet = new HashSet<>();
//        for (String s : split) {
//            fileSystemResource = imageUrl2FSR(s);
//            MultiValueMap<String, Object> uploadParam = new LinkedMultiValueMap<>();
//            uploadParam.add("file[]", fileSystemResource);
//            HttpEntity request = new HttpEntity(uploadParam, httpHeaders);
//            uploadAndCreated(url, request, productStore.getProductName());
////            sb.append("catalog/").append(s).append(",");
////            imgSet.add("catelog" + s.substring(s.lastIndexOf("/")));
//            imgSet.add("catalog" + fileSystemResource.getPath().substring(fileSystemResource.getPath().lastIndexOf("/")));
//            boolean result = fileSystemResource.getFile().delete();
//            log.info("临时文件删除" + (result ? "成功" : "失败"));
//        }
////        productStore.setImage(sb.toString().substring(0, sb.toString().lastIndexOf("/")));
//        log.info("opencart图片路径: " + imgSet);
//        productStore.setImage(String.join(",", imgSet));
//        return productStore;
//    }

	public void uploadAndCreated(String url, HttpEntity request, String singleKey) throws Exception {
		String body;
		try {
			body = restTemplate.postForEntity(url, request, String.class).getBody();
		} catch (Exception e) {
			UploadTaskDto uploadTaskDto = (UploadTaskDto) redisTemplate.opsForValue().get(singleKey);
			if (uploadTaskDto == null) {
				log.info("uploadTaskDto not exist");
				throw new ResourceNotFoundException();
			}
			uploadTaskDto.setErrorMessage("站点出错");
			uploadTaskDto.setTaskStatus(3);
			redisTemplate.opsForValue().set(singleKey, uploadTaskDto);
			uploadTaskWebSocket.sendMessage(uploadTaskDto, uploadTaskDto.getUsername());
			throw new SiteNotFoundException(ImmutableMap.of("站点信息错误", ""));
		}
		log.info("body::::" + body);
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
				throw new SiteNotFoundException(ImmutableMap.of("商品图片上传失败", request));
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

	public static FileSystemResource imageUrl2FSR(String url) throws IOException {
		URL imageUrl = new URL(url);
		File file = File.createTempFile(url.substring(url.lastIndexOf("/") + 1, url.lastIndexOf(".")), url.substring(url.lastIndexOf(".")));
		FileUtils.copyURLToFile(imageUrl, file);
		return new FileSystemResource(file);
	}

//    public Map<String, Object> login2OpenCart(UploadRequest uploadRequest) {
//
//        Integer siteId = uploadRequest.getSiteId();
//        EcommerceSite site = siteMapper.selectById(siteId);
//        String domain = site.getDomain();
//        String url = domain + "?route=common/login";
//        HttpHeaders httpHeaders = new HttpHeaders();
//        httpHeaders.set("content-type", "multipart/form-data; boundary=----WebKitFormBoundaryz3cn3BLSkyswVbLY");
//        httpHeaders.set("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9");
//        httpHeaders.set("user-agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_4) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/84.0.4147.135 Safari/537.36");
//
//        MultiValueMap<String, String> loginParam = new LinkedMultiValueMap<>();
//        loginParam.add("username", site.getAccount());
//        loginParam.add("password", site.getPassword());
//        loginParam.add("redirect", OPEN_CART_REDIRECT);
//
//        HttpEntity request = new HttpEntity(loginParam, httpHeaders);
//        ResponseEntity<String> response = null;
//        try {
//            response = restTemplate.postForEntity(url, request, String.class);
//        } catch (Exception e) {
//            log.info(e.getMessage());
//            throw new SiteNotFoundException(ImmutableMap.of("站点错误", ""));
//        }
//        URI location = response.getHeaders().getLocation();
//        List<String> cookie = response.getHeaders().get("Set-Cookie");
//        String token;
//        HashMap<String, Object> result = new HashMap<>();
//        if (location != null && cookie != null) {
//            log.info("登陆成功");
//            String path = location.toString();
//            token = path.substring(path.lastIndexOf("user_token=") + "user_token=".length());
//            log.info(token);
//            log.info(cookie.toString());
//            result.put("token", token);
//            result.put("Cookie", cookie);
//        } else {
//            throw new CurrentUserException();
//        }
//        return result;
//    }

	public OpenCartAuthDto login2OpenCart2(EcommerceSite site, String singleKey) {
		try {
			String domain = site.getDomain();
			String url = domain + "?route=common/login";
			HttpHeaders httpHeaders = new HttpHeaders();
			httpHeaders.set("content-type", "multipart/form-data; boundary=----WebKitFormBoundaryz3cn3BLSkyswVbLY");
			httpHeaders.set("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9");
			httpHeaders.set("user-agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_4) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/84.0.4147.135 Safari/537.36");

			MultiValueMap<String, String> loginParam = new LinkedMultiValueMap<>();
			loginParam.add("username", site.getAccount());
			loginParam.add("password", site.getPassword());
			loginParam.add("redirect", url);

			HttpEntity request = new HttpEntity(loginParam, httpHeaders);
			ResponseEntity<String> response = null;
			response = restTemplate.postForEntity(url, request, String.class);
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
				throw new SiteNotFoundException();
			}
			return new OpenCartAuthDto(token, cookie);
		} catch (Exception e) {
			log.info(e.getMessage());
			UploadTaskDto uploadTaskDto = (UploadTaskDto) redisTemplate.opsForValue().get(singleKey);
			if (uploadTaskDto == null) {
				log.info("uploadTaskDto not exist");
				throw new ResourceNotFoundException();
			}
			uploadTaskDto.setErrorMessage(e.getMessage());
			uploadTaskDto.setTaskStatus(3);
			redisTemplate.opsForValue().set(singleKey, uploadTaskDto);
			try {
				uploadTaskWebSocket.sendMessage(uploadTaskDto, uploadTaskDto.getUsername());
			} catch (Exception exception) {
				log.info("websocket异常，{}", exception.getMessage());
				exception.printStackTrace();
			}
			throw new RuntimeException();
		}
	}

	public boolean upload2Shopify2(EcommerceProductStore productStore, EcommerceSite site, Integer uid, String singleKey) throws Exception {

		if (site.getApi() == null) {
			throw new SiteNotFoundException();
		}

		List<Map<String, Object>> variants = new ArrayList<>();
		List<Map<String, Object>> options = new ArrayList<>();
		String authorization = site.getApiKey() + ":" + site.getApiPassword();
		Base64 base64 = new Base64();
		String base64Token = base64.encodeToString(authorization.getBytes(StandardCharsets.UTF_8));
		String token = "Basic " + base64Token;
		log.info("authorization= " + token);

		String productOptions = productStore.getProductOptions();
		if (productOptions.equals("{}")) {
			variants = null;
			options = null;
		} else {
			JSONObject jsonObject = new JSONObject(productOptions);
			Iterator<String> keys = jsonObject.keys();
			while (keys.hasNext()) {
				String name = keys.next();
				Object optionsValue = jsonObject.get(name);
				Map<String, Object> option = new HashMap<>();
				option.put("name", name);
				option.put("values", optionsValue);
				options.add(option);
			}

			JSONArray lists = new JSONArray();
			options.forEach(map -> lists.put(map.get("values")));

			JSONArray variantsList = getVariantsList(lists);

			List<Object> result = Collections.singletonList(variantsList);

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
			UploadTaskDto uploadTaskDto = (UploadTaskDto) redisTemplate.opsForValue().get(singleKey);
			if (uploadTaskDto == null) {
				log.info("uploadTaskDto not exist");
				throw new ResourceNotFoundException();
			}
			uploadTaskDto.setErrorMessage("站点出错");
			uploadTaskDto.setTaskStatus(3);
			redisTemplate.opsForValue().set(singleKey, uploadTaskDto);
			uploadTaskWebSocket.sendMessage(uploadTaskDto, uploadTaskDto.getUsername());
			throw new SiteNotFoundException(ImmutableMap.of("站点出错", site));
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

	public EcommerceProductStore uploadPic2OpenCart2(EcommerceProductStore productStore, EcommerceSite site, OpenCartAuthDto openCartAuthDto, String singleKey) throws Exception {
		String domain = site.getDomain();
		String directory = site.getSiteName();
//        String url = domain + "?route=common/filemanager/upload&user_token=" + openCartAuthDto.get("token") + "&directory=" + directory;
		String url = domain + "?route=common/filemanager/upload&user_token=" + openCartAuthDto.getToken();
		String createDirectory = domain + "?route=common/filemanager/folder&user_token=" + openCartAuthDto.getToken() + "&directory=";
		String cookie = "";
		if (openCartAuthDto.getCookie() != null) {
			List<String> cookies = openCartAuthDto.getCookie();
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
		FileSystemResource fileSystemResource;
		String[] split = image.split(",");
		Set<String> imgSet = new HashSet<>();
		for (String s : split) {
			fileSystemResource = imageUrl2FSR(s);
			MultiValueMap<String, Object> uploadParam = new LinkedMultiValueMap<>();
			uploadParam.add("file[]", fileSystemResource);
			HttpEntity request = new HttpEntity(uploadParam, httpHeaders);
			uploadAndCreated(url, request, singleKey);
			imgSet.add("catalog" + fileSystemResource.getPath().substring(fileSystemResource.getPath().lastIndexOf("/")));
			boolean result = fileSystemResource.getFile().delete();
		}
		productStore.setImage(String.join(",", imgSet));
		return productStore;
	}

	public boolean upload2OpenCart2(EcommerceProductStore productStore, Integer uid, EcommerceSite site) throws SiteMessageException {

		Set<String> now = loadDataSourceUtil.now();
		for (String s : now) {
			if (!s.equals(site.getDbName())) {
				loadDataSourceUtil.add(site);
			}
		}
		log.info("当前连接的数据源" + loadDataSourceUtil.now().toString());
		String siteDbName = site.getDbName();

		// look up there is same sku
		boolean skuNotExist = productDynamicDbService.isSkuNotExist(productStore.getSku(), siteDbName);
		if (!skuNotExist) {
			throw new ProductSkuAlreadyExistException(ImmutableMap.of("sku", productStore.getSku()));
		}

		Integer productId = productDynamicDbService.insertProduct(productStore, siteDbName);
		productDynamicDbService.insertProductDescription(productStore, productId, siteDbName);
		productDynamicDbService.insertProductImage(productStore, productId, siteDbName);
		insertCategorie(productStore, productId, siteDbName);

		String options = productStore.getProductOptions();
		JSONObject jsonObject = new JSONObject(options);
		Iterator keys = jsonObject.keys();
		while (keys.hasNext()) {
			String key = (String) keys.next();
			System.out.println("key= " + key);
//            JSONArray optionValueArray = new JSONArray(jsonObject.get(key));
			JSONArray optionValueArray = jsonObject.getJSONArray(key);
			productDynamicDbService.insertOptions(productStore, productId, key, optionValueArray, siteDbName);
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
		Set<Object> members = redisTemplate.opsForSet().members(RedisConstant.UPLOAD_TASK_BY_USER + user.getId());
		if (members == null) {
			return null;
		}
		for (Object member : members) {
			UploadTaskResponse uploadTaskResponse = (UploadTaskResponse) redisTemplate.opsForValue().get(member);
			if (uploadTaskResponse == null) {
				throw new RuntimeException();
			}
			List<UploadTaskDto> productStores = uploadTaskResponse.getProductStores();
			for (int i = 0; i < productStores.size(); i++) {
				UploadTaskDto uploadTaskDto = (UploadTaskDto) redisTemplate.opsForValue().get(RedisConstant.UPLOAD_TASK_SINGLE_PREFIX + productStores.get(i).getId());
				productStores.set(i, uploadTaskDto);
			}
			uploadTaskResponse.setProductStores(productStores);
			redisTemplate.opsForValue().set(String.valueOf(member), uploadTaskResponse);
			responses.add(uploadTaskResponse);
		}

		return responses;
	}
}
