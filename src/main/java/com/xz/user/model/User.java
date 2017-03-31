package com.xz.user.model;

import com.xz.base.model.BaseModel;

public class User extends BaseModel {
	
	/**  */
	private static final long serialVersionUID = -980314285028871072L;

	private Integer id;

	private String username;

	private String password;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username == null ? null : username.trim();
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password == null ? null : password.trim();
	}
}