package com.hm.hxl.order.order.model;

import java.io.Serializable;
import java.util.List;
/**
 * 类名: CouponsNew 
 * 作用: 优惠券 
 * 作者: yanpengjie 
 * 日期: 2015-4-8 下午12:38:14 
 *
 */
public class CouponsNew  implements Serializable{

	/** 字段描述: **/
	private static final long serialVersionUID = 8188592960053043717L;
	/**  **/
	private List<CouponsGoods> couponsGoodss;
	/** 优惠券id **/
	private String cpns_id;
	/** 优惠券名称 **/
	private String cpns_name;
	/** 优惠券显示金额 **/
	private String cpns_price;
	/** 满减金额 **/
	private String cpns_limit_price;
	/** 优惠券主号码 **/
	private String cpns_prefix;
	/** 关联商品类型 1关联指定商品 2关联商品分类 3关联全部商品 **/
	private String cpns_goods_type;
	/** 获取的总数量 **/
	private String cpns_gen_quantity;
	/** 优惠券生成的key **/
	private String cpns_key;
	/** 优惠券是否启用状态 **/
	private String cpns_status;//'0','1'
	/** 是否app独享 **/
	private String app_only;//'0','1'
	/** 优惠券类型 **/ 
	private String cpns_type;
	/** 优惠券描述 **/
	private String description;
	/** 相关的订单促销规则ID **/
	private String rule_id;
	/** 有效期开始时间 **/
	private String from_time;
	/** 有效期结束时间 **/
	private String to_time;
	/** 优惠券号码 **/
	private String memc_code;
	/** 优惠券使用标识 **/
	private String use_flag;
	/** 优惠券使用标识说明 **/
	private String use_falg_desc;
	
	public String getUse_flag() {
		return use_flag;
	}
	public void setUse_flag(String use_flag) {
		this.use_flag = use_flag;
	}
	public String getUse_falg_desc() {
		return use_falg_desc;
	}
	public void setUse_falg_desc(String use_falg_desc) {
		this.use_falg_desc = use_falg_desc;
	}
	public String getCpns_goods_type() {
		return cpns_goods_type;
	}
	public void setCpns_goods_type(String cpns_goods_type) {
		this.cpns_goods_type = cpns_goods_type;
	}
	public String getMemc_code() {
		return memc_code;
	}
	public void setMemc_code(String memc_code) {
		this.memc_code = memc_code;
	}
	public String getCpns_limit_price() {
		return cpns_limit_price;
	}
	public void setCpns_limit_price(String cpns_limit_price) {
		this.cpns_limit_price = cpns_limit_price;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public List<CouponsGoods> getCouponsGoodss() {
		return couponsGoodss;
	}
	public void setCouponsGoodss(List<CouponsGoods> couponsGoodss) {
		this.couponsGoodss = couponsGoodss;
	}
	public String getFrom_time() {
		return from_time;
	}
	public void setFrom_time(String from_time) {
		this.from_time = from_time;
	}
	public String getTo_time() {
		return to_time;
	}
	public void setTo_time(String to_time) {
		this.to_time = to_time;
	}
	public String getCpns_id() {
		return cpns_id;
	}
	public void setCpns_id(String cpns_id) {
		this.cpns_id = cpns_id;
	}
	public String getCpns_name() {
		return cpns_name;
	}
	public void setCpns_name(String cpns_name) {
		this.cpns_name = cpns_name;
	}
	public String getCpns_price() {
		return cpns_price;
	}
	public void setCpns_price(String cpns_price) {
		this.cpns_price = cpns_price;
	}
	public String getCpns_prefix() {
		return cpns_prefix;
	}
	public void setCpns_prefix(String cpns_prefix) {
		this.cpns_prefix = cpns_prefix;
	}
	public String getCpns_gen_quantity() {
		return cpns_gen_quantity;
	}
	public void setCpns_gen_quantity(String cpns_gen_quantity) {
		this.cpns_gen_quantity = cpns_gen_quantity;
	}
	public String getCpns_key() {
		return cpns_key;
	}
	public void setCpns_key(String cpns_key) {
		this.cpns_key = cpns_key;
	}
	public String getCpns_status() {
		return cpns_status;
	}
	public void setCpns_status(String cpns_status) {
		this.cpns_status = cpns_status;
	}
	public String getApp_only() {
		return app_only;
	}
	public void setApp_only(String app_only) {
		this.app_only = app_only;
	}
	public String getCpns_type() {
		return cpns_type;
	}
	public void setCpns_type(String cpns_type) {
		this.cpns_type = cpns_type;
	}
	public String getRule_id() {
		return rule_id;
	}
	public void setRule_id(String rule_id) {
		this.rule_id = rule_id;
	}
	
}
