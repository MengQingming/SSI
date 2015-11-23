package com.hm.hxl.order.order.model;

import java.io.Serializable;
/**
 * 类名: CouponsType 
 * 作用: 优惠券类型
 * 作者: yanpengjie 
 * 日期: 2015-4-8 下午12:38:01 
 *
 */
public class CouponsType implements Serializable{

	/** 字段描述: **/
	private static final long serialVersionUID = 3873261875358666887L;
	/** 优惠券类型id **/
	private String type_id;
	/** 优惠券类型名称 **/
	private String type_name;
	/** 优惠券类型 1代金券,2满减券 **/
	private String type_used;//'1','2'
	/** 商品限制张数 **/
	private String goods_limit;
	/** 订单限制张数 **/
	private String order_limit;
	
	public String getType_id() {
		return type_id;
	}
	public void setType_id(String type_id) {
		this.type_id = type_id;
	}
	public String getType_name() {
		return type_name;
	}
	public void setType_name(String type_name) {
		this.type_name = type_name;
	}
	public String getType_used() {
		return type_used;
	}
	public void setType_used(String type_used) {
		this.type_used = type_used;
	}
	public String getGoods_limit() {
		return goods_limit;
	}
	public void setGoods_limit(String goods_limit) {
		this.goods_limit = goods_limit;
	}
	public String getOrder_limit() {
		return order_limit;
	}
	public void setOrder_limit(String order_limit) {
		this.order_limit = order_limit;
	}
}
