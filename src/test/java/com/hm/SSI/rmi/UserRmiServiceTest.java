package com.hm.SSI.rmi;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.hm.SSI.manager.mysql.MiUserManager;
import com.hm.SSI.model.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:/applicationContext.xml", "classpath:/application-RmiService.xml"})
public class UserRmiServiceTest {
	
	@Test
	public void testRmiService(){
		try {
			//new ClassPathXmlApplicationContext("application-RmiService.xml");  
			Object lock = new Object();  
			synchronized (lock) {  
			    lock.wait();  
			}
		} catch (BeansException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
	}
	
}

