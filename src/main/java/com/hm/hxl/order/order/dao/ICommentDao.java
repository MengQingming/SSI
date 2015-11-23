package com.hm.hxl.order.order.dao;

import java.util.List;
import java.util.Map;

import com.hm.hxl.order.order.model.Comment;
import com.hm.hxl.order.order.model.CommentPraise;
import com.hm.hxl.order.util.Checksum;
@SuppressWarnings("rawtypes")
public interface ICommentDao {

	public List<Comment> getCommentList(Checksum checkSum,String member_id,String order_id,Integer pageSize,Integer pageNum) throws Exception;
	
	public void saveComment(Map map)throws Exception;
	//保存并返回当前id
	
	public Integer saveCommentNew(Map map)throws Exception;
	
	public Comment getCommentById(String comment_id) throws Exception;
	
	public void saveCommentImage(Map map)throws Exception;
	//点赞评价 为关系表插入记录
	public boolean onclickPraise(CommentPraise scp)throws Exception;
	
	//通过comment_id  member_id 验证这个用户对这条评论点过吗
	public int selectcount(CommentPraise scp)throws Exception;

	//通过comment_id进行点赞
	public boolean addPraise(CommentPraise scp)throws Exception;
	
	public void insertImageUrl(int comment_id, String urls)throws Exception;
	//返回点赞总数
	public int selectPraise(CommentPraise scp)throws Exception;

	//订单中商品是否评价
	public boolean isProductComment(String order_id, String product_id)throws Exception;
}
