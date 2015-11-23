package com.hm.hxl.order.order.model;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class OrderList implements Serializable {

	/** 字段描述: **/
	private static final long serialVersionUID = 4099395519515505938L;
	private String order_id;
	private String order_rel;
	private String status;
	private String createtime;
	private String total_amount;
	private String statusflag; // 订单状态 对应的汉字
	private String from_sto;  //  发货地 1  2  3
	private String shipping_to; // 发货地 对应的汉字
	private String timez;
	private String ship_name;
	private String payment;
	private String paymentDesc;
	private String source;
	
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getShip_name() {
		return ship_name;
	}
	public void setShip_name(String ship_name) {
		this.ship_name = ship_name;
	}
	public String getPayment() {
		return payment;
	}
	public void setPayment(String payment) {
		this.payment = payment;
	}
	public String getPaymentDesc() {
		return paymentDesc;
	}
	public void setPaymentDesc(String paymentDesc) {
		this.paymentDesc = paymentDesc;
	}
	public String getTimez() {
		return timez;
	}
	public void setTimez(String timez) {
		this.timez = timez;
	}
	public String getOrder_rel() {
		return order_rel;
	}
	public void setOrder_rel(String order_rel) {
		this.order_rel = order_rel;
	}
	private List<Map<String,String>> goodsList;
	
	/**
	 * @return the order_id
	 */
	public String getOrder_id() {
		return order_id;
	}
	/**
	 * @param order_id the order_id to set
	 */
	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}
	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * @return the createtime
	 */
	public String getCreatetime() {
		return createtime;
	}
	/**
	 * @param createtime the createtime to set
	 */
	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}
	/**
	 * @return the total_amount
	 */
	public String getTotal_amount() {
		return total_amount;
	}
	/**
	 * @param total_amount the total_amount to set
	 */
	public void setTotal_amount(String total_amount) {
		this.total_amount = total_amount;
	}
	/**
	 * @return the goodsList
	 */
	public List<Map<String, String>> getGoodsList() {
		return goodsList;
	}
	/**
	 * @param goodsList the goodsList to set
	 */
	public void setGoodsList(List<Map<String, String>> goodsList) {
		this.goodsList = goodsList;
	}
	public String getStatusflag() {
		return statusflag;
	}
	public void setStatusflag(String statusflag) {
		this.statusflag = statusflag;
	}
	public String getFrom_sto() {
		return from_sto;
	}
	public void setFrom_sto(String from_sto) {
		this.from_sto = from_sto;
	}
	public String getShipping_to() {
		return shipping_to;
	}
	public void setShipping_to(String shipping_to) {
		this.shipping_to = shipping_to;
	}
	
	
	
	
}
