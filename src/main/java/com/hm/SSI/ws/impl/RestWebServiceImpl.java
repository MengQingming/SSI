package com.hm.SSI.ws.impl;

import com.hm.SSI.ws.RestWebService;
import com.hm.SSI.ws.model.RespBean;

public class RestWebServiceImpl implements RestWebService{
	
	
	public RespBean login(){
		RespBean respBean = new RespBean();
		respBean.setStatus(200);
		respBean.setMessage("OK");
		return respBean;
	}
}
