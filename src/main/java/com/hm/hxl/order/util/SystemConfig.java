/**
 * 项目名: hjkClient
 * 文件名: SystemConfig.java
 * Copyright 2014 北京惠买在线网络科技有限公司 
 */
package com.hm.hxl.order.util;

import java.io.UnsupportedEncodingException;


/**
 * 类名: SystemConfig 
 * 作用: 读取资源文件 
 * 作者: yanpengjie 
 * 日期: 2015-7-17 下午12:47:53 
 * 版本: V 1.0
 *
 */
public class SystemConfig {
	
	public SystemConfig(String configName){
		this.configName = configName;
	}
	
	/** 配置文件keyval.properties **/
	public String configName = "webservice";
	
	/**配置绑定**/
	private ConfigHandler configHandler = null;
	
	/**
	 * 获取资源文件实例
	 * @return ConfigHandler
	 */
	public ConfigHandler getConfigHandler() {
		configHandler = new ConfigHandler();
		configHandler.setConfigFile(configName);
		return configHandler;
	}
	
	/**
	 * 根据key值获取配置
	 * @return String
	 */
	public String getProperty(String proterty){
		try {
			String str = getConfigHandler().getProperty(proterty);
			return new String(str.getBytes("ISO-8859-1"),"UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}
	}
	
}
