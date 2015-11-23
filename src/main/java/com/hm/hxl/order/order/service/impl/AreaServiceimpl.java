package com.hm.hxl.order.order.service.impl;


import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hm.hxl.order.order.dao.IAreaDao;
import com.hm.hxl.order.order.model.Area;
import com.hm.hxl.order.order.model.ResultArea;
import com.hm.hxl.order.order.service.IAreaService;
/**
 * 类名: AreaServiceimpl 
 * 作用: TODO(这里用一句话描述这个类的作用) 
 * 作者: yanpengjie 
 * 日期: 2015-7-17 下午12:55:43 
 * 版本: V 1.0
 *
 */
@Service
public class AreaServiceimpl implements IAreaService {
	@Autowired
	private IAreaDao areaDaoImpl;
	
	@SuppressWarnings("rawtypes")
	@Override
	public ResultArea getArea(Map map){
		// TODO Auto-generated method stub
		return areaDaoImpl.getArea(map);
	}

	@Override
	public Area getLocal_name(String local_name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Area getProIdBylocal_name(String local_name) {
		// TODO Auto-generated method stub
		return areaDaoImpl.getProIdBylocal_name(local_name);
	}

	@Override
	public Area getCityIdBylocal_name(Area area) {
		// TODO Auto-generated method stub
		return areaDaoImpl.getCityIdBylocal_name(area);
	}

	@Override
	public Area getCountyIdBylocal_name(Area area) {
		// TODO Auto-generated method stub
		return areaDaoImpl.getCountyIdBylocal_name(area);
	}

	
	//根据id获得省市区
	@Override
	public Area getRegionById(String region_id) {
		// TODO Auto-generated method stub
		return areaDaoImpl.getRegionById(region_id);
	}

	


}
