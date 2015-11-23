package com.hm.hxl.order.order.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.hm.hxl.order.order.model.Address;
import com.hm.hxl.order.order.model.CartGoods;
import com.hm.hxl.order.order.model.CouponsNew;
import com.hm.hxl.order.order.model.OrderDetail;
import com.hm.hxl.order.order.model.OrderItems;
import com.hm.hxl.order.order.model.OrderList;
import com.hm.hxl.order.order.model.OrderObjects;
import com.hm.hxl.order.order.model.OrderParameter;
import com.hm.hxl.order.order.model.OrderPay;
import com.hm.hxl.order.order.model.ResultObject;
import com.hm.hxl.order.util.Checksum;

/**
 * 类名: IOrderService 
 * 作用: 订单 service接口
 * 作者: yanpengjie 
 * 日期: 2015-7-17 下午12:54:24 
 * 版本: V 1.0
 *
 */
public interface IOrderService {

	/**
	 * 根据订单id判断该订单号是否存在
	 * @param orderId
	 * @return
	 * @throws Exception
	 */
	public boolean getOrderId(String orderId) throws Exception;
	
	/**
	 * 获取用户收获地址
	 * @param order
	 * @return
	 * @throws Exception
	 */
	public Address getAddress(OrderParameter order) throws Exception;
	
	/**
	 * 根据仓库与商品id获取商品列表
	 * @param order
	 * @return
	 * @throws Exception
	 */
	public List<Map<String,Object>> getOrderGoods(OrderParameter order) throws Exception;

	/**
	 * 根据商品id获取货品列表
	 * @param goods_id
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> getOrderItems(String goods_id)throws Exception;
	/**
	 * 根据商品id获取商品价格和所在的仓库
	 * @param order
	 * @return
	 * @throws Exception
	 */
	public List<Map<String,Object>> getOrderFromSto(List<CartGoods> list,OrderParameter order,List<CartGoods> gift) throws Exception;
	/**
	 * 保存订单
	 * @param order
	 * @throws Exception
	 */
	public ResultObject saveOrder(List<CartGoods> list,OrderParameter order,List<CartGoods> gift,Checksum checkSum,HttpServletRequest request) throws Exception;
	/**
	 * 保存订单日志表
	 * @param order
	 * @throws Exception
	 */
	public void saveOrderLog(OrderParameter order) throws Exception;
	/**
	 * 保存订单商品表
	 * @param order
	 * @throws Exception
	 */
	public Integer saveOrderObjects(OrderObjects obj) throws Exception;
	/**
	 * 保存特卖订单表
	 * @param order
	 * @throws Exception
	 */
	public void saveLimitBuyObject(OrderObjects obj) throws Exception;
	/**
	 * 保存订单货品表
	 * @param order
	 * @throws Exception
	 */
	public Integer saveOrderItems(OrderItems item) throws Exception;
	/**
	 * 清空购物车
	 * @param order
	 * @throws Exception
	 */
	public void deleteCartInfo(OrderParameter order)throws Exception;
	
	/**
	 * 方法名: getOrderCTPCP
	 * <br/> 作用: 获取订单费用
	 * <br/> C cost_item 商品总金额
	 * <br/> T total_amount 总付金额
	 * <br/> P pmt_order 订单促销优惠
	 * <br/> C cost_freight 物流运费
	 * <br/> P pmt_goods 商品促销优惠
	 * <br/> @param cartGoodss 商品列表
	 * <br/> @param pmt 优惠信息 键值对形式(可以不传,如有优惠则需要传)
	 * <pre>
	 * 固定格式:
	 *  BigDecimal pmt_order = new BigDecimal(20.0);//订单促销优惠
	 *  BigDecimal pmt_goods = new BigDecimal(0.0);//商品促销优惠
	 *  Map &lt;String, BigDecimal&gt; pmt = new HashMap &lt;String, BigDecimal&gt; ();
	 *  pmt.put("pmt_order",pmt_order);
	 *  pmt.put("pmt_goods",pmt_goods);
	 * </pre>
	 * <br/> 返回值: Map&lt;String, BigDecimal&gt;
	 */
	public Map<String, BigDecimal> getOrderCTPCP(List<CartGoods> cartGoodss, Map<String, BigDecimal> pmt) throws Exception;
	/**
	 * 方法名: syncBiku
	 * <br/> 作用: 订单同步到比酷
	 * <br/> @param order 订单(至少要包含order_rel,payment,source 三个属性)
	 * <br/> @param checkSum
	 * <br/> @return ResultObject
	 * <br/> @throws Exception 
	 * <br/> 返回值: String
	 * <br/> @Throws
	 */
	public ResultObject syncBiku(OrderParameter order,Checksum checkSum) throws Exception;
	/**
	 * 方法名: savehandlerOrder
	 * <br/> 作用: 总订单处理包含子订单
	 * <br/> @param orderCTPCP 订单价格处理结果
	 * <br/> @param order 订单
	 * <br/> @param blanketOrder 分单结果
	 * <br/> @param checkSum 
	 * <br/> @return
	 * <br/> @throws Exception 
	 * <br/> 返回值: OrderParameter
	 * <br/> @Throws
	 */
	public OrderParameter savehandlerOrder(List<CouponsNew> couponsNews,Map<String, BigDecimal> orderCTPCP, OrderParameter order,Map<String,List<List<CartGoods>>> blanketOrder, Checksum checkSum) throws Exception;
	/**
	 * 方法名: handlerspecialSelling
	 * <br/> 作用: 对特卖商品的处理,操作orderitem,对已占数量进行修改并修改压单状态
 	 * <br/>	如果第二个参数是add则对已占数量增加,如果是minus则是减去)
	 * <br/> @param specialSelling 特卖商品
	 * <br/> @param addOrMinus 取值只能是add或minus(add则对已占数量增加,如果是minus则是减去)
	 * <br/> @throws Exception 
	 * <br/> 返回值: void
	 * <br/> @Throws
	 */
	public void handlerspecialSelling(List<CartGoods> specialSelling,String addOrMinus) throws Exception;
	
	/**
	 * 方法名: updateUsed_storeByOrder_rel
	 * <br/> 作用: 通过主订单号修改订单商品压单情况
	 * <br/> @param order_rel 主订单号
	 * <br/> @param used_store  0 不压单 1压本地 2压比酷
	 * <br/> @param is_limitbuy 是否特卖,可以传入特卖不特卖俩种情况同时出现
	 * <br/> @param is_overseas 区域区分可以传入单个区域,或多个区域
	 * <br/> @throws Exception 
	 * <br/> 返回值: void
	 * <br/> @Throws
	 */
	public void updateUsed_storeByOrder_rel(String order_rel,String used_store,List<String> is_limitbuy,List<String> is_overseas) throws Exception;
	/**
	 * 方法名: updateUsed_storeByOrder_id 
	 * <br/> 作用: 通过子订单号修改订单商品压单情况
	 * <br/> @param order_id 子订单号
	 * <br/> @param used_store 0 不压单 1压本地 2压比酷
	 * <br/> @throws Exception 
	 * <br/> 返回值: void
	 * <br/> @Throws
	 */
	public void updateUsed_storeByOrder_id(String order_id,String used_store) throws Exception;
	
	public List<OrderList> getOrderList(Checksum checkSum,String member_id,Integer pageNum) throws Exception;
	
	public List<OrderList> getOrderListOne(Checksum checkSum,String member_id,Integer pageNum) throws Exception;
	
	public OrderDetail getOrderDetail(Checksum checkSum,String order_id,String member_id) throws Exception;
	
	public String getOrderExp(String bk_ord_no,Checksum checkSum)throws Exception;
	
	public List<Map<String,Object>> getOrderLogList(String order_id) throws Exception;
	
	public Map<String,Object> getOrderInfo(String order_id) throws Exception;
	
	public void cancelOrder(Checksum checkSum,String order_id,String member_id,String falg,String reason) throws Exception;
	
	public void deleteCartCoupons(OrderParameter order) throws Exception;
	
	public int getOrderNumbers(String member_id) throws Exception;
	
	public int isExistOrder(String member_id)throws Exception;
	
	public Map<String,Object> getAppRuleOrder(String actionCondition)throws Exception;

	/**
	 * 方法名: getSpecialSellingByOrder_rel
	 * <br/> 作用: 通过主订单号获取商品信息
	 * <br/> @param order_rel
	 * <br/> @return 
	 * <br/> 返回值:	List<Map<String, Object>>
	 * <br/> @Throws 
	 */ 
	public List<Map<String, Object>> getPrductInfoByOrder_rel(String order_rel)throws Exception;

	
	/**
	 * 方法名: getBlanketOrder
	 * <br/> 作用: 分单
	 * <br/> @param list
	 * <br/> @return
	 * <br/> @throws Exception 
	 * <br/> 返回值: Map<String,List<List<CartGoods>>>
	 * <br/> 返回值说明:主键:订单区域值(1大陆2保税3海外) 
	 * <br/>		  值    :List<List<CartGoods>>代表用户该区域的所有订单
	 * <br/>			 List<CartGoods> 代表某一订单
	 * <br/>			 CartGoods 代表某一订单中的商品
	 * <br/> @Throws
	 */
	public Map<String,List<List<CartGoods>>> getBlanketOrder(List<CartGoods> list)throws Exception;

	/**
	 * 方法名: getPayGoods
	 * <br/> 作用: 通过order_rel获取PayGoods商品信息
	 * <br/> @param order_rel
	 * <br/> @return 
	 * <br/> 返回值: List<OrderPay>
	 * <br/> @Throws 
	 */ 
	public List<OrderPay> getPayGoodsByOrder_rel(String order_rel)throws Exception;

	/**
	 * 方法名: updateItemPriceByitem_id
	 * <br/> 作用: 通过itemID更新order item表中与惠买价格相关联的值
	 * <br/> @param orderItems
	 * <br/> @param item_id 
	 * <br/> 返回值: void
	 * <br/> @Throws 
	 */ 
	public void updateItemPriceByitem_id(OrderItems orderItems, String item_id)throws Exception;

	/**
	 * 方法名: updateObjectsPriceByobj_id
	 * <br/> 作用: 通过obj_id更新order Objects表中与惠买价格相关联的值
	 * <br/> @param orderObjects
	 * <br/> @param obj_id 
	 * <br/> 返回值: void
	 * <br/> @Throws 
	 */ 
	public void updateObjectsPriceByobj_id(OrderObjects orderObjects,
			String obj_id)throws Exception;

	/**
	 * 方法名: updateOrderPriceByOrder_id
	 * <br/> 作用:  通过order_id更新order 表中与惠买价格相关联的值
	 * <br/> @param orderParameter
	 * <br/> @param order_id 
	 * <br/> 返回值: void
	 * <br/> @Throws 
	 */ 
	public void updateOrderPriceByOrder_id(OrderParameter orderParameter,
			String order_id)throws Exception;

	/**
	 * 方法名: updateOrderCost_freightByOrder_id
	 * <br/> 作用: 更新订单中第一笔订单总价以及邮费
	 * <br/> @param cost_freight
	 * <br/> @param order_id 
	 * <br/> 返回值: void
	 * <br/> @Throws 
	 */ 
	public void updateOrderCost_freightByOrder_id(BigDecimal cost_freight,
			String order_id)throws Exception;

	/**
	 * 方法名: updateItemgGPriceByItem_id
	 * <br/> 作用:  通过item_id更新orderitem 表中销售价格
	 * <br/> @param g_price
	 * <br/> @param item_id 
	 * <br/> 返回值: void
	 * <br/> @Throws 
	 */ 
	public void updateItemgGPriceByItem_id(double g_price, String item_id)throws Exception;

	/**
	 * 方法名: updateOrderCost_itemByOrder_id
	 * <br/> 作用: 通过order_id更新order表中销售价格
	 * <br/> @param cost_item
	 * <br/> @param order_id 
	 * <br/> 返回值: void
	 * <br/> @Throws 
	 */ 
	public void updateOrderCost_itemByOrder_id(BigDecimal cost_item,String order_id)throws Exception;

	/**
	 * 方法名: updateOrderItemIs_limitbuyByItem_id
	 * <br/> 作用:  通过item_id更新orderitem表中是否特卖
	 * <br/> @param is_limitbuy
	 * <br/> @param item_id 
	 * <br/> 返回值: void
	 * <br/> @Throws 
	 */ 
	public void updateOrderItemIs_limitbuyByItem_id(String is_limitbuy,
			String item_id)throws Exception;
	/**
	 * 方法名: secondaryPay
	 * <br/> 作用:二次支付
	 * <br/> @param orderPays
	 * <br/> @param order
	 * <br/> @return
	 * <br/> @throws Exception 
	 * <br/> 返回值: ResultObject
	 * <br/> @Throws
	 */
	public ResultObject secondaryPay(List<OrderPay> orderPays,OrderParameter order,Checksum checkSum,HttpServletRequest request) throws Exception;
	
	/**
	 * <pre>
	 * 方法名: productStatus
	 * 作用: 判断商品是否下架，无货
	 * @param cartGoodss
	 * @param op
	 * @param checkSum
	 * @return
	 * @throws Exception 
	 * 返回值: ResultObject
	 * @Throws 
	 * </pre>
	 */
	public ResultObject productStatus(List<CartGoods> cartGoodss,OrderParameter op ,Checksum checkSum) throws Exception;
	/**
	 * <pre>
	 * 方法名: getGoodsStock
	 * 作用:  查看比酷商品是否下架，无货
	 * @param bk_id
	 * @param op
	 * @param cartGoodss
	 * @param checkSum
	 * @return
	 * @throws Exception 
	 * 返回值: ResultObject
	 * @Throws 
	 * </pre>
	 */
	public ResultObject getGoodsStock(String bk_id,OrderParameter op , List<CartGoods> cartGoodss, Checksum checkSum) throws Exception ;
	
	public String getOrder_relByOrder_id(String order_id) throws Exception ;

	/**
	 * <pre>
	 * 方法名: getShip_area
	 * 作用: 获取收货地址
	 * @param order_rel
	 * @return 
	 * 返回值: String
	 * @Throws 
	 * </pre>
	 */ 
	public String getShip_area(String order_rel) throws Exception ;
	/**
	 * <pre>
	 * 方法名: saveOrder4Memc
	 * 作用: 保存订单(携带代金券)
	 * @param cartGoodss
	 * @param order
	 * @param gift
	 * @param checkSum
	 * @param request
	 * @return 
	 * 返回值: ResultObject
	 * @Throws 
	 * </pre>
	 */
	public ResultObject saveOrder4Memc(List<CartGoods> cartGoodss,
			OrderParameter order, List<CartGoods> gift, Checksum checkSum,
			HttpServletRequest request) throws Exception ;
	/**
	 * <pre>
	 * 方法名: verificationCashCoupons
	 * 作用: 验证优惠券
	 * @param memc_code 优惠券号码
	 * @param cartGoodss 商品列表
	 * @return
	 * @throws Exception 
	 * 返回值: ResultObject 
	 * 如果flag为1或-3 msg内定义为amount+products+coupons
	 * @Throws 
	 * </pre>
	 */
	public ResultObject verificationCashCoupons(String memc_code,List<CartGoods> cartGoodss) throws Exception ;
	/**
	 * <pre>
	 * 方法名: getCouponsNewByMemc_code
	 * 作用: 获取代金券信息
	 * @param memc_code
	 * @return
	 * @throws Exception 
	 * 返回值: CouponsNew
	 * @Throws 
	 * </pre>
	 */
	public List<CouponsNew> getCouponsNewByMemc_code(String memc_code) throws Exception ;
	
	@SuppressWarnings("rawtypes")
	public List<Map> getAPPOrderList(Checksum checkSum,String member_id,Integer pageNum) throws Exception ;

	public List<String> getOrder_ids(String order_rel) throws Exception ;

	@SuppressWarnings("rawtypes")
	public List<Map> getWebOrderList(Checksum checkSum,String member_id, Integer pageNum, String flag)throws Exception ;
	
	public List<OrderList> getWebOrderListy(Checksum checkSum,String member_id, Integer pageNum, String status)throws Exception ;

	public Integer verificationOZF(String order_rel)throws Exception ;

	public int getWebOrderNum(String member_id,String status)throws Exception ;
	
	public void cancelLocalOrder(Checksum checkSum, String order_id,
			String member_id, String flag, String reason)
			throws Exception;

	public boolean getAlipayStatus(String order_rel)throws Exception;

}
