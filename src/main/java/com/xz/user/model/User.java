package com.xz.user.model;

import com.xz.annotation.field.GenerateGetAndSet;
import com.xz.base.model.BaseModel;

public class User extends BaseModel {

	/**  */
	private static final long serialVersionUID = -980314285028871072L;

	@GenerateGetAndSet(name = "id", description = "主键")
	private Integer id;
	@GenerateGetAndSet
	private String username;
	@GenerateGetAndSet
	private String password;

}