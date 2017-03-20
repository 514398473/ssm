package com.xz.crawler.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.xz.crawler.model.Urlmd5;
import com.xz.crawler.model.Urlmd5Example;

public interface Urlmd5Service {

	int countByExample(Urlmd5Example example);

    int deleteByExample(Urlmd5Example example);

    int deleteByPrimaryKey(Integer id);

    int insert(Urlmd5 record);

    int insertSelective(Urlmd5 record);

    List<Urlmd5> selectByExample(Urlmd5Example example);

    Urlmd5 selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Urlmd5 record, @Param("example") Urlmd5Example example);

    int updateByExample(@Param("record") Urlmd5 record, @Param("example") Urlmd5Example example);

    int updateByPrimaryKeySelective(Urlmd5 record);

    int updateByPrimaryKey(Urlmd5 record);
}
