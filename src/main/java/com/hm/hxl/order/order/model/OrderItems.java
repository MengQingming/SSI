package com.hm.hxl.order.order.model;

import java.io.Serializable;

/**
 * 订单货品
 * @author hanhangyun
 */
public class OrderItems implements Serializable{
	private static final long serialVersionUID = -1387163348568156155L;
	
	/**压单状态   0:不压单  1:网站压单  2:比酷压单**/
	private String used_store;
	/**是否特卖 0:否 1:是**/
	private String is_limitbuy;
	/**订单id**/
	private String order_id;
	/**货品id**/
	private String product_id;
	/**商品id**/
	private String goods_id;
	/**订单商品id**/
	private String obj_id;
	/**货品类型 product or gift **/
	private String item_type;
	/**货品类型id**/
	private String type_id;
	/**货品编号**/
	private String bn;
	/**货品名称**/
	private String name;
	/**货品成本**/
	private double cost;
	/**货品价格**/
	private double price;
	/**货品总价格**/
	private double amount;
	/**货品购买数量**/
	private double nums;
	/**重量**/
	private double weight;
	/**积分**/
	private double score;
	/**货品原价格**/
	private double g_price;
	/**优惠价格**/
	private double pmt_price;
	/**  **/
	private int item_id;
	
	public int getItem_id() {
		return item_id;
	}
	public void setItem_id(int item_id) {
		this.item_id = item_id;
	}
	public double getPmt_price() {
		return pmt_price;
	}
	public void setPmt_price(double pmt_price) {
		this.pmt_price = pmt_price;
	}
	/*** 返回: the used_store ***/
	public String getUsed_store() {
		return used_store;
	}
	/*** 参数: used_store the used_store to set ***/
	public void setUsed_store(String used_store) {
		this.used_store = used_store;
	}
	/*** 返回: the is_limitbuy ***/
	public String getIs_limitbuy() {
		return is_limitbuy;
	}
	/*** 参数: is_limitbuy the is_limitbuy to set ***/
	public void setIs_limitbuy(String is_limitbuy) {
		this.is_limitbuy = is_limitbuy;
	}
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
	 * @return the product_id
	 */
	public String getProduct_id() {
		return product_id;
	}
	/**
	 * @param product_id the product_id to set
	 */
	public void setProduct_id(String product_id) {
		this.product_id = product_id;
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
	public String getObj_id() {
		return obj_id;
	}
	/**
	 * @param obj_id the obj_id to set
	 */
	public void setObj_id(String obj_id) {
		this.obj_id = obj_id;
	}
	/**
	 * @return the item_type
	 */
	public String getItem_type() {
		return item_type;
	}
	/**
	 * @param item_type the item_type to set
	 */
	public void setItem_type(String item_type) {
		this.item_type = item_type;
	}
	/**
	 * @return the type_id
	 */
	public String getType_id() {
		return type_id;
	}
	/**
	 * @param type_id the type_id to set
	 */
	public void setType_id(String type_id) {
		this.type_id = type_id;
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
	/**
	 * @return the cost
	 */
	public double getCost() {
		return cost;
	}
	/**
	 * @param cost the cost to set
	 */
	public void setCost(double cost) {
		this.cost = cost;
	}
	/**
	 * @return the price
	 */
	public double getPrice() {
		return price;
	}
	/**
	 * @param price the price to set
	 */
	public void setPrice(double price) {
		this.price = price;
	}
	/**
	 * @return the amount
	 */
	public double getAmount() {
		return amount;
	}
	/**
	 * @param amount the amount to set
	 */
	public void setAmount(double amount) {
		this.amount = amount;
	}
	/**
	 * @return the nums
	 */
	public double getNums() {
		return nums;
	}
	/**
	 * @param nums the nums to set
	 */
	public void setNums(double nums) {
		this.nums = nums;
	}
	/**
	 * @return the weight
	 */
	public double getWeight() {
		return weight;
	}
	/**
	 * @param weight the weight to set
	 */
	public void setWeight(double weight) {
		this.weight = weight;
	}
	/**
	 * @return the score
	 */
	public double getScore() {
		return score;
	}
	/**
	 * @param score the score to set
	 */
	public void setScore(double score) {
		this.score = score;
	}
	/**
	 * @return the g_price
	 */
	public double getG_price() {
		return g_price;
	}
	/**
	 * @param g_price the g_price to set
	 */
	public void setG_price(double g_price) {
		this.g_price = g_price;
	}

	
}
