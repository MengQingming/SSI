package com.hm.SSI.ws.impl;

import org.apache.log4j.Logger;

import com.hm.SSI.ws.RestWebService;
import com.hm.SSI.ws.model.RespBean;

public class RestWebServiceImpl implements RestWebService{
	
	private static Logger log = Logger.getLogger(RestWebServiceImpl.class);
	
	public RespBean login(String username, String password){
		log.info("RestWebServiceImpl login--->Begin");
		log.info("RestWebServiceImpl login--->username:"+username+", password:"+password);
		RespBean respBean = new RespBean();
		respBean.setStatus(200);
		respBean.setMessage("username:"+username+", password:"+password);
		log.info("RestWebServiceImpl login--->End");
		return respBean;
	}
}
