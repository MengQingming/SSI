package com.hm.SSI.manager.oracle.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hm.SSI.manager.oracle.OiUserManager;
import com.hm.SSI.model.User;
import com.hm.SSI.service.IUserService;

@Service
public class OuserManagerImpl implements OiUserManager {

	@Autowired
	private IUserService userServiceImpl;
	
	@Override
	public List<User> selectAllUser()  throws Exception {
		return userServiceImpl.selectAllUser();
	}

}
