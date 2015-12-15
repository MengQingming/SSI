package com.hm.SSI.ws.impl;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.apache.log4j.Logger;

import com.hm.SSI.ws.RestWebService;
import com.hm.SSI.ws.model.RespBean;

@Path("/app")
public class RestWebServiceImpl implements RestWebService{
	
	private static Logger log = Logger.getLogger(RestWebServiceImpl.class);
	
//	@GET
	@POST
	@Produces("application/json")
	@Path("/user/login")
	public RespBean login(@FormParam("username") String username, 
			@FormParam("password") String password){
		log.info("RestWebServiceImpl login--->Begin");
		log.info("RestWebServiceImpl login--->username:"+username+", password:"+password);
		RespBean respBean = new RespBean();
		respBean.setStatus(200);
		respBean.setMessage("username:"+username+", password:"+password);
		log.info("RestWebServiceImpl login--->End");
		return respBean;
	}
}
