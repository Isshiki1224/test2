package com.xm.commerce.opencart;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;

@SpringBootTest

public class LoginTest {

//    @Resource
//    ProductStoreService productStoreService;
//    @Resource
//    SiteMapper siteMapper;
//
//    @Resource
//     DataSource dataSource;
//    @Resource
//    DataSourceCreator dataSourceCreator;

    @Resource
    private RestTemplate restTemplate;

    @Test
    public void test() {
        String url = "https://www.costhelpercom.com/admin/index.php?user_token=" + "87KyYvqcY2x6hBqvsU5KYoh1dMiDNlyJ";
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("content-type", "multipart/form-data; boundary=----WebKitFormBoundaryz3cn3BLSkyswVbLY");
        httpHeaders.set("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9");
        httpHeaders.set("user-agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_4) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/84.0.4147.135 Safari/537.36");

        HttpEntity entity = new HttpEntity("parameters", httpHeaders);

        ResponseEntity<String> forEntity = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
        System.out.println(forEntity.toString());
    }





//    @Test
//    void test22() throws Exception {
//        EcommerceSite site = siteMapper.selectByPrimaryKey(20);
//        EcommerceProductStore productStore = productStoreService.selectByPrimaryKey(7);
//        String url = "jdbc:mysql://" + site.getIp() + ":" + site.getPort() + "/" + site.getDbName() + "?useUnicode=true&characterEncoding=utf8&serverTimezone=GMT%2B8";
//        Class.forName("com.mysql.cj.jdbc.Driver");
//        Connection connection = DriverManager.getConnection(url, site.getDbUsername(), site.getDbPassword());
//        String insertSql = "INSERT INTO oc_product SET model=?,upc='',ean='',jan='',isbn='',mpn=?,location='',manufacturer_id='0'" +
//                ",date_added=now(),date_modified=now(),sku=?,image=?,price=?,quantity=?, stock_status_id='7', tax_class_id=0,date_available=now(), `status`='1'";
//        try (PreparedStatement preparedStatement = connection.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS)) {
//            preparedStatement.setString(1, productStore.getModel());
//            preparedStatement.setString(2, productStore.getMpn());
//            preparedStatement.setString(3, productStore.getSku());
//            preparedStatement.setString(4, productStore.getImage());
//            preparedStatement.setBigDecimal(5, productStore.getPrice());
//            preparedStatement.setInt(6, productStore.getQuantity());
//            preparedStatement.execute();
//            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
//            Integer productId = null;
//            while (generatedKeys.next()) {
//                productId = generatedKeys.getInt(1);
//            }
//            System.out.println("productId= " + productId);
//        }
//    }


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
}
