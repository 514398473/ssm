package com.xz.crawler.dao;

import com.xz.crawler.model.Html;
import com.xz.crawler.model.HtmlExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface HtmlMapper {
	
	int countByExample(HtmlExample example);

	int deleteByExample(HtmlExample example);

	int deleteByPrimaryKey(Integer id);

	int insert(Html record);

	int insertSelective(Html record);

	List<Html> selectByExampleWithBLOBs(HtmlExample example);

	List<Html> selectByExample(HtmlExample example);

	Html selectByPrimaryKey(Integer id);

	int updateByExampleSelective(@Param("record") Html record, @Param("example") HtmlExample example);

	int updateByExampleWithBLOBs(@Param("record") Html record, @Param("example") HtmlExample example);

	int updateByExample(@Param("record") Html record, @Param("example") HtmlExample example);

	int updateByPrimaryKeySelective(Html record);

	int updateByPrimaryKeyWithBLOBs(Html record);

	int updateByPrimaryKey(Html record);
}