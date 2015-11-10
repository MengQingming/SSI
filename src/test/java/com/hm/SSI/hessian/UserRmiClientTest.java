package com.hm.SSI.hessian;

import java.util.List;

import org.junit.Test;

import com.caucho.hessian.client.HessianProxyFactory;
import com.hm.SSI.manager.mysql.MiUserManager;
import com.hm.SSI.model.User;

public class UserRmiClientTest {

	private MiUserManager muserManagerImpl;
	
	
	@Test
	public void testMysqlSelectAllUser(){
		try {
			String url = "http://localhost:8080/SSI/hessian/hessianManager";
            HessianProxyFactory factory = new HessianProxyFactory();    
            muserManagerImpl = (MiUserManager) factory.create(MiUserManager.class, url);    
			
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

