package com.hm.hxl.order.order.model;

import java.io.Serializable;


//取消商品时返回库存
public class CancelOrderItemsInventory implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4371748760826959662L;
	//取消订单时用
	private String is_limitbuy;   //('0','1')  是否特卖 0:否 1:是  
	private String used_store;    //('0','1','2')  压单状态   0:不压单  1:网站压单  2:比酷压单
	private String is_overseas;   //发货地  1大货   2保税  3海外      大陆，保税的商品比酷和本地都锁库存了             海外的只在本地锁库存
	private String nums;    //商品购买量
	private String product_id  ;
	private String order_id ;
	private String bk_no_id ; //比酷订单id
	
	
	
	
	
	
	public String getBk_no_id() {
		return bk_no_id;
	}
	public void setBk_no_id(String bk_no_id) {
		this.bk_no_id = bk_no_id;
	}
	public String getOrder_id() {
		return order_id;
	}
	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}
	public String getProduct_id() {
		return product_id;
	}
	public void setProduct_id(String product_id) {
		this.product_id = product_id;
	}
	public String getIs_limitbuy() {
		return is_limitbuy;
	}
	public void setIs_limitbuy(String is_limitbuy) {
		this.is_limitbuy = is_limitbuy;
	}
	public String getUsed_store() {
		return used_store;
	}
	public void setUsed_store(String used_store) {
		this.used_store = used_store;
	}
	public String getIs_overseas() {
		return is_overseas;
	}
	public void setIs_overseas(String is_overseas) {
		this.is_overseas = is_overseas;
	}
	public String getNums() {
		return nums;
	}
	public void setNums(String nums) {
		this.nums = nums;
	}
	
	
	
	
	
	

}
