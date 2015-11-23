package com.hm.hxl.order.order.model;

import java.io.Serializable;

/**
 * 类名: CouponsCodes 
 * 作用: 优惠券子券
 * 作者: yanpengjie 
 * 日期: 2015-4-9 下午08:24:53 
 *
 */
public class CouponsCodes implements Serializable{
	/** 字段描述: **/
	private static final long serialVersionUID = -1289447516560852160L;
	/** 优惠券ID **/
	private String cpns_id;
	/** 优惠券号码 **/
	private String memc_cod;
	/**  **/
	private String downtime;
	
	public String getCpns_id() {
		return cpns_id;
	}
	public void setCpns_id(String cpns_id) {
		this.cpns_id = cpns_id;
	}
	public String getMemc_cod() {
		return memc_cod;
	}
	public void setMemc_cod(String memc_cod) {
		this.memc_cod = memc_cod;
	}
	public String getDowntime() {
		return downtime;
	}
	public void setDowntime(String downtime) {
		this.downtime = downtime;
	}
	
}
