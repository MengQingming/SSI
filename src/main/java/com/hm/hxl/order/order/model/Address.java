package com.hm.hxl.order.order.model;

import java.io.Serializable;

/**
 * 收货地址
 * @author hanhangyun
 */
public class Address implements Serializable{
	private static final long serialVersionUID = -2605954125752508473L;
	
	private String addr_id; // 地址id   不允许为空
	private String member_id; //用户id   不允许为空
	private String name;   //收件人姓名
	private String area;   //省市区的 id
	private String[] areaNames = new String[]{"","",""} ;//省市区的 名称 供前台显示用
	private String[] areaBk_ids;
	private String addr;   //收货人地址
	private String zip;    // 邮编
	private String mobile; //手机
	
	private String tel;    // 电话
	private String gender; //性别     0女   1男 
	private String day;    //送货日期
	
	private String time;  //送货时间 
	private String def_addr;  // 1为默认地址       0为非默认
	private String ident_code;//身份证号码
	private String ident_code_front;//正面
	private String ident_code_opposite;//反面
	
	public String getIdent_code() {
		return ident_code;
	}

	public void setIdent_code(String ident_code) {
		this.ident_code = ident_code;
	}

	public String getIdent_code_front() {
		return ident_code_front;
	}

	public void setIdent_code_front(String ident_code_front) {
		this.ident_code_front = ident_code_front;
	}

	public String getIdent_code_opposite() {
		return ident_code_opposite;
	}

	public void setIdent_code_opposite(String ident_code_opposite) {
		this.ident_code_opposite = ident_code_opposite;
	}

	
	@Override
	public String toString() {
		String returnStr = addr+addr_id+area+day+def_addr+gender+member_id+mobile+name+tel+time+zip;
		return returnStr.replaceAll("null", "");
	}

	
	/**
	 * @return the addr_id
	 */
	public String getAddr_id() {
		return addr_id;
	}

	/**
	 * @param addr_id the addr_id to set
	 */
	public void setAddr_id(String addr_id) {
		this.addr_id = addr_id;
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
	 * @return the area
	 */
	public String getArea() {
		return area;
	}

	/**
	 * @param area the area to set
	 */
	public void setArea(String area) {
		this.area = area;
	}

	/**
	 * @return the addr
	 */
	public String getAddr() {
		return addr;
	}

	/**
	 * @param addr the addr to set
	 */
	public void setAddr(String addr) {
		this.addr = addr;
	}

	/**
	 * @return the zip
	 */
	public String getZip() {
		return zip;
	}

	/**
	 * @param zip the zip to set
	 */
	public void setZip(String zip) {
		this.zip = zip;
	}

	/**
	 * @return the mobile
	 */
	public String getMobile() {
		return mobile;
	}

	/**
	 * @param mobile the mobile to set
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	/**
	 * @return the tel
	 */
	public String getTel() {
		return tel;
	}

	/**
	 * @param tel the tel to set
	 */
	public void setTel(String tel) {
		this.tel = tel;
	}

	/**
	 * @return the gender
	 */
	public String getGender() {
		return gender;
	}

	/**
	 * @param gender the gender to set
	 */
	public void setGender(String gender) {
		this.gender = gender;
	}

	/**
	 * @return the day
	 */
	public String getDay() {
		return day;
	}

	/**
	 * @param day the day to set
	 */
	public void setDay(String day) {
		this.day = day;
	}

	/**
	 * @return the time
	 */
	public String getTime() {
		return time;
	}

	/**
	 * @param time the time to set
	 */
	public void setTime(String time) {
		this.time = time;
	}

	/**
	 * @return the def_addr
	 */
	public String getDef_addr() {
		return def_addr;
	}

	/**
	 * @param def_addr the def_addr to set
	 */
	public void setDef_addr(String def_addr) {
		this.def_addr = def_addr;
	}

	public String[] getAreaNames() {
		return areaNames;
	}

	public void setAreaNames(String[] areaNames) {
		this.areaNames = areaNames;
	}

	public String[] getAreaBk_ids() {
		return areaBk_ids;
	}

	public void setAreaBk_ids(String[] areaBk_ids) {
		this.areaBk_ids = areaBk_ids;
	}

	
	
	
}
