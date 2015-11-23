package com.hm.hxl.order.order.model;

import java.io.Serializable;

/**
 * 订单商品
 * @author hanhangyun
 */
public class OrderObjects implements Serializable{
	
	private static final long serialVersionUID = -1387163348568156155L;
	
	/**订单id**/
	private String order_id;
	/**商品id**/
	private String goods_id;
	/**订单商品id**/
	private int obj_id;
	/**订单商品类型 goods or gift **/
	private String obj_type;
	/**订单商品类型描述**/
	private String obj_alias;
	/**商品编号**/
	private String bn;
	/**商品名称**/
	private String name;
	/**商品价格**/
	private double price;
	/**商品总价格**/
	private double amount;
	/**商品购买数量**/
	private double quantity;
	/**商品总重量**/
	private double weight;
	/**商品总积分**/
	private double score;
	
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
	 * @return the goods_id
	 */
	public String getGoods_id() {
		return goods_id;
	}
	/**
	 * @param goods_id the goods_id to set
	 */
	public void setGoods_id(String goods_id) {
		this.goods_id = goods_id;
	}
	/**
	 * @return the obj_id
	 */
	public int getObj_id() {
		return obj_id;
	}
	/**
	 * @param obj_id the obj_id to set
	 */
	public void setObj_id(int obj_id) {
		this.obj_id = obj_id;
	}
	/**
	 * @return the obj_type
	 */
	public String getObj_type() {
		return obj_type;
	}
	/**
	 * @param obj_type the obj_type to set
	 */
	public void setObj_type(String obj_type) {
		this.obj_type = obj_type;
	}
	/**
	 * @return the obj_alias
	 */
	public String getObj_alias() {
		return obj_alias;
	}
	/**
	 * @param obj_alias the obj_alias to set
	 */
	public void setObj_alias(String obj_alias) {
		this.obj_alias = obj_alias;
	}
	/**
	 * @return the bn
	 */
	public String getBn() {
		return bn;
	}
	/**
	 * @param bn the bn to set
	 */
	public void setBn(String bn) {
		this.bn = bn;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/*** 返回: the price ***/
	public double getPrice() {
		return price;
	}
	/*** 参数: price the price to set ***/
	public void setPrice(double price) {
		this.price = price;
	}
	/*** 返回: the amount ***/
	public double getAmount() {
		return amount;
	}
	/*** 参数: amount the amount to set ***/
	public void setAmount(double amount) {
		this.amount = amount;
	}
	/*** 返回: the quantity ***/
	public double getQuantity() {
		return quantity;
	}
	/*** 参数: quantity the quantity to set ***/
	public void setQuantity(double quantity) {
		this.quantity = quantity;
	}
	/*** 返回: the weight ***/
	public double getWeight() {
		return weight;
	}
	/*** 参数: weight the weight to set ***/
	public void setWeight(double weight) {
		this.weight = weight;
	}
	/*** 返回: the score ***/
	public double getScore() {
		return score;
	}
	/*** 参数: score the score to set ***/
	public void setScore(double score) {
		this.score = score;
	}
	
}
