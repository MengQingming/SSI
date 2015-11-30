/*
 * Copyright 1999-2012 Alibaba Group.
 *  
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *  
 *      http://www.apache.org/licenses/LICENSE-2.0
 *  
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.hm.SSI.dubbo;

import java.util.List;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.hm.SSI.dubbo.action.AnnotationAction;
import com.hm.SSI.manager.mysql.MiUserManager;
import com.hm.SSI.model.User;


/**
 * CallbackConsumer
 * 
 * @author william.liangf
 */
public class DubboConsumer {

	private MiUserManager muserManagerImplTemp;
	
	@Test
	public void testMysqlSelectAllUser(){
		
		 String config = "classpath:/dubbo-consumer.xml";
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(config);
        context.start();
        final MiUserManager muserManagerImpl = (MiUserManager)context.getBean("muserManagerImpl");
		
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
	
    public static void main(String[] args) throws Exception {
        String config = "classpath:/dubbo-consumer.xml";		//AnnotationConsumer.class.getPackage().getName().replace('.', '/') + "/annotation-consumer.xml";
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(config);
        context.start();
        final AnnotationAction annotationAction = (AnnotationAction)context.getBean("annotationAction");
        String hello = annotationAction.doSayHello("world");
        System.out.println("result :" + hello);
        System.in.read();
    }

}
