package com.xm.commerce;

import com.xm.commerce.system.mapper.ecommerce.SiteMapper;
import com.xm.commerce.system.model.entity.ecommerce.Site;
import org.apache.commons.codec.binary.Base64;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;

@SpringBootTest
public class LoginTest {

	@Resource
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	@Resource
	SiteMapper siteMapper;

	@Test
	public void test() throws UnsupportedEncodingException {
//		System.out.println(bCryptPasswordEncoder.encode("123456"));

		Site site = siteMapper.selectByPrimaryKey(2);
		String authorization = site.getApiKey() + ":" + site.getApiPassword();
		Base64 base64 = new Base64();
		String base64Token = base64.encodeToString(authorization.getBytes("UTF-8"));
		String token = "Basic " + base64Token;
		String shopifyToken = "Basic ZDM1MWU0ZDkzNzY1ZmYzNDc1Y2I1MGVjYjM0MDIzYjU6c2hwcGFfYTUyMTdmYTU0YWQ0NWEwN2JhNjVkZWQxN2VhYWJmYzM=";
		System.out.println(token);
		System.out.println(shopifyToken.equals(token));

	}
}
