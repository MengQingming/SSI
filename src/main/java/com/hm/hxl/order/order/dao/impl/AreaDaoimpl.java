package com.hm.hxl.order.order.dao.impl;

/**
 * hanahngyun
 */
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hm.hxl.order.order.dao.IAreaDao;
import com.hm.hxl.order.order.model.Area;
import com.hm.hxl.order.order.model.ResultArea;
import com.ibatis.sqlmap.client.SqlMapClient;

@SuppressWarnings("unchecked")
@Repository
public class AreaDaoimpl implements IAreaDao {

	@Autowired
	private SqlMapClient sqlMapClient;

	@SuppressWarnings("rawtypes")
	@Override
	public ResultArea getArea(Map map) {

		List<Area> areaList = null;
		List<Area> listpro = new ArrayList<Area>();
		List<Area> listcity =new ArrayList<Area>() ;
		List<Area> listcounty = new ArrayList<Area>();
		ResultArea Rarea = new ResultArea();
		Map<String, Area> p_ids = new HashMap<String, Area>();
		try {
			
			areaList = sqlMapClient.queryForList("area.getAreaList",map);
			for(Area aaa : areaList){
				if("1".equals(aaa.getRegion_grade())){
					listpro.add(aaa);
				}else if("2".equals(aaa.getRegion_grade())){
					listcity.add(aaa);
				}else if("3".equals(aaa.getRegion_grade())){
					listcounty.add(aaa);
				}
			}
			
			for(Area area :listcity){//判断有没有第三级，如没有第三级 就把第二级放到第三级，第二级放第一级的东西
				String region_id = area.getRegion_id(); //市id
				String p_id = area.getP_region_id();//省id
				//areadd = getCountyRegionById(region_id);//通过市的id取下级地址
				boolean flag = false;
				for(Area area1 : listcounty){
					if(area1.getP_region_id().equals(region_id) ){
						flag = true;
						break;
					}
				}
				if(!flag){//没有第三级
					//把本级地址放到下级
					Area downArea = new Area();
					downArea.setBk_id(area.getBk_id());
					downArea.setLocal_name(area.getLocal_name());
					downArea.setP_region_id(area.getP_region_id());
					downArea.setRegion_id(area.getRegion_id());
					listcounty.add(downArea);//把补全的下级放到下级集合中
					
					//通过id取的上级地址 放到本级
					//areadd2 = getRegionById(p_id);
					for(Area aa :listpro){//所有省
						if(aa.getRegion_id().equals(p_id)){
							area.setBk_id(aa.getBk_id());
							area.setLocal_name(aa.getLocal_name());
							area.setP_region_id(aa.getRegion_id());
							area.setRegion_id(aa.getRegion_id());
							break;
						}
					}
					p_ids.put(p_id, area); //记录没有第三级时的最顶级id
				}
			}
			// 把市集合中的所有重复的pid==id的删除的就剩一个

			for (int j = listcity.size() - 1; j >= 0; j--) {
				Area area = listcity.get(j);
				for (String v : p_ids.keySet()) {
					if (area.getP_region_id().equalsIgnoreCase(v)
							&& area.getRegion_id().equals(v)) {
						listcity.remove(area);
					}
				}
				if (area.getRegion_id().equals("23122")) {// 把以前有的第三级的第二级删除掉
					listcity.remove(area);
				}
			}
			for (String v : p_ids.keySet()) {
				listcity.add(p_ids.get(v));
			}

			// 取的丁青 第三级 （把以前有的第三级的第二级删除掉 ） 把删除的第二级对应的三级再加到新二级上
			Area a = getRegionById("19254");
			a.setP_region_id("18892");// 新二级的p_id
			listcounty.add(a);

			Rarea.setListpro(listpro);
			Rarea.setListcity(listcity);
			Rarea.setListcounty(listcounty);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return Rarea;
	}

	@Override
	public Area getLocal_name(String local_name) {
		// TODO Auto-generated method stub
		return null;
	}

	// 返回省id
	@Override
	public Area getProIdBylocal_name(String local_name) {
		// TODO Auto-generated method stub
		Area area = null;
		try {
			area = (Area) sqlMapClient.queryForObject(
					"area.getProIdBylocal_name", local_name);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return area;
	}

	/**
	 * 返回市id
	 */
	@Override
	public Area getCityIdBylocal_name(Area area1) {
		Area area = null;
		try {
			area = (Area) sqlMapClient.queryForObject(
					"area.getCityIdBylocal_name", area1);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return area;
	}

	// 返回区id
	@Override
	public Area getCountyIdBylocal_name(Area area1) {
		Area area = null;
		try {
			area = (Area) sqlMapClient.queryForObject(
					"area.getCountyIdBylocal_name", area1);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return area;
	}

	// 根据id取的地址
	@Override
	public Area getRegionById(String region_id) {
		// TODO Auto-generated method stub
		Area area = null;
		try {
			area = (Area) sqlMapClient.queryForObject("area.getRegionById",
					region_id);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return area;
	}

	// 根据市的id取的区
	@Override
	public Area getCountyRegionById(String region_id) {
		// TODO Auto-generated method stub
		Area area = null;
		try {
			area = (Area) sqlMapClient.queryForObject(
					"area.getCountyRegionById", region_id);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return area;
	}
}
