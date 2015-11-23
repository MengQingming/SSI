package com.hm.hxl.order.order.model;

import java.io.Serializable;
import java.math.BigDecimal;
/**
 * 类名: Couponlog4User 
 * 作用: 优惠券使用记录
 * 作者: yanpengjie 
 * 日期: 2015-4-8 上午11:46:57 
 *
 */
public class Couponlog4User  implements Serializable{

	/** 字段描述: **/
	private static final long serialVersionUID = -7789873577460080207L;
	/**  **/
	private String id;
	/** 订单ID **/
	private String order_id;
	/** 优惠券ID **/
	private String cpns_id;
	/** 优惠券名称 **/
	private String cpns_name;
	/** 使用时间 **/
	private Long usetime;
	/** 总金额 **/
	private BigDecimal total_amount;
	/** 用户id **/
	private String member_id;
	/** 优惠券号码 **/
	private String memc_code;
	/** 优惠券类型 0 一张无限使用 ,1 多张使用一次 **/ 
	private String cpns_type;
	
	public Long getUsetime() {
		return usetime;
	}
	public void setUsetime(Long usetime) {
		this.usetime = usetime;
	}
	public BigDecimal getTotal_amount() {
		return total_amount;
	}
	public void setTotal_amount(BigDecimal total_amount) {
		this.total_amount = total_amount;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getOrder_id() {
		return order_id;
	}
	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}
	public String getCpns_id() {
		return cpns_id;
	}
	public void setCpns_id(String cpns_id) {
		this.cpns_id = cpns_id;
	}
	public String getCpns_name() {
		return cpns_name;
	}
	public void setCpns_name(String cpns_name) {
		this.cpns_name = cpns_name;
	}
	public String getMember_id() {
		return member_id;
	}
	public void setMember_id(String member_id) {
		this.member_id = member_id;
	}
	public String getMemc_code() {
		return memc_code;
	}
	public void setMemc_code(String memc_code) {
		this.memc_code = memc_code;
	}
	public String getCpns_type() {
		return cpns_type;
	}
	public void setCpns_type(String cpns_type) {
		this.cpns_type = cpns_type;
	}
}
