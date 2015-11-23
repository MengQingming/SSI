package com.hm.hxl.order.unionpay.model;

import java.io.Serializable;

public class UnionpayResult implements Serializable {
		
	private static final long serialVersionUID = 7470930932996106083L;
	
	private String order_id;
	private String total_amount;
	private String tn;
	public String getOrder_id() {
		return order_id;
	}
	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}
 
	public String getTn() {
		return tn;
	}
	public void setTn(String tn) {
		this.tn = tn;
	}
	public String getTotal_amount() {
		return total_amount;
	}
	public void setTotal_amount(String total_amount) {
		this.total_amount = total_amount;
	}

}
