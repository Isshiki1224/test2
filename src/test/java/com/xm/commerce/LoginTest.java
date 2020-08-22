package com.xm.commerce;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.annotation.Resource;

@SpringBootTest
public class LoginTest {

	@Resource
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Test
	public void test() {
		System.out.println(bCryptPasswordEncoder.encode("123456"));
	}
}
