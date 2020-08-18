package com.xm.commerce;

import com.xm.commerce.system.util.HttpRequestUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.Iterator;

@SpringBootTest
public class Test3 {
    @Resource
    RestTemplate restTemplate;

    @Test
    @SuppressWarnings(value = "unchecked")
    void test3() {

        String a = "{\n" +
                "    \"color\": [\n" +
                "        \"blue\",\n" +
                "        \"black\"\n" +
                "    ],\n" +
                "    \"links\": [\n" +
                "        155,\n" +
                "        160,\n" +
                "        165\n" +
                "    ]\n" +
                "}";

        JSONObject jsonObject = JSONObject.fromObject(a);
        Iterator keys = jsonObject.keys();
        while (keys.hasNext()) {
            String key = (String) keys.next();
            System.out.println("key= " + key);
            JSONArray array = JSONArray.fromObject(jsonObject.get(key));
            array.forEach(o -> {
                System.out.println("value= " + o);
            });
            //System.out.println("value= " + array.toString());
        }
    }

}
