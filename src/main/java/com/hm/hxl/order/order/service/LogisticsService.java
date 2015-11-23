package com.hm.hxl.order.order.service;

import com.hm.hxl.order.order.model.GoodsStockByIdParameters;
import com.hm.hxl.order.util.Checksum;

/**
 * 
 * 类 名: LogisticsService
 * <br/>描 述: 查询仓库存量service
 * <br/>作 者: yanpengjie
 * <br/>创 建： 2014-8-11
 * <br/>版 本：v1.0
 *
 * <br/>历 史:无
 */
public interface LogisticsService {

	/**
	 * 
	 * 描 述：查询仓库存量
	 * @param checkSum
	 * @param param 
	 * @return String 
	 * @throws Exception
	 */
	public String queryGoodsStockById( Checksum checkSum,GoodsStockByIdParameters param ) throws Exception;
}
