package com.hm.hxl.order.order.model;

import java.io.Serializable;
/**
 * 类名: CouponsRequest 
 * 作用: 优惠券返回信息
 * 作者: yanpengjie 
 * 日期: 2015-4-10 上午11:31:57 
 */
public class CouponsRequest  implements Serializable{
	/** 字段描述: **/
	private static final long serialVersionUID = -902083061725126435L;
	/** 优惠券号码 **/
	private String memc_code;
	/** 优惠券名称 **/
	private String cpns_name;
	/** 优惠券显示金额 **/
	private String cpns_price;
	/** 满减金额 **/
	private String cpns_limit_price;
	/** 是否app独享 **/
	private String app_only;//'0','1'
	/** 优惠券类型 **/ 
	private String cpns_type;
	/** 优惠券描述 **/
	private String description;
	/** 有效期开始时间 **/
	private String from_time;
	/** 有效期结束时间 **/
	private String to_time;
	/** 优惠券类型汉字 **/
	private String cpns_type_hz;
	/** 优惠券使用标识 **/
	private String use_flag;
	/** 优惠券使用标识说明 **/
	private String use_falg_desc;
	
	public String getUse_flag() {
		return use_flag;
	}
	public void setUse_flag(String use_flag) {
		this.use_flag = use_flag;
	}
	public String getUse_falg_desc() {
		return use_falg_desc;
	}
	public void setUse_falg_desc(String use_falg_desc) {
		this.use_falg_desc = use_falg_desc;
	}
	public String getCpns_type_hz() {
		return cpns_type_hz;
	}
	public void setCpns_type_hz(String cpns_type_hz) {
		this.cpns_type_hz = cpns_type_hz;
	}
	public String getMemc_code() {
		return memc_code;
	}
	public void setMemc_code(String memc_code) {
		this.memc_code = memc_code;
	}
	public String getCpns_limit_price() {
		return cpns_limit_price;
	}
	public void setCpns_limit_price(String cpns_limit_price) {
		this.cpns_limit_price = cpns_limit_price;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getFrom_time() {
		return from_time;
	}
	public void setFrom_time(String from_time) {
		this.from_time = from_time;
	}
	public String getTo_time() {
		return to_time;
	}
	public void setTo_time(String to_time) {
		this.to_time = to_time;
	}
	public String getCpns_name() {
		return cpns_name;
	}
	public void setCpns_name(String cpns_name) {
		this.cpns_name = cpns_name;
	}
	public String getCpns_price() {
		return cpns_price;
	}
	public void setCpns_price(String cpns_price) {
		this.cpns_price = cpns_price;
	}
	public String getApp_only() {
		return app_only;
	}
	public void setApp_only(String app_only) {
		this.app_only = app_only;
	}
	public String getCpns_type() {
		return cpns_type;
	}
	public void setCpns_type(String cpns_type) {
		this.cpns_type = cpns_type;
	}
	@Override
	public String toString() {
		return "CouponsRequest [memc_code=" + memc_code + ", cpns_name="
				+ cpns_name + ", cpns_price=" + cpns_price
				+ ", cpns_limit_price=" + cpns_limit_price + ", app_only="
				+ app_only + ", cpns_type=" + cpns_type + ", description="
				+ description + ", from_time=" + from_time + ", to_time="
				+ to_time + ", cpns_type_hz=" + cpns_type_hz + "]";
	}
	
}
