package com.xz.upload.dao;

import com.xz.upload.model.Path;

public interface PathMapper {
	int insert(Path record);

	int insertSelective(Path record);

	/**
	 * TODO
	 * @param fileMd5
	 * @return
	 */
	Path getPathBymd5(String fileMd5);
}