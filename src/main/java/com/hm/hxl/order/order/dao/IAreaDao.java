package com.hm.hxl.order.order.dao;


import java.util.Map;

import com.hm.hxl.order.order.model.Area;
import com.hm.hxl.order.order.model.ResultArea;




@SuppressWarnings("rawtypes")
public interface IAreaDao {

	
	public ResultArea getArea(Map map);
	
	//根据地区名称获得地区
	public Area getLocal_name(String local_name);
	//根据地区名称获得省的id
	public Area getProIdBylocal_name(String local_name );
	//根据地区名称获和上级id得市
	public Area getCityIdBylocal_name(Area area );
	//根据地区名称和上级id获得区
	public Area getCountyIdBylocal_name(Area area );
	
	public Area getRegionById(String region_id);

	Area getCountyRegionById(String region_id)throws Exception;
}
