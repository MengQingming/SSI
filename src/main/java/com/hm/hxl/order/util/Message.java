/**
 * 项目名: hjkClient
 * 文件名: Message.java
 * Copyright 2014 北京惠买在线网络科技有限公司 
 */
package com.hm.hxl.order.util;

/**
 * 类名: Message 
 * 作用: 接口返回抽象信息类
 * 作者: yanpengjie 
 * 日期: 2015-7-17 下午12:51:07 
 * 版本: V 1.0
 *
 */
public abstract class Message<T> {

	/**标识  1：成功 0：异常   -1：失败**/
	private String flag = "0";
	
	/**接口返回的描述信息**/
	private String error = "签名错误";

	/**返回实体对象**/
	protected T msg;
	
	protected abstract T getMsg();
	protected abstract void setMsg(T msg);
	

	/**
	 * @return flag 
	 */
	public String getFlag() {
		return flag;
	}

	/**
	 * @param flag
	 */
	public void setFlag(String flag) {
		this.flag = flag;
	}
	/**
	 * @return the error
	 */
	public String getError() {
		return error;
	}
	/**
	 * @param error the error to set
	 */
	public void setError(String error) {
		this.error = error;
	}
	
	
}
