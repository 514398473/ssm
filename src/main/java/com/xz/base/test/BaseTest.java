/**
 * Copyright © 1998-2017, Glodon Inc. All Rights Reserved.
 */
package com.xz.base.test;

import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 基础测试类
 * <p>
 * 基础测试类
 * </p>
 * 
 * @author xuz-d
 * @since jdk1.6 2017年3月23日
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:applicationContext-*.xml")
public class BaseTest {

	/**
	 * 日志记录对象，子类直接使用即可
	 */
	protected Logger logger = LoggerFactory.getLogger(getClass());
	
}
