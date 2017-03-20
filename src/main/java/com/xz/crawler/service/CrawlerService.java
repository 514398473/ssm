package com.xz.crawler.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.xz.crawler.model.Html;
import com.xz.crawler.model.HtmlExample;

public interface CrawlerService {

	public int countByExample(HtmlExample example);

	public int deleteByExample(HtmlExample example);

	public int deleteByPrimaryKey(Integer id);

	public int insert(Html record);

	public int insertSelective(Html record);

	public List<Html> selectByExampleWithBLOBs(HtmlExample example);

	public List<Html> selectByExample(HtmlExample example);

	public Html selectByPrimaryKey(Integer id);

	public int updateByExampleSelective(@Param("record") Html record, @Param("example") HtmlExample example);

	public int updateByExampleWithBLOBs(@Param("record") Html record, @Param("example") HtmlExample example);

	public int updateByExample(@Param("record") Html record, @Param("example") HtmlExample example);

	public int updateByPrimaryKeySelective(Html record);

	public int updateByPrimaryKeyWithBLOBs(Html record);

	public int updateByPrimaryKey(Html record);
}
