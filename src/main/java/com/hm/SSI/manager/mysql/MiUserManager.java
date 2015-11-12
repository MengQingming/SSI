package com.hm.SSI.manager.mysql;

import java.util.List;

import com.hm.SSI.model.User;

public interface MiUserManager {
	
	public List<User> selectAllUser() throws Exception;
	
	public String getName(String name) throws Exception;
	
}
