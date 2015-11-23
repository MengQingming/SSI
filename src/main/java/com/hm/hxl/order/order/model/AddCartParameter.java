package com.hm.hxl.order.order.model;

import java.io.Serializable;

import com.hm.hxl.order.util.Checksum;

/**
 * 加入购物车请求参数
 * @author tubingbing
 */
public class AddCartParameter implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**产品id **/
	private String product_id;
	
	/**商品id**/
	private String goods_id;
	
	/**用户id**/
	private String member_id;
	
	/**数量**/
	private String quantity;


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
	 * @return the member_id
	 */
	public String getMember_id() {
		return member_id;
	}

	/**
	 * @param member_id the member_id to set
	 */
	public void setMember_id(String member_id) {
		this.member_id = member_id;
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
	
	
	@Override
	public String toString() {
		String returnStr = goods_id+member_id+product_id+quantity;
		return returnStr.replaceAll("null", "");
	}
	
	public String toLog(Checksum checkSum){
		return "SendParameters:[member_id:"+member_id+",product_id:"+product_id 
			+",goods_id："+goods_id+",quantity："+quantity+"]:"+checkSum.toLog();
	}

	
}
