package com.hm.hxl.order.order.model;

import java.io.Serializable;
/**
 * 类名: CouponsGoods 
 * 作用: 优惠券商品关联
 * 作者: yanpengjie 
 * 日期: 2015-4-8 下午12:38:43 
 *
 */
public class CouponsGoods  implements Serializable{

	/** 字段描述: **/
	private static final long serialVersionUID = -6920440900590947476L;
	
	private String id;
	/** 优惠券ID **/
	private String cpns_id;
	/** 商品ID **/
	private String goods_id;
	/** 货品ID **/
	private String product_id;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCpns_id() {
		return cpns_id;
	}
	public void setCpns_id(String cpns_id) {
		this.cpns_id = cpns_id;
	}
	public String getGoods_id() {
		return goods_id;
	}
	public void setGoods_id(String goods_id) {
		this.goods_id = goods_id;
	}
	public String getProduct_id() {
		return product_id;
	}
	public void setProduct_id(String product_id) {
		this.product_id = product_id;
	}
	
}
