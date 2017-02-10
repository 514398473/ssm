/**
 * Copyright © 1998-2017, Glodon Inc. All Rights Reserved.
 */
package com.xz.upload.service;

import com.xz.upload.model.Path;

/**
 * 文件上传Service
 * <p>
 * 文件上传Service
 * </p>
 * 
 * @author xuz-d
 * @since jdk1.6 2017年2月10日
 */

public interface UploadService {

	boolean inseterPath(Path path);

	/**
	 * TODO
	 * @param fileMd5
	 * @return
	 */
	Path getPathBymd5(String fileMd5);
}
