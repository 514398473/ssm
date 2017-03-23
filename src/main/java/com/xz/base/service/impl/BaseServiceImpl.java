/**
 * Copyright © 1998-2017, Glodon Inc. All Rights Reserved.
 */
package com.xz.base.service.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.xz.base.dao.BaseDao;
import com.xz.base.service.BaseService;

/**
 * 基础service实现类
 * <p>
 * 基础service实现类
 * </p>
 * 
 * @author xuz-d
 * @since jdk1.6 2017年3月23日
 */
public class BaseServiceImpl<T, E, ID extends Serializable> implements BaseService<T, E, ID> {

	@Autowired
	private BaseDao<T, E, ID> baseDao;
	
	/**
	 * {@inheritDoc}
	 * 
	 * @see com.xz.base.service.BaseService#countByExample(java.lang.Object)
	 */
	@Override
	public int countByExample(E example) {
		return baseDao.countByExample(example);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.xz.base.service.BaseService#deleteByExample(java.lang.Object)
	 */
	@Override
	public int deleteByExample(E example) {
		return baseDao.deleteByExample(example);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.xz.base.service.BaseService#deleteByPrimaryKey(java.io.Serializable)
	 */
	@Override
	public int deleteByPrimaryKey(ID id) {
		return baseDao.deleteByPrimaryKey(id);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.xz.base.service.BaseService#insert(java.lang.Object)
	 */
	@Override
	public int insert(T record) {
		return baseDao.insert(record);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.xz.base.service.BaseService#insertSelective(java.lang.Object)
	 */
	@Override
	public int insertSelective(T record) {
		return baseDao.insertSelective(record);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.xz.base.service.BaseService#selectByExampleWithBLOBs(java.lang.Object)
	 */
	@Override
	public List<T> selectByExampleWithBLOBs(E example) {
		return baseDao.selectByExampleWithBLOBs(example);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.xz.base.service.BaseService#selectByExample(java.lang.Object)
	 */
	@Override
	public List<T> selectByExample(E example) {
		return baseDao.selectByExample(example);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.xz.base.service.BaseService#selectByPrimaryKey(java.io.Serializable)
	 */
	@Override
	public T selectByPrimaryKey(ID id) {
		return baseDao.selectByPrimaryKey(id);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.xz.base.service.BaseService#updateByExampleSelective(java.lang.Object,
	 *      java.lang.Object)
	 */
	@Override
	public int updateByExampleSelective(T record, E example) {
		return baseDao.updateByExampleSelective(record, example);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.xz.base.service.BaseService#updateByExampleWithBLOBs(java.lang.Object,
	 *      java.lang.Object)
	 */
	@Override
	public int updateByExampleWithBLOBs(T record, E example) {
		return baseDao.updateByExampleWithBLOBs(record, example);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.xz.base.service.BaseService#updateByExample(java.lang.Object,
	 *      java.lang.Object)
	 */
	@Override
	public int updateByExample(T record, E example) {
		return baseDao.updateByExample(record, example);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.xz.base.service.BaseService#updateByPrimaryKeySelective(java.lang.Object)
	 */
	@Override
	public int updateByPrimaryKeySelective(T record) {
		return baseDao.updateByPrimaryKeySelective(record);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.xz.base.service.BaseService#updateByPrimaryKeyWithBLOBs(java.lang.Object)
	 */
	@Override
	public int updateByPrimaryKeyWithBLOBs(T record) {
		return baseDao.updateByPrimaryKeyWithBLOBs(record);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.xz.base.service.BaseService#updateByPrimaryKey(java.lang.Object)
	 */
	@Override
	public int updateByPrimaryKey(T record) {
		return baseDao.updateByPrimaryKey(record);
	}

}
