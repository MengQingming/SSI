package com.hm.SSI.manager.mysql.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hm.SSI.manager.mysql.MiUserManager;
import com.hm.SSI.model.User;
import com.hm.SSI.service.IUserService;

@Service
public class MuserManagerImpl implements MiUserManager {

	@Autowired
	private IUserService userServiceImpl;
	
	@Override
	public List<User> selectAllUser()  throws Exception {
		return userServiceImpl.selectAllUser();
	}

}
