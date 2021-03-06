package com.xz.upload.model;

import com.xz.base.model.BaseModel;

public class Path extends BaseModel {

	/**  */
	private static final long serialVersionUID = 7426080273603808983L;

	private Integer id;

	private String path;

	private String md5;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path == null ? null : path.trim();
	}

	public String getMd5() {
		return md5;
	}

	public void setMd5(String md5) {
		this.md5 = md5 == null ? null : md5.trim();
	}
}