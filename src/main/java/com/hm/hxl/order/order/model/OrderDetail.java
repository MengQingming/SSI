package com.hm.hxl.order.order.model;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
/**
 * 类名: OrderDetail 
 * 作用: 订单详情
 * 作者: yanpengjie 
 * 日期: 2015-7-7 下午1:54:44 
 * 版本: V 1.0
 *
 */
public class OrderDetail implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1870105531983149925L;
	private String order_id;
	private String order_rel;
	private String payment; //支付方式  在线:alipay  货到付: offline
 	private String total_amount; //商品默认货币总值
 	private String status;  //订单状态 active:活动订单;dead:取消;finish:已完成;  写的是对应的汉字
 	private String createtime;//下单时间
 	private String ship_area; //收货地区  18870,18904,22881
 	private String ship_name; //v收货人
 	private String ship_addr; //收货地址
 	private String ship_mobile;//收货人手机
 	private String cost_item;//订单商品总价格
 	private String pmt_order;//订单促销优惠   =wipe_price+pmt_order
 	private String cost_freight;//配送费用
 	private String is_tax;//是否要开发票 enum('true','false')
 	private String tax_type;//发票类型 false:不需发票;personal:个人发票;company:公司发票
 	private String tax_company;//发票内容 
 	private String quantity;//数量 商品对象购买量
 	
 	private String paymentTohanzi ; //支付方式换汉字
 	private String pay_status;//付款状态 0:未支付;1:已支付;2:已付款至到担保方;3:部分付款;4:部分退款;5:全额退款
 	private String statusflag; //订单状态 active:活动订单;dead:取消;finish:已完成;  只是这三个状态
 	private String ship_status;//发货状态 0:未发货;1:已发货;2:部分发货;3:部分退货;4:已退货
 	private List<Map<String,String>> goodsList;//
 	
 	
 	
 	
 	public String getPaymentTohanzi() {
		return paymentTohanzi;
	}
	public void setPaymentTohanzi(String paymentTohanzi) {
		this.paymentTohanzi = paymentTohanzi;
	}
	public String getShip_status() {
		return ship_status;
	}
	public void setShip_status(String ship_status) {
		this.ship_status = ship_status;
	}
	public String getOrder_rel() {
		return order_rel;
	}
	public void setOrder_rel(String order_rel) {
		this.order_rel = order_rel;
	}
	/**是否已评价 1：已评价 0：未评价**/
 	private String is_comments;
 	
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
	 * @return the ship_area
	 */
	public String getShip_area() {
		return ship_area;
	}
	/**
	 * @param ship_area the ship_area to set
	 */
	public void setShip_area(String ship_area) {
		this.ship_area = ship_area;
	}
	/**
	 * @return the ship_name
	 */
	public String getShip_name() {
		return ship_name;
	}
	/**
	 * @param ship_name the ship_name to set
	 */
	public void setShip_name(String ship_name) {
		this.ship_name = ship_name;
	}
	/**
	 * @return the ship_addr
	 */
	public String getShip_addr() {
		return ship_addr;
	}
	/**
	 * @param ship_addr the ship_addr to set
	 */
	public void setShip_addr(String ship_addr) {
		this.ship_addr = ship_addr;
	}
	/**
	 * @return the ship_mobile
	 */
	public String getShip_mobile() {
		return ship_mobile;
	}
	/**
	 * @param ship_mobile the ship_mobile to set
	 */
	public void setShip_mobile(String ship_mobile) {
		this.ship_mobile = ship_mobile;
	}
	/**
	 * @return the cost_item
	 */
	public String getCost_item() {
		return cost_item;
	}
	/**
	 * @param cost_item the cost_item to set
	 */
	public void setCost_item(String cost_item) {
		this.cost_item = cost_item;
	}
	/**
	 * @return the pmt_order
	 */
	public String getPmt_order() {
		return pmt_order;
	}
	/**
	 * @param pmt_order the pmt_order to set
	 */
	public void setPmt_order(String pmt_order) {
		this.pmt_order = pmt_order;
	}
	/**
	 * @return the cost_freight
	 */
	public String getCost_freight() {
		return cost_freight;
	}
	/**
	 * @param cost_freight the cost_freight to set
	 */
	public void setCost_freight(String cost_freight) {
		this.cost_freight = cost_freight;
	}
	/**
	 * @return the is_tax
	 */
	public String getIs_tax() {
		return is_tax;
	}
	/**
	 * @param is_tax the is_tax to set
	 */
	public void setIs_tax(String is_tax) {
		this.is_tax = is_tax;
	}
	/**
	 * @return the tax_type
	 */
	public String getTax_type() {
		return tax_type;
	}
	/**
	 * @param tax_type the tax_type to set
	 */
	public void setTax_type(String tax_type) {
		this.tax_type = tax_type;
	}
	/**
	 * @return the tax_company
	 */
	public String getTax_company() {
		return tax_company;
	}
	/**
	 * @param tax_company the tax_company to set
	 */
	public void setTax_company(String tax_company) {
		this.tax_company = tax_company;
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
	/**
	 * @return the quantity
	 */
	public String getQuantity() {
		return quantity;
	}
	/**
	 * @param quantity the quantity to set
	 */
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
	/**
	 * @return the payment
	 */
	public String getPayment() {
		return payment;
	}
	/**
	 * @param payment the payment to set
	 */
	public void setPayment(String payment) {
		this.payment = payment;
	}
	/**
	 * @return the is_comments
	 */
	public String getIs_comments() {
		return is_comments;
	}
	/**
	 * @param is_comments the is_comments to set
	 */
	public void setIs_comments(String is_comments) {
		this.is_comments = is_comments;
	}
	public String getPay_status() {
		return pay_status;
	}
	public void setPay_status(String pay_status) {
		this.pay_status = pay_status;
	}
	public String getStatusflag() {
		return statusflag;
	}
	public void setStatusflag(String statusflag) {
		this.statusflag = statusflag;
	}
 	
	
	
}
