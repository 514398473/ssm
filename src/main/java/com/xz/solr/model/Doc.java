/**
 * Copyright © 1998-2017, Glodon Inc. All Rights Reserved.
 */
package com.xz.solr.model;

import org.apache.solr.client.solrj.beans.Field;

/**
 * 文档model
 * <p>
 * 文档model
 * </p>
 * 
 * @author xuz-d
 * @since jdk1.6 2017年2月28日
 */

public class Doc {

	@Field
	private String id;
	@Field
	private String title;
	@Field
	private String content;
	@Field
	private String url;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
