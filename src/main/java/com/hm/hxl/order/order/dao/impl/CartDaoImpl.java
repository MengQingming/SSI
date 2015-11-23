package com.hm.hxl.order.order.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hm.hxl.order.order.dao.ICartDao;
import com.hm.hxl.order.order.model.Address;
import com.hm.hxl.order.order.model.Cart;
import com.hm.hxl.order.order.model.CartGoods;
import com.hm.hxl.order.order.model.MemberInfo;
import com.hm.hxl.order.order.model.PromptMessage;
import com.ibatis.sqlmap.client.SqlMapClient;

/**
 * 购物车
 *@author tubingbing
 */
@SuppressWarnings({"unchecked","rawtypes"})
@Repository
public class CartDaoImpl implements ICartDao{

	@Autowired
	private SqlMapClient sqlMapClient;

	@Override
	public List<CartGoods> getCartGoodsByMemberId(Cart cart) throws Exception {
		return sqlMapClient.queryForList("cart.getCartGoodsByMemberId",cart);
	}
	
	@Override
	public List<CartGoods> getCartGoodsByProductId(Cart cart) throws Exception {
		return sqlMapClient.queryForList("cart.getCartGoodsByProductId",cart);
	}
	
	@Override
	public List<CartGoods> getCartGoods(Cart cart) throws Exception {
		return sqlMapClient.queryForList("cart.getCartGoods",cart);
	}
	/**
	 * 获取购物车商品总数
	 */
	@Override
	public int getCartNumbers(Cart cart) throws Exception {
		return (Integer) sqlMapClient.queryForObject("cart.getCartNumbers",cart);
	}

	@Override
	public void addCart(Map map) throws Exception {
		sqlMapClient.insert("cart.addCart", map);
	}

	@Override
	public void updateCart(Map map) throws Exception {
		// TODO Auto-generated method stub
		sqlMapClient.insert("cart.updateCart", map);
	}

	@Override
	public boolean isCart(Map map) throws Exception {
		// TODO Auto-generated method stub
		int num = (Integer) sqlMapClient.queryForObject("cart.isCart",map);
		if(num>=1){
			return true;
		}
		return false;
	}

	@Override
	public void deleteCart(Map map) throws Exception {
		// TODO Auto-generated method stub
		sqlMapClient.delete("cart.deleteCart", map);
	}

	@Override
	public void editCart(Map map) throws Exception {
		sqlMapClient.update("cart.editCart", map);
	}

	@Override
	public Address getDefaultAddrs(Cart cart) throws Exception {
		return (Address) sqlMapClient.queryForObject("cart.getDefaultAddrs", cart);
	}

	@Override
	public void updateCartIsSelect(Cart ap) throws Exception {
		// TODO Auto-generated method stub
		sqlMapClient.update("cart.updateCartIsSelect",ap);
	}

	@Override
	public void updateCartIsSelectTrue(Cart ap) throws Exception {
		// TODO Auto-generated method stub
		sqlMapClient.update("cart.updateCartIsSelectTrue",ap);
	}

	@Override
	public void deleteCartCoupons(Map map) throws Exception {
		// TODO Auto-generated method stub
		sqlMapClient.delete("cart.deleteCartCoupons",map);
	}

	@Override
	public String getCartCouponsByMemberId(String member_id) throws Exception {
		// TODO Auto-generated method stub
		return (String) sqlMapClient.queryForObject("cart.getCartCouponsByMemberId", member_id);
	}

	@Override
	public List<String> getAreaTypeByfrom_stos(List<String> from_stos) throws Exception  {
		// TODO Auto-generated method stub
		return sqlMapClient.queryForList("cart.getAreaTypeByfrom_stos",from_stos);
	}

	@Override
	public PromptMessage getPromptMessageByAreaType(String areaType) throws Exception {
		// TODO Auto-generated method stub
		return (PromptMessage) sqlMapClient.queryForObject("cart.getPromptMessageByAreaType", areaType);
	}

	@Override
	public List<String> getNotOverseasAreaID()  throws Exception {
		// TODO Auto-generated method stub
		return sqlMapClient.queryForList("cart.getNotOverseasAreaID");
	}

	@Override
	public MemberInfo getMemberInfoByMemberId(String member_id)throws Exception {
		// TODO Auto-generated method stub
		return (MemberInfo) sqlMapClient.queryForObject("cart.getMemberInfoByMemberId", member_id);
	}

	@Override
	public void updateLimitbuy_us_quantityByProductId_add(Map map) throws Exception {
		sqlMapClient.update("cart.updateLimitbuy_us_quantityByProductId_add",map);
	}

	@Override
	public void updateLimitbuy_us_quantityByProductId_minus(Map map)
			throws Exception {
		sqlMapClient.update("cart.updateLimitbuy_us_quantityByProductId_minus",map);
	}

	@Override
	public List<CartGoods> getHappyCartGoods(Cart cart) throws Exception {
		return sqlMapClient.queryForList("cart.getHappyCartGoods",cart);
	}

	@Override
	public MemberInfo getMemberInfoByAddr(Map<String, String> map)
			throws Exception {
		return (MemberInfo) sqlMapClient.queryForObject("cart.getMemberInfoByAddr", map);
	}

	
}
