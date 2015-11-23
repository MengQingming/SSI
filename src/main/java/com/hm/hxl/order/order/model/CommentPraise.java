package com.hm.hxl.order.order.model;

import java.io.Serializable;

/**
 * 评价点赞
 * @author hanhangyun
 *
 */
public class CommentPraise implements Serializable {

	/** 字段描述: **/
	private static final long serialVersionUID = 8553370897269934808L;
	private String praise_id;
	private String comment_id;//评价id
	private String user_id;//用户id
	
	
	 
	public String getPraise_id() {
		return praise_id;
	}

	public void setPraise_id(String praise_id) {
		this.praise_id = praise_id;
	}

	public String getComment_id() {
		return comment_id;
	}

	public void setComment_id(String comment_id) {
		this.comment_id = comment_id;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toLog() {
		return "CommentPraise [praise_id=" + praise_id + ", comment_id="
				+ comment_id + ", user_id=" + user_id 
				+ "]";
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		String str = comment_id + praise_id + user_id ;
		return str.replaceAll("null", "");
	}

	
	
}
