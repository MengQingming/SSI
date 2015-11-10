package com.hm.SSI.service;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.hm.SSI.manager.mysql.MiUserManager;
import com.hm.SSI.manager.oracle.OiUserManager;
import com.hm.SSI.model.User;

/**
 * 测试类
 * @ClassName: UserTest 
 * @Description: TODO 
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)  
@ContextConfiguration(locations={"/applicationContext.xml"})
public class UserTest {
	
	@Autowired
	private OiUserManager ouserManagerImpl;
	
	@Autowired
	private MiUserManager muserManagerImpl;
	
	
	@Test
	public void testMysqlSelectAllUser(){
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

	
	
//	@Test
//	public void testOracleSelectAllUser(){
//		try {
//			List<User> userList = ouserManagerImpl.selectAllUser();
//			for (User user : userList) {
//				System.out.println("Id:"+user.getId()+"   name:"+user.getName());
//			}
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
	
}
