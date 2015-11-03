package com.hm.SSI.dao;

import java.util.List;

import com.hm.SSI.model.User;

public interface IUserDao {
	
	public List<User> selectAllUser() throws Exception;
	
}
