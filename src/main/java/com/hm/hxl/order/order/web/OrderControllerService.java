package com.hm.hxl.order.order.web;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hm.hxl.order.alipay.model.AlipayParameter;
import com.hm.hxl.order.kqpay.model.KqpayParameter;
import com.hm.hxl.order.order.model.Address;
import com.hm.hxl.order.order.model.Cart;
import com.hm.hxl.order.order.model.CartGoods;
import com.hm.hxl.order.order.model.MemberInfo;
import com.hm.hxl.order.order.model.OrderParameter;
import com.hm.hxl.order.order.model.ResultObject;
import com.hm.hxl.order.unionpay.model.UnionPayParameter;
import com.hm.hxl.order.util.Checksum;
import com.hm.hxl.order.util.Constant;
import com.hm.hxl.order.util.DataSourceContextHolder;

/**
 * 类名: OrderController 
 * 作用: 订单操作
 * 作者: yanpengjie 
 * 日期: 2015-7-16 下午5:31:28 
 * 版本: V 1.0
 *
 */
@Controller
@RequestMapping(value="/order")
public class OrderControllerService {
	/**日志**/
	private static Logger log = Logger.getLogger(OrderControllerService.class);
	
	
	/**
	 * 保存订单
	 * @param checkSum
	 * @param cp
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value="/saveOrder",method=RequestMethod.POST)
	@ResponseBody
	public ResultObject saveOrder(Checksum checkSum,OrderParameter order,HttpServletRequest request){
		try {
			if(StringUtils.isBlank(order.getMember_id())){
				log.error("OrderController saveOrder=====>用户id不能为空");
				return new ResultObject("-1","用户id为空",null);
			}
			if(StringUtils.isBlank(order.getAddr_id())){
				log.error("OrderController saveOrder=====>收货地址不能为空");
				return new ResultObject("-1","收货地址为空",null);
			}
			
			if(StringUtils.isBlank(order.getPayment())){
				log.error("OrderController saveOrder=====>请选择支付方式");
				return new ResultObject("-1","请选择支付方式",null);
			}
			if(!order.getPayment().equals(Constant.PAYMENT_OFFLINE) && !order.getPayment().equals(Constant.PAYMENT_ALIPAY)&&!order.getPayment().equals(Constant.PAYMENT_UNIONPAY)){
				log.error("OrderController saveOrder=====>请选择支付方式");
				return new ResultObject("-1","请选择支付方式",null);
			}
			Address addr = null; //orderServiceImpl.getAddress(order);
			if(addr==null){
				log.error("OrderServiceImpl createWebOrder=====>收货地址不能为空");
				return new ResultObject("-1","收货地址为空",null);
			}
			String area2 = addr.getArea();
			if(area2.contains("18903") || area2.contains("18901") || area2.contains("18902") || area2.contains("18892")){
				log.error("OrderController saveOrder=====>此地区不支持购买,请修改收货地址");
				return new ResultObject("-1","此地区不支持购买,请修改收货地址",null);
			}
			if(StringUtils.isBlank(order.getPvc_id())||StringUtils.isBlank(order.getCountry_id())||StringUtils.isBlank(order.getLocal_id())){
				log.error("OrderController saveOrder=====>省市县不能为空");
				return new ResultObject("-1","省市县不能为空",null);
//				order.setPvc_id(Constant.PVC_ID);
//				order.setLocal_id(Constant.LOCAL_ID);
//				order.setCountry_id(Constant.COUNTRY_ID);
			}
			order.setShip_area(addr.getArea());
			order.setShip_name(addr.getName());
			order.setShip_addr(addr.getAddr());
			order.setShip_zip(addr.getZip());
			order.setShip_tel(addr.getTel());
			order.setShip_time(addr.getTime());
			order.setShip_mobile(addr.getMobile());
			
			Cart cart = new Cart();
			cart.setMember_id(order.getMember_id());
			List<CartGoods>  cartGoodss = new ArrayList<CartGoods>();
			List<CartGoods>  list = null; //cartService.getCartGoodsByMemberId(cart);
			log.info("CartController saveOrder-----getCartGoodsByMemberId->"+list);
			for(CartGoods cg :list){
				if(cg.getIs_selected().equals("true")){
					cartGoodss.add(cg);
				}
			}
			log.info("OrderController saveOrder=====>商品列表："+cartGoodss);
			if(cartGoodss==null || cartGoodss.size()<=0){
				log.error("OrderController saveOrder=====>购物车为空");
				return new ResultObject("-1","购物车为空",null);
			}
			
			MemberInfo memberInfo = null;
			//MemberInfo memberInfo = cartService.getMemberInfoByMemberId(order.getMember_id());
			memberInfo = null; //cartService.getMemberInfoByAddr(order.getMember_id(),order.getAddr_id());
			if(memberInfo==null){
				return new ResultObject("-1","登录信息有误,请重新登录",null);
			}
			log.info("CartController saveOrder---memberInfo-->----Ident_code:"+memberInfo.getIdent_code()+"---name:-"+memberInfo.getName());
			double totalPrice = 0;
			int totalQuantity = 0;
			for (CartGoods cartGoods : cartGoodss) {
				//实名认证验证
				String isBorH = cartGoods.getIsBorH();
				if("true".equals(isBorH)){	//判断货物中如果包含保税区,海外产品,是否已实名认证
					if(StringUtils.isBlank(memberInfo.getName()) || StringUtils.isBlank(memberInfo.getIdent_code())){
						log.error("CartController saveOrder=====>商品中有保税区或海外产品但实名认证失败" );
						return new ResultObject("-2","海外商品需要提供收货人的身份信息以备海关审核",null);
					}else if(!order.getShip_name().equals(memberInfo.getName())){
						log.error("CartController saveOrder=====>跨境购买商品需要收货人姓名与实名认证姓名一致" );
						return new ResultObject("-1","跨境购买商品需要收货人姓名与实名认证姓名一致!",null);
					}
				}
				//计算保税区产品总价及数量
				String area_type = cartGoods.getArea_type();
				if("2".equals(area_type)){//保税区
					int quantity = Integer.parseInt(cartGoods.getQuantity());
					totalPrice += (Double.parseDouble(cartGoods.getPrice())*quantity);
					totalQuantity += quantity;
				}
			}
			if(totalPrice > 0 && totalQuantity > 0){
				if(totalPrice>=500){
					if(totalQuantity>1){//不通过
						log.error("CartController saveOrder=====>保税区商品价格大于等于500但没有超过1000" );
						return new ResultObject("-1","亲，您购买的保税区商品已经超过500元，会产生额外的行邮税费，建议您分次下单（使订单金额低于500元），节省开支哟。",null);
					}else {
						if(totalPrice>=1000){//不通过
							log.error("CartController saveOrder=====>保税区商品价格大于等于1000且数量大于1" );
							return new ResultObject("-1","保税区商品价格大于等于1000",null);
						}
					}
				}
			}
			//库存,是否有效//非比酷
			ResultObject resultObject = null; //orderServiceImpl.productStatus(cartGoodss, order, checkSum);
			if(resultObject != null){
				log.error("CartController saveOrder---productStatus-->"+resultObject.toString());
				return resultObject;
			}
			
			String product_ids="";
			String goods_ids="";
			String quantity="";
			for(CartGoods cg :cartGoodss){
				product_ids += cg.getProduct_id()+",";
				goods_ids += cg.getGoods_id()+",";
				quantity += cg.getQuantity()+",";
			}
			if(StringUtils.isNotBlank(product_ids)){
				order.setProduct_id(product_ids.substring(0,product_ids.length()-1));
			}else{
				log.error("OrderController saveOrder=====>货品id不能为空");
				return new ResultObject("-1","产品id为空",null);
			}
			if(StringUtils.isNotBlank(goods_ids)){
				order.setGoods_id(goods_ids.substring(0,goods_ids.length()-1));
			}else{
				log.error("OrderController saveOrder=====>商品id不能为空");
				return new ResultObject("-1","商品id为空",null);
			}
			if(StringUtils.isNotBlank(quantity)){
				order.setQuantity(quantity.substring(0,quantity.length()-1));
			}else{
				log.error("OrderController saveOrder=====>购买数量不能为空");
				return new ResultObject("-1","购买数量为空",null);
			}
			
			List<CartGoods> gift = null;//需补充
			String ip = checkSum.getIp();
			log.info("OrderController saveOrder---getIp---->"+ip);
			order.setIp(ip);
			String memc_code = order.getMemc_code();
			if(StringUtils.isNotBlank(memc_code)){
				//优惠券号
				String[] memc_codes = memc_code.split(",");
				if(memc_codes.length>0){
					log.info("OrderController saveOrder-------->优惠券号:"+memc_code);
					ResultObject saveOrder4Memc = null; //orderServiceImpl.saveOrder4Memc(cartGoodss,order,gift,checkSum,request);
					if("1".equals(saveOrder4Memc.getFlag())){
						OrderParameter order2 = (OrderParameter) saveOrder4Memc.getMsg();
						if(order.getPayment().equals("alipay")){//支付宝支付调用支付宝接口
							AlipayParameter alipaypar = new AlipayParameter();
							alipaypar.setOrder_id(order2.getOrder_rel());
							alipaypar.setMember_id(order2.getMember_id());
							return null; //alipayServiceImpl.saveAlipaySign(checkSum,alipaypar, request);
						}else if(order.getPayment().equals("unionpay")){
							UnionPayParameter unionpaypar = new UnionPayParameter();
							unionpaypar.setOrder_id(order2.getOrder_rel());
							unionpaypar.setMember_id(order2.getMember_id());
							return null; //unionpayServiceImpl.saveUnionpaySign(checkSum, unionpaypar, request);
						}else if(order.getPayment().equals("99billpay")){
							KqpayParameter kqpayparm = new KqpayParameter();
							kqpayparm.setOrder_id(order2.getOrder_rel());
							kqpayparm.setMember_id(order2.getMember_id());
							return null;//kqpayServiceImpl.saveKqpaySign(checkSum, kqpayparm, request);
						}else{
							Map map = new HashMap();
							map.put("order_id", order2.getOrder_rel());
							//总付金额
							BigDecimal total_amount = order2.getTotal_amount();
							BigDecimal cost_freight = order2.getCost_freight();
							BigDecimal cost_item = order2.getCost_item();
							BigDecimal pmt_goods = order2.getPmt_goods();
							BigDecimal pmt_order = order2.getPmt_order();
							map.put("total_amount", total_amount);
							map.put("cost_freight", cost_freight);
							map.put("cost_item", cost_item);
							map.put("pmt_goods", pmt_goods);
							map.put("pmt_order", pmt_order);
							return new ResultObject("1","success",map);
						}
					}else{
						return saveOrder4Memc;
					}
				}
			}
			
			ResultObject saveOrder = null; //orderServiceImpl.saveOrder(cartGoodss,order,gift,checkSum,request);
			if("1".equals(saveOrder.getFlag())){
				OrderParameter order2 = (OrderParameter) saveOrder.getMsg();
				if(order.getPayment().equals("alipay")){//支付宝支付调用支付宝接口
					AlipayParameter alipaypar = new AlipayParameter();
					alipaypar.setOrder_id(order2.getOrder_rel());
					alipaypar.setMember_id(order2.getMember_id());
					return null; //alipayServiceImpl.saveAlipaySign(checkSum,alipaypar, request);
				}else if(order.getPayment().equals("unionpay")){
					UnionPayParameter unionpaypar = new UnionPayParameter();
					unionpaypar.setOrder_id(order2.getOrder_rel());
					unionpaypar.setMember_id(order2.getMember_id());
					return null; //unionpayServiceImpl.saveUnionpaySign(checkSum, unionpaypar, request);
				}else if(order.getPayment().equals("99billpay")){
					KqpayParameter kqpayparm = new KqpayParameter();
					kqpayparm.setOrder_id(order2.getOrder_rel());
					kqpayparm.setMember_id(order2.getMember_id());
					return null; //kqpayServiceImpl.saveKqpaySign(checkSum, kqpayparm, request);
				}else{
					Map map = new HashMap();
					map.put("order_id", order2.getOrder_rel());
					//总付金额
					BigDecimal total_amount = order2.getTotal_amount();
					BigDecimal cost_freight = order2.getCost_freight();
					BigDecimal cost_item = order2.getCost_item();
					BigDecimal pmt_goods = order2.getPmt_goods();
					BigDecimal pmt_order = order2.getPmt_order();
					map.put("total_amount", total_amount);
					map.put("cost_freight", cost_freight);
					map.put("cost_item", cost_item);
					map.put("pmt_goods", pmt_goods);
					map.put("pmt_order", pmt_order);
					return new ResultObject("1","success",map);
				}
			}else{
				return saveOrder;
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("OrderController saveOrder:"+e.getMessage(),e);
			return new ResultObject("0","系统接口异常，请联系管理员！",null);
		}finally{
			DataSourceContextHolder.clearDataSourceType();
		}
	}
	
	
	
}
