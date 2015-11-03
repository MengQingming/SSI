package com.hm.SSI.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hm.SSI.dao.IUserDao;
import com.hm.SSI.model.User;
import com.hm.SSI.service.IUserService;

@Service
public class UserServiceImpl implements IUserService {

	@Autowired
	private IUserDao userDaoImpl;
	
	@Override
	public List<User> selectAllUser() throws Exception{
		return userDaoImpl.selectAllUser();
	}

}
