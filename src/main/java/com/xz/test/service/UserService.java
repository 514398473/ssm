/**
 * Copyright © 1998-2017, Glodon Inc. All Rights Reserved.
 */
package com.xz.test.service;
import java.util.List;

import com.xz.test.model.User;


/**
 * 此处填写类简介
 * <p>
 * 此处填写类说明
 * </p>
 * 
 * @author xuz-d
 * @since jdk1.6 2017年1月24日
 */

public interface UserService {

	/**
	 * 查询所有用户
	 */
	List<User> findUsers();
}
