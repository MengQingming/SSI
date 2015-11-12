package com.hm.SSI.rmi;

import java.util.List;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.hm.SSI.manager.mysql.MiUserManager;
import com.hm.SSI.model.User;

public class UserRmiClientTest {

	private MiUserManager muserManagerImpl;
	
	
	@Test
	public void testMysqlSelectAllUser(){
		
		ApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:/application-RmiClient.xml");  
		muserManagerImpl = ctx.getBean(MiUserManager.class);  
		
		try {
			List<User> userList = muserManagerImpl.selectAllUser();
			for (User user : userList) {
				System.out.println("Id:"+user.getId()+"   name:"+user.getName());
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}

