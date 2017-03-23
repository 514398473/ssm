/**
 * Copyright © 1998-2017, Glodon Inc. All Rights Reserved.
 */
package com.xz.base.dao;

import java.io.Serializable;
import java.util.List;

import org.apache.ibatis.annotations.Param;

/**
 * 基础dao
 * <p>
 * 基础dao
 * </p>
 * 
 * @author xuz-d
 * @since jdk1.6 2017年3月23日
 */

public interface BaseDao<T, E, ID extends Serializable> {

	int countByExample(E example);

	int deleteByExample(E example);

	int deleteByPrimaryKey(ID id);

	int insert(T record);

	int insertSelective(T record);

	List<T> selectByExampleWithBLOBs(E example);

	List<T> selectByExample(E example);

	T selectByPrimaryKey(ID id);

	int updateByExampleSelective(@Param("record") T record, @Param("example") E example);

	int updateByExampleWithBLOBs(@Param("record") T record, @Param("example") E example);

	int updateByExample(@Param("record") T record, @Param("example") E example);

	int updateByPrimaryKeySelective(T record);

	int updateByPrimaryKeyWithBLOBs(T record);

	int updateByPrimaryKey(T record);

}
