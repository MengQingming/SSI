package com.hm.hxl.order.util;

import org.springframework.stereotype.Component;
/**
 * 类名: DataSourceInterceptor 
 * 作用: TODO(这里用一句话描述这个类的作用) 
 * 作者: yanpengjie 
 * 日期: 2015-7-17 下午12:49:02 
 * 版本: V 1.0
 *
 */
@Component("dataSourceInterceptor")
public class DataSourceInterceptor {
	
	public void setMySqlDataSource(){
		DataSourceContextHolder.setDataSourceType("WMS");
	}
	
	public void setOracleDataSource(){
		DataSourceContextHolder.setDataSourceType("EDI");
	}
}
