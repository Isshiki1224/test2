package com.xm.commerce;

import com.xm.commerce.system.mapper.ecommerce.SiteMapper;
import com.xm.commerce.system.model.entity.ecommerce.Site;
import org.apache.commons.codec.binary.Base64;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;

@SpringBootTest
public class LoginTest {

    @Resource
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Resource
    SiteMapper siteMapper;

    @Test
    public void test() throws UnsupportedEncodingException {
//		System.out.println(bCryptPasswordEncoder.encode("123456"));

//		String s = aaa();
//		byte[] bytes = Base64.decodeBase64(s);
//		System.out.println(new String(bytes));
//        createTempFile();

        String path = "https://images-na.ssl-images-amazon.com/images/I/718CpIkyv6L._AC_SL1500_.jpg";
        System.out.println(path.substring(path.lastIndexOf("/"))
        );
    }

    private void createTempFile() {
        File tempFile = null;
        try {
            tempFile = File.createTempFile("tempFile", ".jpg");
            System.out.println(tempFile.getAbsolutePath());
            tempFile.deleteOnExit();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void test2() {
        for (int i = 0; i < 5; i++) {
            System.out.println(i);
        }
        Thread th = new Thread(() -> System.out.println("jvm结束了。。。"));
        //设置监听线程
        Runtime.getRuntime().addShutdownHook(th);
    }


    private String aaa() throws UnsupportedEncodingException {
        Site site = siteMapper.selectByPrimaryKey(2);
        String authorization = site.getApiKey() + ":" + site.getApiPassword();
        Base64 base64 = new Base64();
        String base64Token = base64.encodeToString(authorization.getBytes("UTF-8"));
        String token = "Basic " + base64Token;
        String shopifyToken = "Basic ZDM1MWU0ZDkzNzY1ZmYzNDc1Y2I1MGVjYjM0MDIzYjU6c2hwcGFfYTUyMTdmYTU0YWQ0NWEwN2JhNjVkZWQxN2VhYWJmYzM=";
        System.out.println(token);
        System.out.println(shopifyToken.equals(token));
        return base64Token;
    }
}
