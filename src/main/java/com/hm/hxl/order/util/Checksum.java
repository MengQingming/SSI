/**
 * 项目名: hczClient
 * 文件名: Checksum.java
 * Copyright 2014 北京惠买在线网络科技有限公司 
 */
package com.hm.hxl.order.util;

import java.io.Serializable;

/**
 * 
 * 类 名: Checksum
 * <br/>描 述: 请求头参数
 * <br/>作 者: tubingbing
 * <br/>创 建： 2014-8-11
 * <br/>版 本：v1.0
 *
 * <br/>历 史: 无
 */
public class Checksum  implements Serializable{

	/** 字段描述: **/
	private static final long serialVersionUID = 6272227803542185588L;

	//加密token
	private String sign;
	
	//客户端持有的私钥
	private String api_key;
	
	//每次访问时的随机数，
	private String nonce;
	
	//接口版本号
	private String version;
	
	//加或解 密方式
	private String sign_method;
	
	//时间戳
	private String timestamp;
	
	//客户端类型
	private String type;
	//hxl来源
	private String hxl_type;
	
	private String ip="127.0.0.1";
	
	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getHxl_type() {
		return hxl_type;
	}

	public void setHxl_type(String hxl_type) {
		this.hxl_type = hxl_type;
	}
	
	/**
	 * @return the sign
	 */
	public String getSign() {
		return sign;
	}

	/**
	 * @param sign the sign to set
	 */
	public void setSign(String sign) {
		this.sign = sign;
	}

	/**
	 * @return the api_key
	 */
	public String getApi_key() {
		return api_key;
	}

	/**
	 * @param api_key the api_key to set
	 */
	public void setApi_key(String api_key) {
		this.api_key = api_key;
	}

	/**
	 * @return the nonce
	 */
	public String getNonce() {
		return nonce;
	}

	/**
	 * @param nonce the nonce to set
	 */
	public void setNonce(String nonce) {
		this.nonce = nonce;
	}

	/**
	 * @return the version
	 */
	public String getVersion() {
		return version;
	}

	/**
	 * @param version the version to set
	 */
	public void setVersion(String version) {
		this.version = version;
	}

	/**
	 * @return the sign_method
	 */
	public String getSign_method() {
		return sign_method;
	}

	/**
	 * @param sign_method the sign_method to set
	 */
	public void setSign_method(String sign_method) {
		this.sign_method = sign_method;
	}

	/**
	 * @return the timestamp
	 */
	public String getTimestamp() {
		return timestamp;
	}

	/**
	 * @param timestamp the timestamp to set
	 */
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * 
	 * 描 述： 消息日志格式
	 * @return String
	 */
	public String toLog() {
		return "Checksum [sign=" + sign + ", api_Key=" + api_key + ", nonce="
				+ nonce + ", version=" + version + ", sign_method="
				+ sign_method + ", timestamp=" + timestamp + ", type=" + type
				+ "]";
	}
	
	@Override
	public String toString() {
		String returnStr = api_key + nonce + sign_method +  timestamp + type + version;
		returnStr = returnStr.replaceAll("null", "");
		return returnStr;
	}
}
