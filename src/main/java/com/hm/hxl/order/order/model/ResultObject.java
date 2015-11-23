package com.hm.hxl.order.order.model;

import java.io.Serializable;

import com.hm.hxl.order.util.Message;

/**
 * 
 * 类 名: ResultObject
 * <br>描 述: 接口返回结果
 * <br>作 者: tubingbing
 * <br>创 建： 2014-9-22
 * <br>版 本：v1.0
 *
 * <br>历 史: 无
 */
public class ResultObject extends Message<Object> implements Serializable{
	
	/**uid**/
	private static final long serialVersionUID = 4210294864072444235L;
	
	/**
	 * 构造方法
	 * @param flag
	 * @param result
	 * @param code
	 * @param resultObject
	 */
	public ResultObject(String flag,String error,Object msg){
		this.setFlag(flag);
		this.setError(error);
		this.setMsg(msg);
	}
	/**
	 * 无参构造方法
	 */
	public ResultObject(){}
	
	@Override
	public Object getMsg() {
		return this.msg;
	}
	@Override
	public void setMsg(Object msg) {
		this.msg = msg;
	}
	@Override
	public String toString() {
		return "ResultObject [msg="+getMsg()+", flag="+getFlag()+", error="+getError()+"]";
	}
	
}

