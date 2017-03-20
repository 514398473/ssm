package com.xz.crawler.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xz.crawler.dao.Urlmd5Mapper;
import com.xz.crawler.model.Urlmd5;
import com.xz.crawler.model.Urlmd5Example;
import com.xz.crawler.service.Urlmd5Service;

@Service
public class Urlmd5ServiceImpl implements Urlmd5Service {

	@Autowired
	private Urlmd5Mapper urlmd5Mapper;

	@Override
	public int countByExample(Urlmd5Example example) {
		return urlmd5Mapper.countByExample(example);
	}

	@Override
	public int deleteByExample(Urlmd5Example example) {
		return urlmd5Mapper.deleteByExample(example);
	}

	@Override
	public int deleteByPrimaryKey(Integer id) {
		return urlmd5Mapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(Urlmd5 record) {
		return urlmd5Mapper.insert(record);
	}

	@Override
	public int insertSelective(Urlmd5 record) {
		return urlmd5Mapper.insertSelective(record);
	}

	@Override
	public List<Urlmd5> selectByExample(Urlmd5Example example) {
		return urlmd5Mapper.selectByExample(example);
	}

	@Override
	public Urlmd5 selectByPrimaryKey(Integer id) {
		return urlmd5Mapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByExampleSelective(Urlmd5 record, Urlmd5Example example) {
		return urlmd5Mapper.updateByExampleSelective(record, example);
	}

	@Override
	public int updateByExample(Urlmd5 record, Urlmd5Example example) {
		return updateByExample(record, example);
	}

	@Override
	public int updateByPrimaryKeySelective(Urlmd5 record) {
		return urlmd5Mapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(Urlmd5 record) {
		return urlmd5Mapper.updateByPrimaryKey(record);
	}

}
