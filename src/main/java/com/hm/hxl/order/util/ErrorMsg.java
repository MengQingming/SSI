package com.hm.hxl.order.util;

import java.io.Serializable;
import java.util.Date;

public class ErrorMsg implements Serializable{

	private static final long serialVersionUID = 4068398901918752984L;
	
	private String pk_id;		//主键id
	private String ep_data;		//exception数据
	private String ep_result;	//exception结果
	private Date ep_time;		//异常时间
	private String ep_type;		//ep类型
	public String getPk_id() {
		return pk_id;
	}
	public void setPk_id(String pk_id) {
		this.pk_id = pk_id;
	}
	public String getEp_data() {
		return ep_data;
	}
	public void setEp_data(String ep_data) {
		this.ep_data = ep_data;
	}
	public String getEp_result() {
		return ep_result;
	}
	public void setEp_result(String ep_result) {
		this.ep_result = ep_result;
	}
	public Date getEp_time() {
		return ep_time;
	}
	public void setEp_time(Date ep_time) {
		this.ep_time = ep_time;
	}
	public String getEp_type() {
		return ep_type;
	}
	public void setEp_type(String ep_type) {
		this.ep_type = ep_type;
	}
	
	
}
