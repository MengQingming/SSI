package com.hm.SSI.service;

import java.util.List;

import com.hm.SSI.model.User;

public interface IUserService {
	
	public List<User> selectAllUser() throws Exception;
	
}
