package com.hm.hxl.order.alipay.model;

import java.io.Serializable;

public class AlipayParameter implements Serializable{
	
	private static final long serialVersionUID = -1559857139646147485L;
	
	private String order_id;
	private String member_id;
	private String hxl_desc;

	public String getHxl_desc() {
		return hxl_desc;
	}

	public void setHxl_desc(String hxl_desc) {
		this.hxl_desc = hxl_desc;
	}

	public String getMember_id() {
		return member_id;
	}

	public void setMember_id(String member_id) {
		this.member_id = member_id;
	}

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}
	

}
