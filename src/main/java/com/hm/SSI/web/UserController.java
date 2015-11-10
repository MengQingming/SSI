package com.hm.SSI.web;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hm.SSI.manager.mysql.MiUserManager;
import com.hm.SSI.model.User;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private MiUserManager mUserManagerImpl;
	
	@RequestMapping(value="/getAllUser", method=RequestMethod.GET)
	public @ResponseBody List<User> getAllUser(){
		List<User> userList = new ArrayList<User>();
		try {
			userList = mUserManagerImpl.selectAllUser();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return userList;
	}
	
}
