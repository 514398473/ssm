package com.xz.test.dao;

import java.util.List;

import com.xz.test.model.User;

public interface UserMapper {
    int deleteByPrimaryKey(String username);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(String username);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

	/**
	 * TODO
	 * @return
	 */
	List<User> findAll();
}