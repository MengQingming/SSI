package com.hm.SSI.ws;

import com.hm.SSI.ws.model.RespBean;

public interface RestWebService {
	
	public RespBean login(String username, String password);
	
}
