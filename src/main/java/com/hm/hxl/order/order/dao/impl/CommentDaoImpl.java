package com.hm.hxl.order.order.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hm.hxl.order.order.dao.ICommentDao;
import com.hm.hxl.order.order.model.Comment;
import com.hm.hxl.order.order.model.CommentPraise;
import com.hm.hxl.order.util.Checksum;
import com.hm.hxl.order.util.SystemConfig;
import com.ibatis.sqlmap.client.SqlMapClient;

@SuppressWarnings({ "unchecked", "rawtypes" })
@Repository
public class CommentDaoImpl implements ICommentDao{

	@Autowired
	private SqlMapClient sqlMapClient;
	
	
	@Override
	public List<Comment> getCommentList(Checksum checkSum,String member_id,String order_id,Integer pageSize,Integer pageNum) throws Exception {
		Map map = new HashMap();
		map.put("member_id", member_id);
		String hxl_type = checkSum.getHxl_type();
		String webService = new SystemConfig("clientService").getProperty(hxl_type);
		map.put("picture_url",new SystemConfig(webService).getProperty("picture_url"));
		map.put("order_id", order_id);
		map.put("pageSize", pageSize);
		map.put("pageNum", pageSize*(pageNum-1));
		return sqlMapClient.queryForList("comment.getCommentList",map);
	}

	@Override
	public void saveComment(Map map) throws Exception {
		// TODO Auto-generated method stub
		sqlMapClient.insert("comment.saveComment",map);
	}

	@Override
	public Comment getCommentById(String comment_id) throws Exception {
		// TODO Auto-generated method stub
		return (Comment) sqlMapClient.queryForObject("comment.getCommentById",comment_id);
	}

	@Override
	public synchronized Integer saveCommentNew(Map map) throws Exception {
		// TODO Auto-generated method stub
		return (Integer)sqlMapClient.insert("comment.saveCommentNew", map);
	}

	
	@Override
	public synchronized void saveCommentImage(Map map) throws Exception {
		// TODO Auto-generated method stub
		sqlMapClient.insert("comment.saveCommentImage",map);
	}

	//插入记录关系表中
	@Override
	public boolean onclickPraise(CommentPraise scp) throws Exception {
		// TODO Auto-generated method stub
		sqlMapClient.insert("comment.saveCommentPraise",scp);
		return true;
	}
	//通过comment_id  member_id 验证这个用户对这条评论点过吗
	public int selectcount(CommentPraise scp)throws Exception{
		int i = (Integer) sqlMapClient.queryForObject("comment.selectcount",scp);
		return i;
	}
	//通过comment_id进行点赞
	@SuppressWarnings("unused")
	public boolean addPraise(CommentPraise scp)throws Exception{
		int  i = sqlMapClient.update("comment.addPraise",scp);
		return true;
	}

	public int selectPraise(CommentPraise scp)throws Exception{
		
		int i = (Integer) sqlMapClient.queryForObject("comment.selectPraise", scp);
		return i;
	}
	
	@Override
	public void insertImageUrl(int comment_id, String urls) throws Exception {
		// TODO Auto-generated method stub
		Map map = new HashMap();
		map.put("comment_id", comment_id);
		map.put("urls", urls);
		sqlMapClient.update("comment.insertImageUrl",map);
	}

	@Override
	public boolean isProductComment(String order_id, String product_id) throws Exception {
		// TODO Auto-generated method stub
		Map<String, String> map = new HashMap<String, String>();
		map.put("order_id",order_id);
		map.put("product_id", product_id);
		int i = (Integer) sqlMapClient.queryForObject("comment.isProductComment", map);
		if(i>0){
			return true;
		}else{
			return false;
		}
	}
}
