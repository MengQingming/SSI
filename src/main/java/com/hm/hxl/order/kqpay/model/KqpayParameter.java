package com.hm.hxl.order.kqpay.model;

import java.io.Serializable;

public class KqpayParameter implements Serializable {
	private static final long serialVersionUID = -6602107590073177885L;
	private String order_id;
	private String member_id;

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
