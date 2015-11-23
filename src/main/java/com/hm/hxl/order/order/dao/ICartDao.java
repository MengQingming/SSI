package com.hm.hxl.order.order.dao;

import java.util.List;
import java.util.Map;

import com.hm.hxl.order.order.model.Address;
import com.hm.hxl.order.order.model.Cart;
import com.hm.hxl.order.order.model.CartGoods;
import com.hm.hxl.order.order.model.MemberInfo;
import com.hm.hxl.order.order.model.PromptMessage;



/**
 * 购物车
 *@author tubingbing
 */
@SuppressWarnings("rawtypes") 
public interface ICartDao {
	
	/**
	 * 查询购物车商品接口（登录）
	 * @param cart
	 * @return 
	 * @throws Exception
	 */
	public List<CartGoods> getCartGoodsByMemberId(Cart cart)throws Exception;
	
	/**
	 * 查询购物车商品接口（未登录）
	 * @param cart
	 * @return 
	 * @throws Exception
	 */
	public List<CartGoods> getCartGoodsByProductId(Cart cart)throws Exception;
	
	/**
	 * 根据商品id与用户id查询购物车商品接口
	 * @param cart
	 * @return 
	 * @throws Exception
	 */
	public List<CartGoods> getCartGoods(Cart cart)throws Exception;
	
	/**
	 * 购物车总数量接口（登录）
	 * @param cart
	 * @return int 
	 * @throws Exception
	 */
	public int getCartNumbers(Cart cart)throws Exception;

	/**
	 * 加入购物车
	 * @param map
	 * @throws Exception
	 */
	public void addCart(Map map)throws Exception;
	
	/**
	 * 修改购物车
	 * @param map
	 * @throws Exception
	 */
	public void updateCart(Map map)throws Exception;
	
	/**
	 * 删除购物车
	 * @param map
	 * @throws Exception
	 */
	public void deleteCart(Map map)throws Exception;
	
	/**
	 * 编辑购物车
	 * @param map
	 * @throws Exception
	 */
	public void editCart(Map map)throws Exception;
	
	/**
	 * 判断商品是否存在购物车表中
	 * @param map
	 * @return boolean
	 * @throws Exception
	 */
	public boolean isCart(Map map)throws Exception;
	
	/**
	 * 获取用户默认收货地址
	 * @param ap
	 * @return
	 * @throws Exception
	 */
	public Address getDefaultAddrs(Cart ap)throws Exception;
	
	/**
	 * 修改购物车未选择的状态为false
	 * @param ap
	 * @throws Exception
	 */
	public void updateCartIsSelect(Cart ap)throws Exception ;
	
	/**
	 * 修改购物车已选中的状态为true
	 * @param ap
	 * @throws Exception
	 */
	public void updateCartIsSelectTrue(Cart ap)throws Exception ;
	
	public void deleteCartCoupons(Map map)throws Exception ;
	
	public String getCartCouponsByMemberId(String member_id)throws Exception;

	/**
	 * 方法名: getAreaTypeByfrom_stos
	 * <br/>作用: 根据商品仓库区分获取仓库区分类型
	 * <br/>参数:@param params 多个from_sto数组
	 * <br/>参数:@return 
	 * <br/>返回值: List<String>
	 * <br/>@Throws 
	 */ 
	public List<String> getAreaTypeByfrom_stos(List<String> from_stos)throws Exception ;

	/**
	 * 方法名: getPromptMessageByAreaType
	 * <br/>作用: T根据仓库区分类型获取提示信息
	 * <br/>参数:@param areaType
	 * <br/>参数:@return 
	 * <br/>返回值: PromptMessage
	 * <br/>@Throws 
	 */ 
	public PromptMessage getPromptMessageByAreaType(String areaType)throws Exception ;

	/**
	 * 方法名: getAreaTypeByfrom_sto
	 * <br/>作用: 获取非海外区仓库区分ID
	 * <br/>参数:@param from_sto
	 * <br/>参数:@return 
	 * <br/>返回值: String
	 * <br/>@Throws 
	 */ 
	public List<String> getNotOverseasAreaID() throws Exception ;

	/**
	 * 方法名: getMemberInfoByMemberId
	 * <br/>作用: 通过用户ID获取用户部分信息 
	 * <br/>参数:@param member_id
	 * <br/>参数:@return 
	 * <br/>返回值: MemberInfo
	 * <br/>@Throws 
	 */ 
	public MemberInfo getMemberInfoByMemberId(String member_id)throws Exception;

	/**
	 * 方法名: updateLimitbuy_quantityByProduct_id_add
	 * <br/> 作用: 修改限购剩余总量
	 * <br/> @param map
	 * <br/> 返回值: void
	 * <br/> @Throws 
	 */ 
	public void updateLimitbuy_us_quantityByProductId_add(Map map)throws Exception;

	/**
	 * 方法名: updateLimitbuy_quantityByProduct_id_minus
	 * <br/> 作用:  修改限购剩余总量
	 * <br/> @param map 
	 * <br/> 返回值: void
	 * <br/> @Throws 
	 */ 
	public void updateLimitbuy_us_quantityByProductId_minus(Map map)throws Exception;

	/**
	 * 方法名: getHappyCartGoods
	 * <br/> 作用: TODO【这里用一句话描述这个方法的作用】
	 * <br/> @param cart
	 * <br/> @return 
	 * <br/> 返回值: List<CartGoods>
	 * <br/> @Throws 
	 */ 
	public List<CartGoods> getHappyCartGoods(Cart cart)throws Exception;

	public MemberInfo getMemberInfoByAddr(Map<String, String> map)throws Exception;

	
}
