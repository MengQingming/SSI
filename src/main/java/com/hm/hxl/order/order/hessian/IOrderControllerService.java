package com.hm.hxl.order.order.hessian;

import javax.servlet.http.HttpServletRequest;


import com.hm.hxl.order.order.model.OrderParameter;
import com.hm.hxl.order.order.model.ResultObject;
import com.hm.hxl.order.util.Checksum;


/**
 * 类名: IOrderController 
 * 作用: TODO(这里用一句话描述这个类的作用) 
 * 作者: yanpengjie 
 * 日期: 2015-7-16 下午5:22:08 
 * 版本: V 1.0
 *
 */
public interface IOrderControllerService {
	
	/**
	 * 保存订单
	 * @param checkSum
	 * @param member_id
	 * @param pageNum
	 * @return
	 */
	public ResultObject saveOrder(Checksum checkSum,OrderParameter order,HttpServletRequest request);
	
	/**
	 * 我的订单列表
	 * @param checkSum
	 * @param member_id
	 * @param pageNum
	 * @return
	 */
	public ResultObject getOrderList(Checksum checkSum,String member_id,Integer pageNum);
	
	/**
	 * 我的订单列表One
	 * @param checkSum
	 * @param member_id
	 * @param pageNum
	 * @return
	 */
	public ResultObject getOrderListOne(Checksum checkSum,String member_id,Integer pageNum);
	
	/**
	 * <pre>
	 * 方法名: getWebOrderList
	 * 作用: 网站使用的我的订单列表
	 * @param checkSum
	 * @param member_id
	 * @param pageNum
	 * @return 
	 * 返回值: Object
	 * @Throws 
	 * </pre>
	 */
	public ResultObject getWebOrderList(Checksum checkSum,String member_id,Integer pageNum,String flag);
	
	/**
	 * 订单详情
	 * @param checkSum
	 * @param member_id 用户id
	 * @param order_id  订单id
	 * @return
	 */
	public ResultObject getOrderDetail(Checksum checkSum,String member_id,String order_id);
	
	/**
	 * <pre>
	 * 方法名: getOrderDetailOne
	 * 作用: 订单详情
	 * @param checkSum
	 * @param member_id
	 * @param order_id
	 * @return 
	 * 返回值: Object
	 * @Throws 
	 * </pre>
	 */
	public ResultObject getOrderDetailOne(Checksum checkSum,String member_id,String order_rel,String order_id,String orderType);
	/**
	 * 订单跟踪
	 * @param checkSum
	 * @param order_id 订单id
	 * @return
	 */
	public ResultObject getOrderExp(Checksum checkSum,String order_id);
	
	/**
	 * 取消订单
	 * @param checkSum
	 * @param member_id
	 * @param order_id
	 * @param reason 网站用的订单取消原因
	 * @return
	 */
	public ResultObject cancelOrder(Checksum checkSum,String member_id,String order_id,String flag,String reason);
	
	/**
	 * 获取用户登录后的未完成订单数量
	 * @param checkSum
	 * @param cp
	 * @return
	 */
	public ResultObject getOrderNumbers(Checksum checkSum,String member_id);
		
	/**
	 * 方法名: createWebOrder
	 * <br/> 作用: 生成订单
	 * <br/> @param checkSum
	 * <br/> @param order
	 * <br/> @param request
	 * <br/> @return 
	 * <br/> 返回值: Object
	 * <br/> @Throws
	 */
	public ResultObject createWebOrder(Checksum checkSum,OrderParameter order,HttpServletRequest request);
	
	
	/**
	 * 方法名: saveWebOrder
	 * <br/> 作用: 倒减  保存订单同步比酷 网站二次支付
	 * <br/> @param checkSum
	 * <br/> @param order
	 * <br/> @param request
	 * <br/> @return 
	 * <br/> 返回值: Object
	 * <br/> @Throws
	 */
	public ResultObject saveWebOrder(Checksum checkSum,OrderParameter order,HttpServletRequest request);
	
	/**
	 * 方法名: saveAppOrder
	 * <br/> 作用: 倒减  保存订单同步比酷 APP二次支付
	 * <br/> @param checkSum
	 * <br/> @param order
	 * <br/> @param request
	 * <br/> @return 
	 * <br/> 返回值: Object
	 * <br/> @Throws
	 */
	public ResultObject saveAppOrder(Checksum checkSum,OrderParameter order,HttpServletRequest request);
	
	/**
	 * 方法名: createWebHappyOrder
	 * <br/> 作用: 生成订单
	 * <br/> @param checkSum
	 * <br/> @param order
	 * <br/> @param request
	 * <br/> @return 
	 * <br/> 返回值: Object
	 * <br/> @Throws
	 */
	public ResultObject createWebHappyOrder(Checksum checkSum,OrderParameter order,HttpServletRequest request);
	
	
	/**
	 * 方法名: createWapH5Order
	 * <br/> 作用: 生成订单WAP
	 * <br/> @param checkSum
	 * <br/> @param order
	 * <br/> @param request
	 * <br/> @return 
	 * <br/> 返回值: ResultObject
	 * <br/> @Throws
	 */
	public ResultObject createWapH5Order(Checksum checkSum,OrderParameter order,HttpServletRequest request);
	/**
	 * 方法名: deleteOrder
	 * <br/> 作用: 删除订单
	 * <br/> @param checkSum
	 * <br/> @param order
	 * <br/> @param request
	 * <br/> @return 
	 * <br/> 返回值: Object
	 * <br/> @Throws
	 */
	public ResultObject deleteOrder(Checksum checkSum,String order_id,String orderType);
}
