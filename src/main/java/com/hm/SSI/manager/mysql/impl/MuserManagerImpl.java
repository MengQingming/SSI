package com.hm.SSI.manager.mysql.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hm.SSI.manager.mysql.MiUserManager;
import com.hm.SSI.model.User;
import com.hm.SSI.service.IUserService;

@Service(value="muserManagerImpl")
public class MuserManagerImpl implements MiUserManager {
	
	private Logger log = Logger.getLogger(MuserManagerImpl.class);
	
	@Autowired
	private IUserService userServiceImpl;
	
	@Override
	public List<User> selectAllUser()  throws Exception {
		log.info("MuserManagerImpl selectAllUser...");
		return userServiceImpl.selectAllUser();
	}

	@Override
	public String getName(String name) throws Exception {
		return name;
	}

}
