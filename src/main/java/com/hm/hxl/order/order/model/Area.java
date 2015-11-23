package com.hm.hxl.order.order.model;

import java.io.Serializable;
/**
 * 地址
 * @author hanhangyun
 *
 */
public class Area implements Serializable {
	
	private static final long serialVersionUID = -1559857139646147485L;
	private String  region_id;  //区域序号 
	private String local_name;  //地区名称
	private String  p_region_id;  //上一级地区的序号
	private String bk_id;  //  比酷id
	
	private String region_grade;
	
	
	
	public String getRegion_grade() {
		return region_grade;
	}

	public void setRegion_grade(String region_grade) {
		this.region_grade = region_grade;
	}

	public String getRegion_id() {
		return region_id;
	}

	public void setRegion_id(String region_id) {
		this.region_id = region_id;
	}

	public String getLocal_name() {
		return local_name;
	}

	public void setLocal_name(String local_name) {
		this.local_name = local_name;
	}

	public String getP_region_id() {
		return p_region_id;
	}

	public void setP_region_id(String p_region_id) {
		this.p_region_id = p_region_id;
	}

	public String getBk_id() {
		return bk_id;
	}

	public void setBk_id(String bk_id) {
		this.bk_id = bk_id;
	}

	

	
	
	
	
	
	
	

}
