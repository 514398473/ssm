/**
 * Copyright © 1998-2017, Glodon Inc. All Rights Reserved.
 */
package com.xz.base.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 基础controller
 * <p>
 * 基础controller
 * </p>
 * 
 * @author xuz-d
 * @since jdk1.6 2017年3月23日
 */

public class BaseController {

	/**
	 * 日志记录对象，子类直接使用即可
	 */
	protected Logger logger = LoggerFactory.getLogger(getClass());
}
