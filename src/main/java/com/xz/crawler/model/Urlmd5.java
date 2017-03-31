package com.xz.crawler.model;

import com.xz.base.model.BaseModel;

public class Urlmd5 extends BaseModel {
	
	/**  */
	private static final long serialVersionUID = 6960628562396947886L;

	private Integer id;

	private String md5;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getMd5() {
		return md5;
	}

	public void setMd5(String md5) {
		this.md5 = md5 == null ? null : md5.trim();
	}
}