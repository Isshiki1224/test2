package com.xm.commerce;



import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;


import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import static org.apache.http.entity.ContentType.MULTIPART_FORM_DATA;


@SpringBootTest
class CommerceApplicationTests {

    @Resource
    private RestTemplate restTemplate;

    @Test
    public void contextLoads() {
        //String url = "https://www.asmater.com/admin/index.php?route=catalog/product/add&user_token=9ju3Iw7pw30c6aPbEkM1taB8tuXR0pxX";
//        String url2 = "https://www.asmater.com/admin/index.php?route=common/login";
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
//        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(popHeaders(), headers);
//        //发送请求，设置请求返回数据格式为String
//        ResponseEntity<String> responseEntity = restTemplate.postForEntity(url2, request, String.class);
//        System.out.println(responseEntity.toString());

    }

    @Test
    public void test2() throws IOException {
        String url = "https://www.asmater.com/admin/index.php?route=common/login";

        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
        builder.addPart("username", new StringBody("admin", MULTIPART_FORM_DATA));
        builder.addPart("password", new StringBody("admin123", MULTIPART_FORM_DATA));
        builder.addPart("redirect", new StringBody("https://www.asmater.com/admin/index.php?route=common/login", MULTIPART_FORM_DATA));
        //MultipartEntity multipartEntity = new MultipartEntity();  deprecated过期
        HttpEntity entity = builder.build();
//        HttpRequestUtil.HttpsDefaultExecute(HttpRequestUtil.POST_METHOD, url, );

        HttpPost request = new HttpPost(url);
        request.setEntity(entity);
        request.addHeader("Content-Type", "Content-Disposition: form-data;");
//        new DefaultHttpClient();  deprecated过期
        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpResponse response = httpClient.execute(request);

        InputStream is = response.getEntity().getContent();
        BufferedReader in = new BufferedReader(new InputStreamReader(is));
        StringBuilder buffer = new StringBuilder();
        String line = "";
        while ((line = in.readLine()) != null){
            buffer.append(line);
        }
        System.out.println("返回消息：" + buffer.toString());

    }



    @Test
    public void test3() throws IOException {
//        String url = "https://www.asmater.com/admin/index.php?route=common/login";
//
//        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
//        builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
//        builder.addPart("username", new StringBody("admin", MULTIPART_FORM_DATA));
//        builder.addPart("password", new StringBody("admin123", MULTIPART_FORM_DATA));
//        builder.addPart("redirect", new StringBody("https://www.asmater.com/admin/index.php?route=common/login", MULTIPART_FORM_DATA));
//        //MultipartEntity multipartEntity = new MultipartEntity();  deprecated过期
//        HttpEntity entity = builder.build();
////        HttpRequestUtil.HttpsDefaultExecute(HttpRequestUtil.POST_METHOD, url, );
//
//        HttpPost request = new HttpPost(url);
//        request.setEntity(entity);
//        request.addHeader("Content-Type", "Content-Disposition: form-data");
////        new DefaultHttpClient();  deprecated过期
//        HttpClient httpClient = HttpClientBuilder.create().build();
//        HttpResponse response = httpClient.execute(request);
//
//        InputStream is = response.getEntity().getContent();
//        BufferedReader in = new BufferedReader(new InputStreamReader(is));
//        StringBuilder buffer = new StringBuilder();
//        String line = "";
//        while ((line = in.readLine()) != null){
//            buffer.append(line);
//        }
//        System.out.println("返回消息：" + buffer.toString());

    }







    protected MultiValueMap<String, String> popHeaders() {
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();

        map.add("username", "admin");
        map.add("password", "admin123");
        map.add("redirect", "https://www.asmater.com/admin/index.php?route=common/login");
        return map;
    }

    protected MultiValueMap<String, String> paramMap() {
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();

        map.add("username", "admin");
        map.add("password", "admin123");
        map.add("redirect", "https://www.asmater.com/admin/index.php?route=common/login");
        return map;
    }


}
