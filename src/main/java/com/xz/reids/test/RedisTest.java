package com.xz.reids.test;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.xz.base.test.BaseTest;
import com.xz.reids.service.RedisService;

public class RedisTest extends BaseTest{

	@Autowired
	private RedisService redisService;
	
	@Test
	public void testRedis() throws Exception {
		
		try {
			redisService.set("name", "zs");
			Object object = redisService.get("name");
			System.out.println(object.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
