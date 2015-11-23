/** 字段描述: **/
package com.hm.hxl.order.order.model;

import java.io.Serializable;

/** 
 * 类名: PromptMessage 
 * <br/>作用: 提示信息 
 * <br/>作者: yanpengjie 
 * <br/>日期: 2015年1月12日 下午9:51:58 
 */
public class PromptMessage  implements Serializable{
	/** 字段描述: **/
	private static final long serialVersionUID = -1283444306032768285L;
	/** 标题 **/
	private String title;
	/** 内容 **/
	private String content;

	/*** 返回: the title ***/
	public String getTitle() {
		return title;
	}
	/*** 参数: title the title to set ***/
	public void setTitle(String title) {
		this.title = title;
	}
	/*** 返回: the content ***/
	public String getContent() {
		return content;
	}
	/*** 参数: content the content to set ***/
	public void setContent(String content) {
		this.content = content;
	}
	
	
}
