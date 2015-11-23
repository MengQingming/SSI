package com.hm.hxl.order.order.model;

import java.io.Serializable;

/**
 * 购物车
 * @author tubingbing
 */
public class Cart  implements Serializable{
	
	/** 字段描述: **/
	private static final long serialVersionUID = -1001906115516198258L;

	/**产品id 多个已,分开**/
	private String product_id;
	
	/**商品id**/
	private String goods_id;
	
	/**用户id**/
	private String member_id;

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
	
}
