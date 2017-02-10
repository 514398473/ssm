/**
 * Copyright © 1998-2017, Glodon Inc. All Rights Reserved.
 */
package com.xz.upload.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xz.upload.dao.PathMapper;
import com.xz.upload.model.Path;
import com.xz.upload.service.UploadService;

/**
 * 此处填写类简介
 * <p>
 * 此处填写类说明
 * </p>
 * 
 * @author xuz-d
 * @since jdk1.6 2017年2月10日
 */
@Service
public class UploadServiceImpl implements UploadService {

	@Autowired
	private PathMapper pathMapper;
	/**
	 * {@inheritDoc}
	 * 
	 * @see com.xz.upload.service.UploadService#inseterPath(com.xz.upload.model.Path)
	 */
	@Override
	public boolean inseterPath(Path path) {
		try {
			pathMapper.insertSelective(path);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	/** 
	  * {@inheritDoc}   
	  * @see com.xz.upload.service.UploadService#getPathBymd5(java.lang.String) 
	  */
	@Override
	public Path getPathBymd5(String fileMd5) {
		return pathMapper.getPathBymd5(fileMd5);
	}

}
