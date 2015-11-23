package com.hm.hxl.order.order.service;

import java.util.Map;

import com.hm.hxl.order.order.model.Area;
import com.hm.hxl.order.order.model.ResultArea;

/**
 * 类名: IAreaService 
 * 作用: TODO(这里用一句话描述这个类的作用) 
 * 作者: yanpengjie 
 * 日期: 2015-7-17 下午12:54:50 
 * 版本: V 1.0
 *
 */
@SuppressWarnings("rawtypes" )
public interface IAreaService {

	
	public ResultArea getArea(Map map);
	//根据地区名称获得地区
	public Area getLocal_name(String local_name); 
	//根据地区名称获得省的id
	public Area getProIdBylocal_name(String local_name );
	//根据地区名称,上级id获得市
	public Area getCityIdBylocal_name(Area area );
	//根据地区名,上级id称获得区
	public Area getCountyIdBylocal_name(Area area );
	//
	public Area getRegionById(String region_id);
}
