package com.hm.hxl.order.alipay.model;

import java.io.Serializable;

public class AlipayResult implements Serializable{
	
	private static final long serialVersionUID = -1559857139646147485L;
	
	
	private String order_id;
	private String total_amount;
	private String sign;
	public String getOrder_id() {
		return order_id;
	}
	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}
	
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
 
	public String getTotal_amount() {
		return total_amount;
	}
	public void setTotal_amount(String total_amount) {
		this.total_amount = total_amount;
	}
	@Override
	public String toString() {
		return "AlipayResult [order_id=" + order_id + ", total_amount="
				+ total_amount + ", sign=" + sign + "]";
	}



}
