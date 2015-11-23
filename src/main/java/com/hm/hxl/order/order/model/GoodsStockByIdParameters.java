package com.hm.hxl.order.order.model;

import java.io.Serializable;

import com.hm.hxl.order.util.Checksum;


/**
 * 
 * 类 名: GoodsStockByIdParameters
 * <br/>描 述: 查询仓库存量参数
 * <br/>作 者: tubingbing
 * <br/>创 建： 2014-8-11
 * <br/>版 本：v1.0
 *
 * <br/>历 史:无
 */
public class GoodsStockByIdParameters implements Serializable {

	/** 字段描述: **/
	private static final long serialVersionUID = 7233200974841843077L;
	/**产品id**/
	private String product_id = "";
	/**省id**/
	private String pvc_id = "";
	/**城市id**/
	private String local_id = "";
	/**区县id**/
	private String country_id = "";
	/**付款方式id 默认1**/
	private Integer pay_id;
	/**部门id**/
	private Integer depart_id;
	
	
	/**
	 * @return the prod_id
	 */
	public String getProduct_id() {
		return product_id;
	}
	/**
	 * @param prod_id the prod_id to set
	 */
	public void setProduct_id(String product_id) {
		this.product_id = product_id;
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
	/**
	 * @return the pay_id
	 */
	public Integer getPay_id() {
		return pay_id;
	}
	/**
	 * @param pay_id the pay_id to set
	 */
	public void setPay_id(Integer pay_id) {
		this.pay_id = pay_id;
	}
	/**
	 * @return the depart_id
	 */
	public Integer getDepart_id() {
		return depart_id;
	}
	/**
	 * @param depart_id the depart_id to set
	 */
	public void setDepart_id(Integer depart_id) {
		this.depart_id = depart_id;
	}
	/**
	 * 
	 * 描 述：消息日志格式 
	 * @param checkSum
	 * @return String  
	 */
	public String toLog( Checksum checkSum ) {
		return checkSum.toLog() + "GoodsStockByIdParameters [ product_id=" + product_id + ", pvc_id=" + pvc_id
				+ ", local_id=" + local_id + ", country_id=" + country_id + "]";
	}
	@Override
	public String toString() {
		String returnStr = country_id + local_id + product_id + pvc_id;
		returnStr = returnStr.replaceAll("null", "");
		return returnStr;
	}
	
}
