package com.hm.hxl.order.order.service;

import java.util.List;
import java.util.Map;

import com.hm.hxl.order.order.model.AddCartParameter;
import com.hm.hxl.order.order.model.Address;
import com.hm.hxl.order.order.model.Cart;
import com.hm.hxl.order.order.model.CartGoods;
import com.hm.hxl.order.order.model.CartParameter;
import com.hm.hxl.order.order.model.Coupons;
import com.hm.hxl.order.order.model.EditCartParameter;
import com.hm.hxl.order.order.model.MemberInfo;
import com.hm.hxl.order.order.model.ProductPara;
import com.hm.hxl.order.order.model.PromptMessage;
import com.hm.hxl.order.order.model.ResultObject;
import com.hm.hxl.order.util.Checksum;


/**
 * 类名: ICartService 
 * 作用: 购物车service 
 * 作者: yanpengjie 
 * 日期: 2015-7-17 下午12:54:38 
 * 版本: V 1.0
 *
 */
public interface ICartService {

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
	public void addCart(AddCartParameter ap)throws Exception;
	
	/**
	 * 修改购物车
	 * @param map
	 * @throws Exception
	 */
	public void updateCart(AddCartParameter ap)throws Exception;
	
	/**
	 * 删除购物车
	 * @param map
	 * @throws Exception
	 */
	public void deleteCart(EditCartParameter ap)throws Exception;
	
	/**
	 * 加入或者修改购物车
	 * @param ap
	 * @throws Exception
	 */
	public void saveOrUpdateCart(AddCartParameter ap)throws Exception;
	
	/**
	 * 编辑购物车
	 * @param map
	 * @throws Exception
	 */
	public void editCart(EditCartParameter ap)throws Exception;
	
	/**
	 * 判断商品是否存在购物车表中
	 * @param map
	 * @return boolean
	 * @throws Exception
	 */
	public boolean isCart(AddCartParameter ap)throws Exception;
	
	/**
	 * 获取用户默认收货地址
	 * @param ap
	 * @return
	 * @throws Exception
	 */
	public Address getDefaultAddrs(Cart ap)throws Exception;
	
	/**
	 * 获取优惠券
	 * @param ap
	 * @return
	 * @throws Exception
	 */
	public List<Coupons> getCoupons(Checksum checksum,Cart ap)throws Exception;
	
	/**
	 * 更新购物车状态（is_selected）
	 * @param ap
	 * @throws Exception
	 */
	public void updateCartIsSelect(Cart ap)throws Exception ;
	
	@SuppressWarnings("rawtypes")
	public void deleteCartCoupons(Map map)throws Exception ;
	public String getCartCouponsByMemberId(String member_id)throws Exception;

	/**
	 * 方法名: getAreaTypeByfrom_stos
	 * <br/>作用: 根据商品仓库区分获取仓库区分类型(list集合)
	 * <br/>参数:@param from_stos
	 * <br/>参数:@return 
	 * <br/>返回值: List<String>
	 * <br/>@Throws 
	 */ 
	public List<String> getAreaTypeByfrom_stos(List<String> from_stos)throws Exception ;

	/**
	 * 方法名: getPromptMessageByAreaType
	 * <br/>作用: 根据仓库区分类型获取提示信息
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
	 * <br/>返回值: void
	 * <br/>@Throws 
	 */ 
	public MemberInfo getMemberInfoByMemberId(String member_id)throws Exception;

	/**
	 * 方法名: getHappyCartGoods
	 * <br/> 作用: TODO【这里用一句话描述这个方法的作用】
	 * <br/> @param cart
	 * <br/> @return 
	 * <br/> 返回值: List<CartGoods>
	 * <br/> @Throws 
	 */ 
	public List<CartGoods> getHappyCartGoods(Cart cart) throws Exception;

	public ResultObject verificationCashCoupons(String memc_code,List<CartGoods> cartGoodss) throws Exception;
	
	public List<CartGoods> productStatus(List<CartGoods> list,CartParameter cp,Checksum checkSum)throws Exception;
	
	public List<CartGoods> getGoodsStock(String bk_id, CartParameter cp, List<CartGoods> cartGoodss, Checksum checkSum) throws Exception;

	public String getGoodsStock(String bk_id, ProductPara para,Checksum checkSum) throws Exception;

	public CartGoods productStatus( CartGoods goods ,ProductPara para,Checksum checkSum)throws Exception;

	public MemberInfo getMemberInfoByAddr(String member_id, String addr_id)throws Exception;
}
