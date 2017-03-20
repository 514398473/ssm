package com.xz.crawler.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xz.crawler.dao.HtmlMapper;
import com.xz.crawler.model.Html;
import com.xz.crawler.model.HtmlExample;
import com.xz.crawler.service.CrawlerService;

@Service
public class CrawlerServiceImpl implements CrawlerService {

	@Autowired
	private HtmlMapper htmlMapper;
	
	@Override
	public int countByExample(HtmlExample example) {
		return htmlMapper.countByExample(example);
	}

	@Override
	public int deleteByExample(HtmlExample example) {
		return htmlMapper.deleteByExample(example);
	}

	@Override
	public int deleteByPrimaryKey(Integer id) {
		return htmlMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(Html record) {
		return htmlMapper.insert(record);
	}

	@Override
	public int insertSelective(Html record) {
		return htmlMapper.insertSelective(record);
	}

	@Override
	public List<Html> selectByExampleWithBLOBs(HtmlExample example) {
		return htmlMapper.selectByExampleWithBLOBs(example);
	}

	@Override
	public List<Html> selectByExample(HtmlExample example) {
		return htmlMapper.selectByExample(example);
	}

	@Override
	public Html selectByPrimaryKey(Integer id) {
		return htmlMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByExampleSelective(Html record, HtmlExample example) {
		return htmlMapper.updateByExampleSelective(record, example);
	}

	@Override
	public int updateByExampleWithBLOBs(Html record, HtmlExample example) {
		
		return htmlMapper.updateByExampleWithBLOBs(record, example);
	}

	@Override
	public int updateByExample(Html record, HtmlExample example) {
		return htmlMapper.updateByExample(record, example);
	}

	@Override
	public int updateByPrimaryKeySelective(Html record) {
		return htmlMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKeyWithBLOBs(Html record) {
		return htmlMapper.updateByPrimaryKeyWithBLOBs(record);
	}

	@Override
	public int updateByPrimaryKey(Html record) {
		return htmlMapper.updateByPrimaryKey(record);
	}

}
