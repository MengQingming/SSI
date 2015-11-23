package com.hm.hxl.order.order.model;

import java.io.Serializable;

/**
 * 评价列表
 * @author hanhangyun
 *
 */
public class Comment implements Serializable {

	/** 字段描述: **/
	private static final long serialVersionUID = 7073279543459354428L;
	private String url;//商品图片url
	private String name;//商品名称
	private String goods_id;//商品id
	private String order_id;//订单id
	private String product_id;//货品id
	private String is_comments;//是否已评价 1：已评价 0：未评价
	private String images; //评价图片
	
	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}
	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
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
	public String getImages() {
		return images;
	}
	public void setImages(String images) {
		this.images = images;
	}
	
	
	
}
