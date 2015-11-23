package com.hm.hxl.order.order.model;

import java.io.Serializable;

import com.hm.hxl.order.util.Checksum;
/**
 * 编辑购物车请求参数
 * @author tubingbing
 */
public class EditCartParameter implements Serializable{

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
	
	/**省id**/
	private String pvc_id ;
	/**城市id**/
	private String local_id ;
	/**区县id**/
	private String country_id ;


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
	
	/**
	 * @return the pvc_id
	 */
	public String getPvc_id() {
		return pvc_id;
	}

	/**
	 * @param pvc_id the pvc_id to set
	 */
	public void setPvc_id(String pvc_id) {
		this.pvc_id = pvc_id;
	}

	/**
	 * @return the local_id
	 */
	public String getLocal_id() {
		return local_id;
	}

	/**
	 * @param local_id the local_id to set
	 */
	public void setLocal_id(String local_id) {
		this.local_id = local_id;
	}

	/**
	 * @return the country_id
	 */
	public String getCountry_id() {
		return country_id;
	}

	/**
	 * @param country_id the country_id to set
	 */
	public void setCountry_id(String country_id) {
		this.country_id = country_id;
	}
	
	@Override
	public String toString() {
		String returnStr = country_id+goods_id+local_id+member_id+product_id+pvc_id+quantity;
		return returnStr.replaceAll("null", "");
	}
	
	public String toLog(Checksum checkSum){
		return "SendParameters:[member_id:"+member_id+",product_id:"+product_id 
			+",goods_id："+goods_id+",quantity："+quantity+",pvc_id："+pvc_id+",local_id："+local_id+",country_id："+country_id+"]:"+checkSum.toLog();
	}

	
}
