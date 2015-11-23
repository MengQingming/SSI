package com.hm.hxl.order.order.model;

import java.io.Serializable;
import java.util.List;
/**
 *  返回的省市区集合
 * @author hanhangyun
 *
 */
public class ResultArea implements Serializable {

	private static final long serialVersionUID = -1559857139646147485L;
	private String[] areaIds = new String[]{"","",""}; //  存放获取省市区id
	private String[] areaNames = new String[]{"","",""};// 获取省市区对应name
	private String[] areaBkId = new String[]{"","",""}; // 存放获取省市区对应比酷id 
	private List<Area> Listpro = null;  //省
	private List<Area> Listcity = null;  // 市
	private List<Area> Listcounty = null;  // 区
	
	
	
	
	public List<Area> getListpro() {
		return Listpro;
	}
	public void setListpro(List<Area> listpro) {
		Listpro = listpro;
	}
	public List<Area> getListcity() {
		return Listcity;
	}
	public void setListcity(List<Area> listcity) {
		Listcity = listcity;
	}
	public List<Area> getListcounty() {
		return Listcounty;
	}
	public void setListcounty(List<Area> listcounty) {
		Listcounty = listcounty;
	}
	public String[] getAreaIds() {
		return areaIds;
	}
	public void setAreaIds(String[] areaIds) {
		this.areaIds = areaIds;
	}
	public String[] getAreaNames() {
		return areaNames;
	}
	public void setAreaNames(String[] areaNames) {
		this.areaNames = areaNames;
	}
	public String[] getAreaBkId() {
		return areaBkId;
	}
	public void setAreaBkId(String[] areaBkId) {
		this.areaBkId = areaBkId;
	}
	
	

	
	
	
	
	
	
}
