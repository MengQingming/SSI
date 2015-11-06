package com.hm.SSI.ws.model;

import java.io.Serializable;

/**
 * 服务器响应信息
 */
public class RespBean implements Serializable {

	private static final long serialVersionUID = 1437455337611728097L;

	private int status = 200;   //响应状态码      200:正确     500：异常
	 
	private String message = "success";  //返回信息
	private Object result;  //返回信息数据实体
	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getResult() {
		return result;
	}

	public void setResult(Object result) {
		this.result = result;
	}
	
}
