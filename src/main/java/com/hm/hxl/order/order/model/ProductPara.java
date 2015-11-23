package com.hm.hxl.order.order.model;

import java.io.Serializable;

import com.hm.hxl.order.util.Checksum;

public class ProductPara implements  Serializable {
	
	private static final long serialVersionUID = -1559857139646147485L;
	
	private String  product_id;
	private String member_id;
	/**省id**/
	private String pvc_id ;
	/**城市id**/
	private String local_id ;
	/**区县id**/
	private String country_id ;
	
	
	public String getPvc_id() {
		return pvc_id;
	}

	public void setPvc_id(String pvc_id) {
		this.pvc_id = pvc_id;
	}

	public String getLocal_id() {
		return local_id;
	}

	public void setLocal_id(String local_id) {
		this.local_id = local_id;
	}

	public String getCountry_id() {
		return country_id;
	}

	public void setCountry_id(String country_id) {
		this.country_id = country_id;
	}

	public String getMember_id() {
		return member_id;
	}

	public void setMember_id(String member_id) {
		this.member_id = member_id;
	}

	public String getProduct_id() {
		return product_id;
	}

	public void setProduct_id(String product_id) {
		this.product_id = product_id;
	}
	
	@Override
	public String toString() {
		String returnStr =member_id+product_id+"";
		return returnStr.replaceAll("null", "");
	}
	
	public String toLog(Checksum checkSum){
		return "SendParameters:[member_id=" + member_id + ",product_id:"+product_id+"]:"+checkSum.toLog();
	}
	
	
	
}
	
