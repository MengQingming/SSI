package com.hm.SSI.manager.oracle;

import java.util.List;

import com.hm.SSI.model.User;

public interface OiUserManager {
	
	public List<User> selectAllUser() throws Exception;
	
}
