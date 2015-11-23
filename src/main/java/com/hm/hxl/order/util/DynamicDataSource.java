package com.hm.hxl.order.util;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * 类名: DynamicDataSource 
 * 作用 : 配置多数据源
 * 作者: yanpengjie 
 * 日期: 2015-7-17 下午12:49:43 
 * 版本: V 1.0
 *
 */
public class DynamicDataSource extends AbstractRoutingDataSource {

	/* (non-Javadoc)
	 * @see org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource#determineCurrentLookupKey()
	 */
	@Override
	protected Object determineCurrentLookupKey() {
		return DataSourceContextHolder.getDataSourceType();
		
	}

}
