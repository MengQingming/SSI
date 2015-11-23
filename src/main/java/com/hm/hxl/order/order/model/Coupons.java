package com.hm.hxl.order.order.model;

import java.io.Serializable;

public class Coupons implements Serializable {

	/** 字段描述: **/
	private static final long serialVersionUID = 309313275471704536L;
	private String cpns_id;
	private String member_id;
	private String memc_code;
	private String cpns_name;
	private String cpns_price;
	private String description;
	private String from_time;
	private String to_time;
	
	private String cpns_use_rule;
	private String app_only;//是否是app独享
	
	/**
	 * @return the cpns_id
	 */
	public String getCpns_id() {
		return cpns_id;
	}
	/**
	 * @param cpns_id the cpns_id to set
	 */
	public void setCpns_id(String cpns_id) {
		this.cpns_id = cpns_id;
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
	 * @return the memc_code
	 */
	public String getMemc_code() {
		return memc_code;
	}
	/**
	 * @param memc_code the memc_code to set
	 */
	public void setMemc_code(String memc_code) {
		this.memc_code = memc_code;
	}
	/**
	 * @return the cpns_name
	 */
	public String getCpns_name() {
		return cpns_name;
	}
	/**
	 * @param cpns_name the cpns_name to set
	 */
	public void setCpns_name(String cpns_name) {
		this.cpns_name = cpns_name;
	}
	/**
	 * @return the cpns_price
	 */
	public String getCpns_price() {
		return cpns_price;
	}
	/**
	 * @param cpns_price the cpns_price to set
	 */
	public void setCpns_price(String cpns_price) {
		this.cpns_price = cpns_price;
	}
	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * @return the from_time
	 */
	public String getFrom_time() {
		return from_time;
	}
	/**
	 * @param from_time the from_time to set
	 */
	public void setFrom_time(String from_time) {
		this.from_time = from_time;
	}
	/**
	 * @return the to_time
	 */
	public String getTo_time() {
		return to_time;
	}
	/**
	 * @param to_time the to_time to set
	 */
	public void setTo_time(String to_time) {
		this.to_time = to_time;
	}
	/**
	 * @return the cpns_use_rule
	 */
	public String getCpns_use_rule() {
		return cpns_use_rule;
	}
	/**
	 * @param cpns_use_rule the cpns_use_rule to set
	 */
	public void setCpns_use_rule(String cpns_use_rule) {
		this.cpns_use_rule = cpns_use_rule;
	}
	/**
	 * @return the app_only
	 */
	public String getApp_only() {
		return app_only;
	}
	/**
	 * @param app_only the app_only to set
	 */
	public void setApp_only(String app_only) {
		this.app_only = app_only;
	}
	
}
