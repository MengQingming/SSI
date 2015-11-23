package com.hm.hxl.order.order.dao;

import java.util.List;
import java.util.Map;

import com.hm.hxl.order.order.model.Address;
import com.hm.hxl.order.order.model.CancelOrderItemsInventory;
import com.hm.hxl.order.order.model.OrderDetail;
import com.hm.hxl.order.order.model.OrderItems;
import com.hm.hxl.order.order.model.OrderObjects;
import com.hm.hxl.order.order.model.OrderParameter;
import com.hm.hxl.order.order.model.OrderPay;
import com.hm.hxl.order.util.Checksum;
import com.hm.hxl.order.util.ErrorMsg;

/**
 * <br/>类名: IOrderDao 
 * <br/>作用: TODO(这里用一句话描述这个类的作用) 
 * <br/>作者: yanpengjie 
 * <br/>日期: 2015-7-22 下午5:15:33 
 * <br/>版本: V 1.0
 */
@SuppressWarnings("rawtypes")
public interface IOrderDao {
	
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
	 * @param product_id
	 * @return
	 * @throws Exception
	 */
	public List<Map<String,Object>> getOrderItems(String product_id) throws Exception;
	/**
	 * 根据商品id获取商品价格和所在的仓库
	 * @param order
	 * @return
	 * @throws Exception
	 */
	public List<Map<String,Object>> getOrderFromSto(OrderParameter order) throws Exception;
	/**
	 * 保存订单
	 * @param order
	 * @throws Exception
	 */
	public void saveOrder(OrderParameter order) throws Exception;
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
	
	public List<Map<String,Object>> getOrderList(Checksum checkSum,String member_id,Integer pageNum) throws Exception;
	public List<Map<String,Object>> getAPPOrderList(Checksum checkSum,String member_id,Integer pageNum) throws Exception;
	public List<Map<String,String>> getOrderGoodsList(Checksum checkSum,String order_id) throws Exception;
	
	public OrderDetail getOrderDetail(String order_id,String member_id) throws Exception;
	
	public List<Map<String,Object>> getOrderLogList(String order_id) throws Exception;
	
	public Map<String,Object> getOrderInfo(String order_id) throws Exception;
	
	public int cancelOrder(String order_id,String member_id) throws Exception;
	public void saveCancelOrderLog(String order_id,String member_id,String reason) throws Exception;
	
	public List<Map<String,Object>> getOrderListByOrderRel(String order_rel) throws Exception;
	
	public List<Map<String,Object>> getOrderItemsByOrderId(String order_id) throws Exception;
	
	public int getOrderNumbers(String member_id) throws Exception;
	
	public void updateOrderBkid(Map map)throws Exception;
	public void deleteLimitBuy(String order_id)throws Exception;
	
	
	public int isExistOrder(String member_id)throws Exception;
	
	public Map<String,Object> getAppRuleOrder(String actionCondition)throws Exception;

	/**
	 * 方法名: getfirstpriceByDt_id
	 * <br/> 作用: 获取物流运费
	 * <br/> @param string
	 * <br/> @return 
	 * <br/> 返回值: double
	 * <br/> @Throws 
	 */ 
	public double getFirstpriceByDt_id(String dt_id)throws Exception;

	/**
	 * 方法名: getBikuOrderInfo
	 * <br/> 作用: 获取比酷订单ID以及订单号
	 * <br/> @param order_rel
	 * <br/> @return 
	 * <br/> 返回值: List<Map<String,Object>>
	 * <br/> @Throws 
	 */ 
	public List<Map<String, Object>> getBikuOrderInfo(String order_rel) throws Exception ;

	/**
	 * 方法名: deleteOrderByOrder_rel
	 * <br/> 作用: 删除订单信息通过order_rel
	 * <br/> @param order_rel 
	 * <br/> 返回值: void
	 * <br/> @Throws 
	 */ 
	public void deleteOrderByOrder_rel(String order_rel)throws Exception ;

	/**
	 * 方法名: deleteOrderItemsByOrder_rel
	 * <br/> 作用: 删除订单货品表通过order_rel
	 * <br/> @param order_rel 
	 * <br/> 返回值: void
	 * <br/> @Throws 
	 */ 
	public void deleteOrderItemsByOrder_rel(String order_rel)throws Exception ;

	/**
	 * 方法名: deleteOrderObjectsByOrder_rel
	 * <br/> 作用: 删除订单商品表通过order_rel
	 * <br/> @param order_rel 
	 * <br/> 返回值: void
	 * <br/> @Throws 
	 */ 
	public void deleteOrderObjectsByOrder_rel(String order_rel)throws Exception ;

	/**
	 * 方法名: getPrimageByCurrentTime
	 * <br/> 作用: 获取当前时间的运费优惠
	 * <br/> @return 
	 * <br/> 返回值: List<Map<String,Object>>
	 * <br/> @Throws 
	 */ 
	public Map<String, Object> getPrimageByCurrentTime(String c_template)throws Exception ;

	/**
	 * 方法名: getSpecialSellingByOrder_rel
	 * <br/> 作用: 通过主订单号获取商品信息
	 * <br/> @param order_rel
	 * <br/> @return 
	 * <br/> 返回值:	List<Map<String, Object>>
	 * <br/> @Throws 
	 */ 
	public List<Map<String, Object>> getPrductInfoByOrder_rel(String order_rel)throws Exception ;
	
	
	//通过订单号取的订单商品
	public List<CancelOrderItemsInventory> getYaDanProductByOrder_id(String order_id,String member_id)throws Exception;
	//通过product_id nums  对货品进行库存释放   + nums
	public void addNumsToLimitbuyusQuantity(CancelOrderItemsInventory citems)throws Exception;

	/**
	 * 方法名: updateUsed_storeByOrder_rel
	 * <br/> 作用: 通过主订单号修改压单状态
	 * <br/> @param map
	 * <br/> @throws Exception 
	 * <br/> 返回值: void
	 * <br/> @Throws
	 */
	public void updateUsed_storeByOrder_rel(Map map)throws Exception ;
	/**
	 * 
	 * 方法名: updateUsed_storeByOrder_id
	 * <br/> 作用: 通过子订单号修改压单状态
	 * <br/> @param map
	 * <br/> @throws Exception 
	 * <br/> 返回值: void
	 * <br/> @Throws
	 */
	public void updateUsed_storeByOrder_id(Map map)throws Exception ;

	/**
	 * 方法名: getPayGoodsByOrder_rel
	 * <br/> 作用: 通过order_rel获取PayGoods商品信息
	 * <br/> @param order_rel
	 * <br/> @return 
	 * <br/> 返回值: List<OrderPay>
	 * <br/> @Throws 
	 */ 
	public List<OrderPay> getPayGoodsByOrder_rel(String order_rel)throws Exception ;

	/**
	 * 方法名: updateItemPriceByitem_id
	 * <br/> 作用: 通过itemID更新item表中与惠买价格相关联的值
	 * <br/> @param map
	 * <br/> 返回值: void
	 * <br/> @Throws 
	 */ 
	public void updateItemPriceByitem_id(Map map)throws Exception ;

	/**
	 * 方法名: updateObjectsPriceByobj_id
	 * <br/> 作用:  通过obj_id更新order Objects表中与惠买价格相关联的值
	 * <br/> @param map 
	 * <br/> 返回值: void
	 * <br/> @Throws 
	 */ 
	public void updateObjectsPriceByobj_id(Map map) throws Exception ;

	/**
	 * 方法名: updateOrderPriceByOrder_id
	 * <br/> 作用: 通过Order_id 更新order 表中与惠买价格相关联的值
	 * <br/> @param map 
	 * <br/> 返回值: void
	 * <br/> @Throws 
	 */ 
	public void updateOrderPriceByOrder_id(Map map)throws Exception ;

	/**
	 * 方法名: updateOrderCost_freightByOrder_id
	 * <br/> 作用: 更新订单中第一笔订单总价以及邮费
	 * <br/> @param map 
	 * <br/> 返回值: void
	 * <br/> @Throws 
	 */ 
	
	public void updateOrderCost_freightByOrder_id(Map map)throws Exception ;

	/**
	 * 方法名: updateItemgGPriceByItem_id
	 * <br/> 作用:通过item_id更新orderitem 表中销售价格
	 * <br/> @param map 
	 * <br/> 返回值: void
	 * <br/> @Throws 
	 */ 
	public void updateItemgGPriceByItem_id(Map map) throws Exception ;

	/**
	 * 方法名: updateOrderCost_itemByOrder_id
	 * <br/> 作用: 通过order_id更新order表中销售价格
	 * <br/> @param map 
	 * <br/> 返回值: void
	 * <br/> @Throws 
	 */ 
	public void updateOrderCost_itemByOrder_id(Map map) throws Exception ;

	/**
	 * 方法名: updateOrderItemIs_limitbuyByItem_id
	 * <br/> 作用:通过item_id更新orderitem表中是否特卖
	 * <br/> @param map 
	 * <br/> 返回值: void
	 * <br/> @Throws 
	 */ 
	public void updateOrderItemIs_limitbuyByItem_id(Map map) throws Exception ;
	
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

	public List<OrderParameter> getOrderParameters(String order_rel) throws Exception ;

	public List<OrderObjects> getOrderObjectss(String order_rel) throws Exception ;
	
	public List<OrderItems> getOrderItemss(String order_rel) throws Exception ;

	public void cancelOrderParameterPmt(OrderParameter op)throws Exception ;

	public void cancelOrderItemsPmt(OrderItems oi)throws Exception ;

	public void saveCouponsItemRel(Map map)throws Exception ;

	public void updatePrice4OrderItems(OrderItems oi)throws Exception ;

	public void updatePrice4OrderObjects(OrderObjects ob)throws Exception ;

	public void updatePrice4OrderParameter(OrderParameter op)throws Exception ;

	public void deleteCouponsUse(String order_rel)throws Exception ;

	public void deleteCouponsItemUse(String order_rel)throws Exception ;

	public List<String> getOrder_ids(String order_rel)throws Exception ;

	public List<Map<String, Object>> getWebOrderList(Checksum checkSum,String member_id,
			Integer pageNum, String status)throws Exception ;

	public Integer verificationOZF(String order_rel)throws Exception ;

	public int getWebOrderNum(String member_id,String status)throws Exception ;

	public String getOrderAmount(String order_rel)throws Exception ;

	public String getBiKuIDByOr(String order_rel)throws Exception ;

	public boolean getAlipayStatus(String order_rel)throws Exception ;

	public void updateUserCoupons(List<String> memc_codes, String member_id,
			boolean b)throws Exception ;

	public List<String> getUseMemc_codes(String order_rel)throws Exception ;

	public String getOrderMember_id(String order_rel)throws Exception ;

	public void saveErrorMsg(ErrorMsg msg)throws Exception ;

	
}
