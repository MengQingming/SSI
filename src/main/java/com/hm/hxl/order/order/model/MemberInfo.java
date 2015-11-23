/** 字段描述: **/
package com.hm.hxl.order.order.model;

import java.io.Serializable;

/** 
 * 类名: MemberInfo 
 * <br/>作用: 用户部分信息
 * <br/>作者: yanpengjie 
 * <br/>日期: 2015年1月12日 下午2:55:19 
 */
public class MemberInfo implements Serializable {
	
	/** 字段描述: **/
	private static final long serialVersionUID = 1015089516972173549L;
	/**用户id**/
	private String member_id;
	/**用户名**/
	private String name;
	/**用户识别码**/
	private String ident_code;
	
	/*** 返回: the member_id ***/
	public String getMember_id() {
		return member_id;
	}
	/*** 参数: member_id the member_id to set ***/
	public void setMember_id(String member_id) {
		this.member_id = member_id;
	}
	/*** 返回: the name ***/
	public String getName() {
		return name;
	}
	/*** 参数: name the name to set ***/
	public void setName(String name) {
		this.name = name;
	}
	/*** 返回: the ident_code ***/
	public String getIdent_code() {
		return ident_code;
	}
	/*** 参数: ident_code the ident_code to set ***/
	public void setIdent_code(String ident_code) {
		this.ident_code = ident_code;
	}
	
	
}
