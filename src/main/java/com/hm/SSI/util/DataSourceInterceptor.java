package com.hm.SSI.util;

import org.springframework.stereotype.Component;

/**aop切面拦截,切换数据源**/
@Component("dataSourceInterceptor")
public class DataSourceInterceptor {
	
	/**设置EC1数据源**/
	public void setMySqlDataSource(){
		DataSourceContextHolder.setDataSourceType("EC1");
	}
	
	/**设置EC2数据源**/
	public void setOracleDataSource(){
		DataSourceContextHolder.setDataSourceType("EC2");
	}
}
