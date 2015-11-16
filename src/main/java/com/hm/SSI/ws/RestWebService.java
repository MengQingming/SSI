package com.hm.SSI.ws;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import com.hm.SSI.ws.model.RespBean;

@Path("/app")
public interface RestWebService {
	
//	@GET
	@POST
	@Produces("application/json")
	@Path("/user/login")
	public RespBean login(@FormParam("username") String username,
			@FormParam("password") String password);
	
}
