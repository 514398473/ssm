/**
 * Copyright © 1998-2017, Glodon Inc. All Rights Reserved.
 */
package com.xz.user.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.xz.base.controller.BaseController;
import com.xz.user.model.User;
import com.xz.user.model.UserExample;
import com.xz.user.service.UserService;

/**
 * 此处填写类简介
 * <p>
 * 此处填写类说明
 * </p>
 * 
 * @author xuz-d
 * @since jdk1.6 2017年1月24日
 */
@Controller
@RequestMapping("/user")
public class UserController extends BaseController {

	@Autowired
	private UserService userService;

	@RequestMapping("/list")
	public ModelAndView list(Model model) {
		ModelAndView mav = new ModelAndView("user/user_list");
		UserExample example = new UserExample();
		List<User> userList = userService.selectByExample(example);
		logger.info("查询数据成功!-class:" + UserController.class.getSimpleName() + " method:list");
		mav.addObject("userList", userList);
		return mav;
	}
}
