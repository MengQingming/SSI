package com.hm.hxl.order.order.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.hm.hxl.order.order.dao.ICartDao;
import com.hm.hxl.order.order.dao.ICommentDao;
import com.hm.hxl.order.order.dao.ICouponsDao;
import com.hm.hxl.order.order.dao.IOrderDao;
import com.hm.hxl.order.order.exception.OrderException;
import com.hm.hxl.order.order.model.Address;
import com.hm.hxl.order.order.model.Area;
import com.hm.hxl.order.order.model.CancelOrderItemsInventory;
import com.hm.hxl.order.order.model.CartGoods;
import com.hm.hxl.order.order.model.Comment;
import com.hm.hxl.order.order.model.ComparatorCartGoods;
import com.hm.hxl.order.order.model.Couponlog4User;
import com.hm.hxl.order.order.model.CouponsGoods;
import com.hm.hxl.order.order.model.CouponsNew;
import com.hm.hxl.order.order.model.GoodsStockByIdParameters;
import com.hm.hxl.order.order.model.OrderDetail;
import com.hm.hxl.order.order.model.OrderItems;
import com.hm.hxl.order.order.model.OrderList;
import com.hm.hxl.order.order.model.OrderObjects;
import com.hm.hxl.order.order.model.OrderParameter;
import com.hm.hxl.order.order.model.OrderPay;
import com.hm.hxl.order.order.model.ResultObject;
import com.hm.hxl.order.order.service.IAreaService;
import com.hm.hxl.order.order.service.IOrderService;
import com.hm.hxl.order.order.service.LogisticsService;
import com.hm.hxl.order.util.Checksum;
import com.hm.hxl.order.util.Constant;
import com.hm.hxl.order.util.ErrorMsg;
import com.hm.hxl.order.util.HttpRequest;
import com.hm.hxl.order.util.Md5;
import com.hm.hxl.order.util.StringUtil;
import com.hm.hxl.order.util.SystemConfig;


/**
 * 类名: OrderServiceImpl 
 * 作用: 订单服务实现
 * 作者: yanpengjie 
 * 日期: 2015-7-17 下午12:55:32 
 * 版本: V 1.0
 *
 */
@SuppressWarnings({"rawtypes","unchecked"})
@Service
public class OrderServiceImpl implements IOrderService {

	/**日志**/
	private static Logger log = Logger.getLogger(OrderServiceImpl.class);
	@Autowired
	private IOrderDao orderDao;
	
	@Autowired
	private ICartDao cartDaoImpl;
	
	@Autowired
	private LogisticsService logisticsService;
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private ICommentDao commentDao;
	
	@Autowired
	private IAreaService areaService;
	
	@Autowired
	private ICouponsDao couponsDaoImpl;
	
	
	private BigDecimal getBLJ(BigDecimal bigDecimal){
		//不保留小数位,四舍五入,开启不要注释掉setScale(2 保持保留2位00 比如 3.00
		//bigDecimal = bigDecimal.setScale(0, BigDecimal.ROUND_HALF_UP);
		bigDecimal = bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP);
		return bigDecimal;
	}
	
	
	@Override
	public boolean getOrderId(String orderId) throws Exception {
		return orderDao.getOrderId(orderId);
	}

	@Override
	public synchronized ResultObject saveOrder(List<CartGoods> list,OrderParameter order,List<CartGoods> gift,Checksum checkSum,HttpServletRequest request) throws Exception {
		order.setCreatetime(System.currentTimeMillis()/1000);
		order.setLast_modified(System.currentTimeMillis()/1000);
		if(!"web".equals(checkSum.getType()) && !"h5wap".equals(checkSum.getType())){
			/**首单立减**/
			BigDecimal sumPrice = new BigDecimal(0.00);
			if(list!=null && list.size()>0){
				for(CartGoods cg :list){
					BigDecimal multiply = new BigDecimal(cg.getPrice()).multiply(new BigDecimal(cg.getQuantity()));
					sumPrice = sumPrice.add(multiply.subtract(cg.getPmt()));
				}
			}
			int count = isExistOrder(order.getMember_id());
			if(count==0){
				Map appOrderm = getAppRuleOrder("appfirstorder");
				if(appOrderm!=null){
					String action_solution = appOrderm.get("action_solution")==null ? "0":appOrderm.get("action_solution").toString();
					//分摊首单立减
					BigDecimal solu = new BigDecimal(action_solution);
					if(sumPrice.doubleValue()<=solu.doubleValue()){
						for(CartGoods cg :list){
							BigDecimal xamt = new BigDecimal(cg.getPrice()).multiply(new BigDecimal(cg.getQuantity()));
							cg.setPmt(xamt);
						}
					}else{
						int size = list.size();
						int index = 0;
						BigDecimal dec = new BigDecimal(0.00);
						for(CartGoods cg :list){
							index++;
							if(index==size){
								BigDecimal subtract = solu.subtract(dec);
								BigDecimal xamt = new BigDecimal(cg.getPrice()).multiply(new BigDecimal(cg.getQuantity()));
								BigDecimal sub = xamt.subtract(cg.getPmt());
								if(subtract.doubleValue()>=sub.doubleValue()){
									cg.setPmt(xamt);
								}else{
									cg.setPmt(cg.getPmt().add(subtract));
								}
							}else{
								BigDecimal xamt = new BigDecimal(cg.getPrice()).multiply(new BigDecimal(cg.getQuantity()));
								BigDecimal sub = xamt.subtract(cg.getPmt());
								BigDecimal divide = sub.divide(sumPrice,6,BigDecimal.ROUND_DOWN);//商品小计所占比例
								BigDecimal multiply = solu.multiply(divide);
								multiply = getBLJ(multiply);
								cg.setPmt(cg.getPmt().add(multiply));
								dec = dec.add(multiply);
							}
						}
					}
				}
			}
		}
		
		Map<String, BigDecimal> orderCTPCP = this.getOrderCTPCP(list,null);
		Map<String, List<List<CartGoods>>> blanketOrder = getBlanketOrder(list);
		OrderParameter order2 = this.savehandlerOrder(null, orderCTPCP,order,blanketOrder,checkSum);
		//处理特卖
		List<CartGoods> specialSelling = new ArrayList<CartGoods>();//特卖商品
		for (CartGoods cartGoods : list) {
			String goodsflag = cartGoods.getGoodsflag();
			if("1".equals(goodsflag)){
				specialSelling.add(cartGoods);
			}
		}
		if(specialSelling.size()>0){
			//修改库存占用
			this.handlerspecialSelling(specialSelling,"add");
			//修改压单状况
			List<String> is_limitbuy = new ArrayList<String>();
			is_limitbuy.add("1");
			List<String> is_overseas = new ArrayList<String>();
			is_overseas.add("1");is_overseas.add("2");is_overseas.add("3");is_overseas.add("4");
			updateUsed_storeByOrder_rel(order2.getOrder_rel(), "1", is_limitbuy,is_overseas);
		}
		//同步比酷
		ResultObject syncBiku = syncBiku(order2,checkSum);
		if(syncBiku!=null){
			log.error("json=result==>>"+syncBiku.getError());
			//一次支付失败删除本地订单
			String order_rel = order.getOrder_rel();
			orderDao.deleteOrderItemsByOrder_rel(order_rel);
			orderDao.deleteOrderObjectsByOrder_rel(order_rel);
			orderDao.deleteOrderByOrder_rel(order_rel);
			return syncBiku;
		}else{
			deleteCartInfo(order);
			order2.setPayment(order.getPayment());
			return new ResultObject("1", "", order2);
		}
	}
	
	@Override
	public Map<String,List<List<CartGoods>>> getBlanketOrder(List<CartGoods> list) throws Exception{
		List<CartGoods> mainlandArea = new ArrayList<CartGoods>();
		List<CartGoods> mainland6A = new ArrayList<CartGoods>();
		Map<String , List<CartGoods>> bondedArea = new HashMap<String, List<CartGoods>>();
		List<List<CartGoods>> overseasArea = new ArrayList<List<CartGoods>>();//每个List<CartGoods>只放一个商品数量为1
		for (CartGoods cartGoods : list) {
			String area_type = cartGoods.getArea_type();//1大陆 2 保税区 3海外直销 4 6大仓
			//所有大陆商品在一块
			if("1".equals(area_type)){
				mainlandArea.add(cartGoods);
			//保税区按保税区分分开,即按from_sto分
			}else if("2".equals(area_type)){
				String from_sto = cartGoods.getFrom_sto();
				List<CartGoods> cartGoodss = null;
				if(bondedArea.containsKey(from_sto)){
					cartGoodss = bondedArea.get(from_sto);
					cartGoodss.add(cartGoods);
					bondedArea.put(from_sto, cartGoodss);
				}else{
					cartGoodss = new ArrayList<CartGoods>();
					cartGoodss.add(cartGoods);
					bondedArea.put(from_sto, cartGoodss);
				}
			//海外按整体数量分单,每个商品的单个数量为一单
			}else if("3".equals(area_type)){
				int quantityz = Integer.parseInt(cartGoods.getQuantity());
				BigDecimal pmt = cartGoods.getPmt();
				BigDecimal divide = pmt.divide(new BigDecimal(quantityz),6,BigDecimal.ROUND_DOWN);
				divide = divide.setScale(2, BigDecimal.ROUND_HALF_UP);
				BigDecimal divi = new BigDecimal(0.00);
				for (int i = 0; i < quantityz; i++) {
					CartGoods cartGoods2 = new CartGoods();
					BeanUtils.copyProperties(cartGoods2, cartGoods);
					cartGoods2.setQuantity("1");
					List<CartGoods> cartGoodss = new ArrayList<CartGoods>();
					if(i==quantityz-1){
						BigDecimal multiply = pmt.subtract(divi);
						cartGoods2.setPmt(multiply);
						cartGoodss.add(cartGoods2);
						overseasArea.add(cartGoodss);
					}else{
						cartGoods2.setPmt(divide);
						divi = divi.add(divide);
						cartGoodss.add(cartGoods2);
						overseasArea.add(cartGoodss);
					}
				}
			}else if("4".equals(area_type)){
				mainland6A.add(cartGoods);
			}
		}
		//mainlandArea 大陆 bondedArea 保税 overseasArea 海外 mainland6A 6大仓
		/**
		 * blanketOrder 说明:
		 * 	键存储的是分区类型 1代表大陆 2代表保税 3代表海外  4 6大仓
		 *  值存储的是该区域的所有订单-->List<List<CartGoods>>
		 *  每一个List<CartGoods>为一单
		 */
		Map<String,List<List<CartGoods>>> blanketOrder = new HashMap<String,List<List<CartGoods>>>();
		//大陆订单
		if(mainlandArea.size()>0){
			List<List<CartGoods>> mainland = new ArrayList<List<CartGoods>>();
			mainland.add(mainlandArea);
			blanketOrder.put("1", mainland);
		}
		//6大仓订单
		if(mainland6A.size()>0){
			List<List<CartGoods>> mainland6 = new ArrayList<List<CartGoods>>();
			mainland6.add(mainland6A);
			blanketOrder.put("4", mainland6);
		}
		
		//保税区订单
		if(bondedArea.size()>0){
			Set<String> keySet = bondedArea.keySet();
			List<List<CartGoods>> bonded = new ArrayList<List<CartGoods>>();
			for (String from_sto : keySet) {
				List<CartGoods> cartGoodss = bondedArea.get(from_sto);
				bonded.add(cartGoodss);
			}
			blanketOrder.put("2", bonded);
		}
		//海外直邮订单
		if(overseasArea.size()>0){
			List<List<CartGoods>> overseas = new ArrayList<List<CartGoods>>();
			for (List<CartGoods> cartGoodss : overseasArea) {
				overseas.add(cartGoodss);
			}
			blanketOrder.put("3", overseas);
		}
		return blanketOrder;
	}
	
	
	@Override
	public Map<String, BigDecimal> getOrderCTPCP(List<CartGoods> cartGoodss, Map<String, BigDecimal> pmt) throws Exception{
		Map<String, BigDecimal> map = new HashMap<String, BigDecimal>();
		BigDecimal cost_item = new BigDecimal(0.0);//商品总金额
		BigDecimal total_amount = new BigDecimal(0.0);//总付金额
		BigDecimal pmt_order = new BigDecimal(0.0);//订单促销优惠
		BigDecimal pmt_goods = new BigDecimal(0.0);//商品促销优惠
		BigDecimal pmt_temp = new BigDecimal(0.0);
		if(pmt!=null && pmt.size()>0){
			if(pmt.get("pmt_order")!=null){
				pmt_order = pmt.get("pmt_order");
			}
			if(pmt.get("pmt_goods")!=null){
				pmt_order = pmt.get("pmt_goods");
			}
		}
		for (CartGoods cartGoods : cartGoodss) {
			BigDecimal pm = cartGoods.getPmt();
			pmt_temp = pmt_temp.add(pm);
			total_amount = total_amount.add(new BigDecimal(cartGoods.getPrice()).multiply(new BigDecimal(cartGoods.getQuantity())));
			total_amount = total_amount.subtract(pm);
			cost_item = cost_item.add(new BigDecimal(cartGoods.getG_price()).multiply(new BigDecimal(cartGoods.getQuantity())));
		}
		total_amount = total_amount.subtract(pmt_order).subtract(pmt_goods);
		pmt_order = pmt_order.add(pmt_temp);
		double firstprice = orderDao.getFirstpriceByDt_id("1");
		BigDecimal cost_freight = new BigDecimal(firstprice);//物流运费
		Map<String, Object> primage = orderDao.getPrimageByCurrentTime("sales_order");
		if(primage!=null && primage.size()>0){
			if(primage.get("conditions")!=null && primage.get("action_conditions")!=null){
				String conditions = primage.get("conditions").toString();
				String action_conditions = primage.get("action_conditions").toString();
				JSONObject conditionsJson = JSONObject.fromObject(conditions);
				JSONObject action_conditionsJson = JSONObject.fromObject(action_conditions);
				boolean containsKey = conditionsJson.containsKey("type");
				boolean containsKey2 = conditionsJson.containsKey("account");
				boolean containsKey3 = action_conditionsJson.containsKey("type");
				if(containsKey && containsKey2 && containsKey3){
					String conditionsType = conditionsJson.getString("type");
					String conditionsAccount = conditionsJson.getString("account");
					String action_conditionsType = action_conditionsJson.getString("type");
					if("sales_order".equals(conditionsType)){
						if(total_amount.doubleValue() >= Double.parseDouble(conditionsAccount)){
							if("freeshipping".equals(action_conditionsType)){
								cost_freight = new BigDecimal(0.0);
							}
						}
					}else {
						
					}
				}
			}
		}
		total_amount = total_amount.add(cost_freight);
		
		cost_item = cost_item.setScale(2, BigDecimal.ROUND_HALF_UP);
		total_amount = total_amount.setScale(2, BigDecimal.ROUND_HALF_UP);
		pmt_order = pmt_order.setScale(2, BigDecimal.ROUND_HALF_UP);
		cost_freight = cost_freight.setScale(2, BigDecimal.ROUND_HALF_UP);
		pmt_goods = pmt_goods.setScale(2, BigDecimal.ROUND_HALF_UP);
		
		map.put("cost_item", cost_item);
		map.put("total_amount", total_amount);
		map.put("pmt_order", pmt_order);
		map.put("cost_freight", cost_freight);
		map.put("pmt_goods", pmt_goods);
		return map;
	}
	
	@Override//limitbuy_us_quantity
	public synchronized void handlerspecialSelling(List<CartGoods> specialSelling,String addOrMinus) throws Exception{
		
		if(specialSelling.size()>0){
			if("minus".equals(addOrMinus)){
				for (CartGoods cartGoods : specialSelling) {
					if("1".equals(cartGoods.getGoodsflag())){
						String quantity = cartGoods.getQuantity();
						String product_id = cartGoods.getProduct_id();
						Map map = new HashMap();
						map.put("quantity", quantity);
						map.put("product_id", product_id);
						cartDaoImpl.updateLimitbuy_us_quantityByProductId_minus(map);
					}
				}
			}else if("add".equals(addOrMinus)){
				for (CartGoods cartGoods : specialSelling) {
					if("1".equals(cartGoods.getGoodsflag())){
						String quantity = cartGoods.getQuantity();
						String product_id = cartGoods.getProduct_id();
						Map map = new HashMap();
						map.put("quantity", quantity);
						map.put("product_id", product_id);
						cartDaoImpl.updateLimitbuy_us_quantityByProductId_add(map);
					}
				}
			}
		}
	}
	
	@Override
	public void updateUsed_storeByOrder_rel(String order_rel,
			String used_store, List<String> is_limitbuy, List<String> is_overseas)
			throws Exception {
		// 修改压单状态0
		Map map = new HashMap();
		map.put("used_store", used_store);//0 不压单 1压单本地2压单比酷
		map.put("is_limitbuy", is_limitbuy);//1 特卖
		map.put("is_overseas", is_overseas);//1大陆 2保税 3海外 4 6大仓"'1','2','3'"
		map.put("order_rel", order_rel);//主订单号
		orderDao.updateUsed_storeByOrder_rel(map);
	}
	
	@Override
	public void updateUsed_storeByOrder_id(String order_id, String used_store)
			throws Exception {
		Map map = new HashMap();
		map.put("used_store", used_store);//0 不压单 1压单本地2压单比酷
		map.put("order_id", order_id);//子订单号
		orderDao.updateUsed_storeByOrder_id(map);
	}
	

	@SuppressWarnings("unused")
	@Override
	public OrderParameter savehandlerOrder(List<CouponsNew> couponsNews,Map<String, BigDecimal> orderCTPCP, OrderParameter order,Map<String,List<List<CartGoods>>> blanketOrder, Checksum checkSum) throws Exception {
		order.setSource(checkSum.getType());
		BigDecimal total_total_amount = orderCTPCP.get("total_amount");//总付金额
		BigDecimal total_cost_freight = orderCTPCP.get("cost_freight");//物流运费
		BigDecimal total_cost_item = orderCTPCP.get("cost_item");//商品总金额
		BigDecimal total_pmt_order = orderCTPCP.get("pmt_order");//订单促销优惠
		BigDecimal total_pmt_goods = orderCTPCP.get("pmt_goods");//商品促销优惠
		String source = null;
		String order_rel = null;
		Set<String> keySet = blanketOrder.keySet();
		int index = 0;
		int size = 0;
		String is_tax=order.getIs_tax();
		String tax_type=order.getTax_type();
		String tax_comany=order.getTax_company();
		BigDecimal temp = new BigDecimal(0.0);
		for (String areatype : keySet) {
			List<List<CartGoods>> cartGoodsss = blanketOrder.get(areatype);
			size += cartGoodsss.size();
		}
		for (String areatype : keySet) {
			List<List<CartGoods>> cartGoodsss = blanketOrder.get(areatype);
			for (List<CartGoods> cartGoodss : cartGoodsss) {
				index++;
				BigDecimal total_amount = new BigDecimal(0.0);
				BigDecimal weight = new BigDecimal(0.0);
				Integer itemnum = 0;
				BigDecimal cost_item = new BigDecimal(0.0);
				BigDecimal pmt_order = new BigDecimal(0.0);
				for (CartGoods cartGoods : cartGoodss) {
					//计算该订单总价
					total_amount = total_amount.add(new BigDecimal(cartGoods.getPrice()).multiply(new BigDecimal(cartGoods.getQuantity())));
					//计算重量
					weight = weight.add(new BigDecimal(cartGoods.getWeight()).multiply(new BigDecimal(cartGoods.getQuantity())));
					//计算订单数量
					itemnum += Integer.parseInt(cartGoods.getQuantity());
					//计算商品总金额
					cost_item = cost_item.add(new BigDecimal(cartGoods.getG_price()).multiply(new BigDecimal(cartGoods.getQuantity())));
					//获取订单优惠费用
					BigDecimal pmt = cartGoods.getPmt();
					pmt_order = pmt_order.add(pmt);
				}
				BigDecimal cost_freight = new BigDecimal(0.0);
				if(index == 1){
					cost_freight = total_cost_freight;
				}else {
					cost_freight =  new BigDecimal(0.0);
				}
				total_amount = total_amount.add(cost_freight).subtract(pmt_order);
				
				String orderId = genOrderId();
				if(order_rel==null){
					order_rel = orderId;
				}
				log.info("orderId===>>"+orderId+"<<====order_rel===>>"+order_rel);
				order.setOrder_id(orderId);//*
				order.setOrder_rel(order_rel);//*
				order.setIs_overseas(areatype);//1大陆订单,2保税订单,3海外订单 4 6大仓
				order.setTotal_amount(total_amount);//*
				order.setFinal_amount(total_amount);//*
				order.setWeight(weight);//重量
				order.setItemnum(itemnum);//*
				order.setCost_item(cost_item);//商品总金额 g  ""
				order.setScore_g(new BigDecimal(total_amount.intValue()));//获得积分   (商品总金额-商品促销优惠)??
				order.setPmt_goods(new BigDecimal(0.00));//商品促销优惠
				order.setPmt_order(pmt_order);//订单促销优惠
				order.setWipe_price(new BigDecimal(0.00));//线下价格（不收款）
				order.setCost_freight(cost_freight);//物流运费
             	if("2".equals(areatype)||"3".equals(areatype)){
					order.setIs_tax("false");
					order.setTax_type("false");
					order.setTax_company("");
				}else{
					order.setIs_tax(is_tax);
					order.setTax_type(tax_type);
					order.setTax_company(tax_comany);
				}
				if(StringUtils.isBlank(checkSum.getType())){
					order.setSource("android");//*
				}else{
					if(checkSum.getType().toLowerCase().equals("android")){
						order.setSource("android");
					}else if(checkSum.getType().toLowerCase().equals("iphone")){
						order.setSource("iphone");
					}else if(checkSum.getType().toLowerCase().equals("ipad")){
						order.setSource("iphone");
					}else if(checkSum.getType().toLowerCase().equals("web")) {
						order.setSource("pc");
					}else if(checkSum.getType().toLowerCase().equals("h5wap")) {
						order.setSource("h5wap");
					}else{
						order.setSource("pc");
					}
				}
				source = order.getSource();
				orderDao.saveOrder(order);//保存订单表
				this.saveOrderLog(order);//保存订单日志表
				
				for (CartGoods cartGoods : cartGoodss) {
					double quantity = Double.parseDouble(cartGoods.getQuantity());
					double price = Double.parseDouble(cartGoods.getPrice());
					double amount = price*quantity;
					double weightx = Double.parseDouble(cartGoods.getWeight());
					OrderObjects orderObjects = new OrderObjects();
					orderObjects.setOrder_id(orderId);
					orderObjects.setGoods_id(cartGoods.getGoods_id());
					orderObjects.setBn(cartGoods.getBn());
					orderObjects.setName(cartGoods.getName());
					orderObjects.setPrice(price);
					orderObjects.setAmount(amount);
					orderObjects.setQuantity(quantity);
					orderObjects.setWeight(weightx);
					if("normal".equals(cartGoods.getGoods_type().toString())){
						orderObjects.setObj_type("goods");
						orderObjects.setObj_alias("商品区块");
						orderObjects.setScore(amount);
					}
					if("gift".equals(cartGoods.getGoods_type().toString())){
						orderObjects.setObj_type("gift");
						orderObjects.setObj_alias("赠品区块");
						orderObjects.setScore(0);
					}
					Integer objId = null;
					//保存订单商品表
					try {
						
						 objId = this.saveOrderObjects(orderObjects);
					} catch (Exception e) {
						e.printStackTrace();
					}
					OrderItems orderItems = new OrderItems();
					orderItems.setOrder_id(orderId);
					orderItems.setObj_id(objId.toString());
					orderItems.setProduct_id(cartGoods.getProduct_id());
					orderItems.setGoods_id(cartGoods.getGoods_id());
					orderItems.setBn(cartGoods.getBn());
					orderItems.setName(cartGoods.getName());
					orderItems.setPrice(price);
					if("1".equals(cartGoods.getGoodsflag())){
						orderItems.setIs_limitbuy("1");//是否特卖
					}else{
						orderItems.setIs_limitbuy("0");
					}
					orderItems.setUsed_store("0");//压单状态
					orderItems.setG_price(Double.parseDouble(cartGoods.getG_price()));
					orderItems.setAmount(amount);
					orderItems.setWeight(weightx);
					orderItems.setNums(quantity);
					orderItems.setType_id(cartGoods.getType_id());//货品类型id
					orderItems.setCost(Double.parseDouble(cartGoods.getCost()));//货品成本
					if("normal".equals(cartGoods.getGoods_type().toString())){
						orderItems.setItem_type("product");
						orderItems.setScore(amount);
					}
					if("gift".equals(cartGoods.getGoods_type().toString())){
						orderItems.setItem_type("gift");
						orderItems.setScore(0);
					}
					double pmt_price = cartGoods.getPmt().doubleValue();
					orderItems.setPmt_price(pmt_price);
					//保存订单货品表
					Integer item_id = saveOrderItems(orderItems);
//					//保存订单商品使用优惠券表
//					for (CouponsNew couponsNew : couponsNews) {
//						String cpns_id = couponsNew.getCpns_id();
//						Map map = new HashMap();
//						map.put("cpns_id", cpns_id);
//						map.put("goods_id", cartGoods.getGoods_id());
//						map.put("product_id", cartGoods.getProduct_id());
//						map.put("item_id", item_id);
//						orderDao.saveCouponsItemRel(map);
//					}
				}
			}
		}
		
		List<String> memc_codes = new ArrayList<String>();
		//保存优惠券使用记录
		if(couponsNews!=null && couponsNews.size()>0){
			for (CouponsNew couponsNew: couponsNews) {
				Couponlog4User couponlog = new Couponlog4User();
				couponlog.setCpns_id(couponsNew.getCpns_id());
				couponlog.setCpns_name(couponsNew.getCpns_name());
				couponlog.setCpns_type(couponsNew.getCpns_type());
				couponlog.setMember_id(order.getMember_id());
				String memc_code = couponsNew.getMemc_code();
				memc_codes.add(memc_code);
				couponlog.setMemc_code(memc_code);
				couponlog.setOrder_id(order_rel);
				couponlog.setTotal_amount(total_total_amount);
				couponlog.setUsetime(order.getCreatetime());
				couponsDaoImpl.saveLogCouponsRef(order_rel, couponsNew.getMemc_code());
				couponsDaoImpl.saveLogCouponsUser(couponlog);
			}
		}
		
		//更新用户优惠券使用
		orderDao.updateUserCoupons(memc_codes,order.getMember_id(),true);
		OrderParameter order2 = new OrderParameter();
		order2.setOrder_rel(order_rel);
		order2.setTotal_amount(total_total_amount);
		order2.setMember_id(order.getMember_id());
		order2.setPayment(order.getPayment());
		order2.setSource(source);
		order2.setCost_freight(total_cost_freight);
		order2.setCost_item(total_cost_item);
		order2.setPmt_goods(total_pmt_goods);
		order2.setPmt_order(total_pmt_order);
		order2.setMemc_code(order.getMemc_code());
		return order2;
	}

	@Override
	public ResultObject syncBiku(OrderParameter order,Checksum checkSum) throws Exception{
		//List<String[]> tempParams = new ArrayList<String[]>();
		String order_rel = order.getOrder_rel();
		List<Map<String,Object>> list = orderDao.getOrderListByOrderRel(order_rel);
		if(list!=null && list.size()>0){
			for(Map<String,Object> map:list){
				String[] areas=(map.get("ship_area")==null?",,":map.get("ship_area").toString()).split(",");
				String mid=map.get("member_bk_id").toString();
				String total=new BigDecimal(map.get("cost_item").toString())//总金额
					.add(new BigDecimal(map.get("cost_freight").toString()))//配送费用
					.add(new BigDecimal(map.get("pmt_goods").toString()))//商品促销优惠
					.multiply(new BigDecimal("100")).longValue()+"";//转换成分
				String cash=new BigDecimal(map.get("total_amount").toString())//总付金额
							.subtract(new BigDecimal(map.get("cost_freight").toString()))
							.multiply(new BigDecimal("100")).longValue()+"";//80
				
				String spec = new BigDecimal(map.get("pmt_order").toString())
							.add(new BigDecimal(map.get("pmt_goods").toString()))
							.multiply(new BigDecimal("100")).longValue()+"";//转换成分
				
				String spec2= new BigDecimal(map.get("pmt_order").toString())
							.add(new BigDecimal(map.get("pmt_goods").toString()))
							.multiply(new BigDecimal("100")).longValue()+"";//转换成分
				
				String name=map.get("ship_name")==null?"":map.get("ship_name").toString();
				String note="";
				String iname="";
				String idescri="";
				String integral="0";
				String uintegral="0";
				
				String cash_need=new BigDecimal(map.get("total_amount").toString())
		 				.subtract(new BigDecimal(map.get("cost_freight").toString()))
		 				.multiply(new BigDecimal("100")).longValue()+"";//80
				
				String cash_receive="0";
				String addr_phone=map.get("ship_mobile")==null?"":map.get("ship_mobile").toString();
				String[] proName = {"","",""}; 
				String[] areasBikuId = {"","",""}; 
				for(int i=0;i<areas.length; i++){
					Area are = areaService.getRegionById(areas[i].trim());//取的省市区实体
					if(null != are){
						proName[i]= are.getLocal_name();
						areasBikuId[i] = are.getBk_id();
					}
				}
				//log.info("areasBikuId--------->>>"+areasBikuId);
				String pvc_id=areasBikuId[0];
				String local_id="";
				if(areasBikuId[1] !=null){
					 local_id=areasBikuId[1];
				}
				String county_id=areasBikuId[2];
				String from_sto="";
				String addr=proName[0]+proName[1]+proName[2]+(map.get("ship_addr")==null?"":map.get("ship_addr").toString());
				List<Map<String,Object>> items = orderDao.getOrderItemsByOrderId(map.get("order_id").toString());
				
				long totalAmount =0;
				for(Map<String,Object> item:items){
					 if(item.get("buy_type").toString().equals("购买")){
						 totalAmount += new BigDecimal(item.get("amount").toString()).multiply(new BigDecimal("100")).longValue(); 
					 }else{
						 item.put("amt", "1");
						 item.put("price0", "0");
						 item.put("amount", "0");
						 item.put("spec", "0");
					 }
				}
				BigDecimal lilv = new BigDecimal(0.0);
				if(Integer.parseInt(spec)!=0){
					lilv = new BigDecimal(spec).divide(new BigDecimal(totalAmount*1.0),10,BigDecimal.ROUND_HALF_DOWN);
				}
				long temp_sum=0;
				Collections.sort(items, new Comparator(){
						public int compare(Object arg0, Object arg1) {  
							Map<String, Object> map1 = (Map<String, Object>)arg0;  
							Map<String, Object> map2 = (Map<String, Object>)arg1;  
							String map1String =  map1.get("amount").toString();  
							String map2String = map2.get("amount").toString();  
							return (map1String.toLowerCase()).compareTo(map2String.toLowerCase());  
						}  
					});
				 Object[] goods_details=new Object[items.size()];//商品明细	 
				 for(int i=0;i<items.size();i++){
					Map m = items.get(i);
					log.info("biku ==product---->BK_ID--------->>>"+ m.get("product_id").toString());
					if(i==0){
						from_sto = m.get("area_type").toString();
					}
					long temp_spec = new BigDecimal(m.get("amount").toString()).multiply(new BigDecimal("100")).multiply(lilv).longValue();
					if(i==items.size()-1){
						temp_spec = Long.parseLong(spec)-temp_sum;
					}
					temp_sum+=temp_spec;
					Map<String,String> goods = new HashMap<String,String>();
					goods.put("product_id", m.get("product_id").toString());
					goods.put("buy_type", m.get("buy_type").toString());
					goods.put("amt", new BigDecimal(m.get("amt").toString()).longValue()+"");
					goods.put("price0", new BigDecimal(m.get("price0").toString()).multiply(new BigDecimal("100")).longValue()+"");
					goods.put("amount", new BigDecimal(m.get("amount").toString()).multiply(new BigDecimal("100")).longValue()-temp_spec+"");
					goods.put("spec", temp_spec+"");
					goods.put("uintegral", "0");
					goods.put("integral", "0");
					goods_details[i]=goods;
				}
				String shipping_fee="0";	 
				if(new BigDecimal(map.get("cost_freight").toString()).doubleValue()>0.0){
					String cost_freight = map.get("cost_freight").toString();
					shipping_fee = Integer.toString(new BigDecimal(cost_freight).intValue());
					//shipping_fee="15";
				}else{
					shipping_fee="0";
				} 
				//支付宝交易号
				// String w_no=payparameter.getTrade_no();
				if(map.get("is_tax").equals("false")){
					iname="否";
				}else{
					iname="是";
					if(map.get("tax_type").equals("company")){
						idescri=map.get("tax_company")==null?"":map.get("tax_company").toString();
					}else if(map.get("tax_type").equals("personal")){
						idescri="个人";
					}else{
						idescri="个人";
					}
				}
				String ship_time = map.get("ship_time")==null?"":map.get("ship_time").toString().replace(",","").trim();
				if(ship_time.equals("2")){
					note="只在工作日送（节假日不送）";
				}else if(ship_time.equals("3")){
					note="只在节假日送（工作日不送）";
				}else{
					note="节假日/工作日都可以";
				}
				String pay_id = "";
				String pay_type="";
				if("alipay".equals(order.getPayment()))
				{
					pay_id = "2";
					pay_type="0";
				}else if("unionpay".equals(order.getPayment()))
				{
					pay_id = "2";
					pay_type="1";
				}else{
					
					pay_id = "1";
					pay_type="0";
				}
				
				String no = map.get("order_id").toString();
				String url = "";
				String hxl_type = checkSum.getHxl_type();
				String webService = new SystemConfig("clientService").getProperty(hxl_type);
				SystemConfig systemConfig = new SystemConfig(webService);
//				if(Constant.IVALUE3_FLAG){
					url = systemConfig.getProperty("saveOrder_url")+"?sign=&api_key=&nonce="+"&version=&sign_method=&timestamp=&type="+map.get("source").toString()
							+"&sid=&nowsid=&uno=1&order_class=2&status=0&pay_id="+pay_id+"&pay_type="+pay_type+"&from_where=wbs_hjk&order_auto_yn=1&orderfrom=6&postal=&sno=73604"
							+"&mid="+mid+"&total="+total+"&cash="+cash+"&spec="+spec+"&spec2="+spec2+"&name="+name+"&pvc_id="+pvc_id
							+"&local_id="+local_id+"&county_id="+county_id+"&note="+note+"&iname="+iname+"&idescri="+idescri+"&integral="+integral
							+"&uintegral="+uintegral+"&cash_need="+cash_need+"&cash_receive="+cash_receive+"&addr_phone="+addr_phone
							+"&addr="+addr+"&shipping_fee="+shipping_fee+"&from_sto="+from_sto+"&no="+no;
//				}else{
//					url = systemConfig.getProperty("saveOrder_url")+"?sign=&api_key=&nonce="+"&version=&sign_method=&timestamp=&type="+map.get("source").toString()
//							+"&sid=&nowsid=&uno=1&order_class=2&status=0&pay_id="+pay_id+"&pay_type="+pay_type+"&from_where=wbs_hjk&order_auto_yn=1&orderfrom=6&postal=&sno=73604"
//							+"&mid="+mid+"&total="+total+"&cash="+cash+"&spec="+spec+"&spec2="+spec2+"&name="+name+"&pvc_id="+pvc_id
//							+"&local_id="+local_id+"&county_id="+county_id+"&note="+note+"&iname="+iname+"&idescri="+idescri+"&integral="+integral
//							+"&uintegral="+uintegral+"&cash_need="+cash_need+"&cash_receive="+cash_receive+"&addr_phone="+addr_phone
//							+"&addr="+addr+"&shipping_fee="+shipping_fee+"&from_sto="+from_sto;
//				}
				log.info("url==>>"+url);
			    MultiValueMap<String, Object> mvm = new LinkedMultiValueMap<String, Object>();
			    mvm.add("goods_details", StringUtil.stringfromUtf8Toiso(JSONArray.fromObject(goods_details).toString())); 
			    
				String message = restTemplate.postForObject(StringUtil.stringfromUtf8Toiso(url),mvm, String.class,  new HashMap());
				log.info("orderServiceImpl syncBiku====================>>>" + message.toString());
				JSONObject json = JSONObject.fromObject(message);
				if(!json.getString("flag").equals("1")){
					
					//同步失败插入异常表
					ErrorMsg msg = new ErrorMsg();
					msg.setPk_id(no);
					msg.setEp_result(json.getString("result"));
					msg.setEp_time(new Date());
					msg.setEp_type("订单同步比酷失败");
					msg.setEp_data(message);
					log.info("orderServiceImpl saveErrorMsg----->保存同步失败日志");
					orderDao.saveErrorMsg(msg);
					
					log.error("同步比酷失败提示信息--------->>>"+json.getString("result"));
//					if(Constant.IVALUE3_FLAG){
						cancelLocalOrder(checkSum, order_rel, order.getMember_id(), "2","0");
						return new ResultObject("-1","选购商品无货，请重新编辑购物车!",null);
//					}else{
//						//取消比酷订单
//						cancelOrder(checkSum, order_rel, order.getMember_id(), "2","0");
//						return new ResultObject("-1","选购商品无货，请重新编辑购物车!",null);
//					}
				}else{
					// 修改压单状态2
					//非海外的订单全部修改为2
					List<String> is_limitbuy = new ArrayList<String>();
					is_limitbuy.add("0");is_limitbuy.add("1");
					List<String> is_overseas = new ArrayList<String>();
					is_overseas.add("1");is_overseas.add("2");is_overseas.add("4");
					updateUsed_storeByOrder_rel(order_rel, "2", is_limitbuy, is_overseas);
					JSONObject resultObject = JSONObject.fromObject(json.get("resultObject"));
					Map ma = new HashMap();
					String order_id = map.get("order_id").toString();
					ma.put("order_id", order_id);
//					if(Constant.IVALUE3_FLAG){
						String ord_id = resultObject.getString("ord_id");
						ma.put("ord_id", ord_id);
						ma.put("ord_no", ord_id);
//					}else{
//						String ord_id = resultObject.getString("ord_id");
//						String ord_no = resultObject.getString("ord_no");
//						ma.put("ord_id", ord_id);
//						ma.put("ord_no", ord_no);
//					}
					orderDao.updateOrderBkid(ma);//更新订单比酷id与no
					//对已成功同步比酷的订单进行记录
					//String[] tempParam = {ord_id,ord_no,order.getSource()};
					//tempParams.add(tempParam);
				}
			}
		}
		return null;
	}
	
	/**
	 * 不分单处理（包含赠品与购买商品的不属于同一仓库）
	 * @param order
	 * @param orderFromSto
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unused")
	@Deprecated
	private String notSeparateOrder(OrderParameter order,List<Map<String,Object>> orderFromSto,List<CartGoods> gift,Checksum checkSum) throws Exception{
		BigDecimal totalAmount=order.getTotal_amount();
		BigDecimal costItem = order.getCost_item();
		String prod_id="";
		for(int k=0;k<orderFromSto.size();k++){
			Map<String,Object> ms = orderFromSto.get(k);
			order.setFrom_sto(Integer.parseInt(ms.get("from_sto").toString()));
			if(k==0){//商品，非赠品
				order.setOrder_id(genOrderId());
				if(order.getPayment().equals("offline")){
					order.setTotal_amount(new BigDecimal(totalAmount.intValue()));
					order.setFinal_amount(new BigDecimal(totalAmount.intValue()));
					order.setWipe_price(totalAmount.subtract(new BigDecimal(totalAmount.intValue())));
					order.setCost_item(costItem.subtract(order.getPmt_goods()).subtract(order.getWipe_price()));
				}else{
					order.setTotal_amount(totalAmount);
					order.setFinal_amount(totalAmount);
					order.setCost_item(costItem.subtract(order.getPmt_goods()));
				}
				order.setScore_g(costItem.subtract(order.getPmt_goods()));
				
				if(order.getPmt_order().subtract(order.getPmt_goods()).doubleValue()>=order.getCost_freight().doubleValue()){
					order.setPmt_order(order.getPmt_order().subtract(order.getPmt_goods()).subtract(order.getCost_freight()));
					order.setCost_freight(new BigDecimal(0.0));
				}else{
					order.setPmt_order(order.getPmt_order().subtract(order.getPmt_goods()));
				}
				order.setOrder_rel(order.getOrder_id());
			}
			List<Map<String,Object>> list = this.getOrderGoods(order);
			for(Map<String,Object> map :list){
				/*if(!map.get("price").toString().equals(map.get("g_price").toString())){
					map.put("price", map.get("g_price"));
				}*/
				prod_id +=(map.get("biku_id")!=null?map.get("biku_id").toString():"")+",";
				if(gift!=null && gift.size()>0){
					for(CartGoods cg:gift){
						if(map.get("product_id").toString().equals(cg.getProduct_id())){
								map.put("goods_type", "gift");
						}
					}
				}
			}
			BigDecimal weight=new BigDecimal(0.0);
			int itemnum=0;
			if(k==0){
				for(Map<String,Object> map :list){
					if("normal".equals(map.get("goods_type").toString())){
						weight = weight.add(new BigDecimal(map.get("weight").toString()));
						itemnum++;
					}
				}
				order.setItemnum(itemnum);
				order.setWeight(weight);
				if(StringUtils.isBlank(checkSum.getType())){
					order.setSource("android");
				}else{
					if(checkSum.getType().toLowerCase().equals("android")){
						order.setSource("android");
					}else if(checkSum.getType().toLowerCase().equals("iphone")){
						order.setSource("iphone");
					}else if(checkSum.getType().toLowerCase().equals("ipad")){
						order.setSource("iphone");
					}else{
						order.setSource("android");
					}
				}
				orderDao.saveOrder(order);//保存订单表
				this.saveOrderLog(order);//保存订单日志表
			}
			this.saveProducts(checkSum,order, list);
		}
		prod_id=prod_id.substring(0,prod_id.length()-1);
		String str = goodsStock(prod_id,order,checkSum);
		if(StringUtils.isBlank(str)){
			return null;
		}else{
			throw new OrderException(str);
		}
		//return null;
	}
	
	/**
	 * 购买商品不同仓库的分单处理
	 * @param order
	 * @param orderFromSto
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unused")
	@Deprecated
	private String separateOrder(OrderParameter order,List<Map<String,Object>> orderFromSto,List<CartGoods> gift,Checksum checkSum) throws Exception{
		BigDecimal resultTotalAmount = new BigDecimal("0.0");
		BigDecimal totalAmount=order.getTotal_amount();
		BigDecimal costItem = order.getCost_item();
		BigDecimal pmt_goods = order.getPmt_goods();
		BigDecimal price =new BigDecimal(0.0) ;
		BigDecimal g_price =new BigDecimal(0.0) ;
		BigDecimal pmtOrder = order.getPmt_order();
		BigDecimal costFreight = order.getCost_freight();
		String order_id = "";
		BigDecimal relPmtOrder= pmtOrder.subtract(pmt_goods);
		BigDecimal lv = relPmtOrder.divide(costItem.subtract(pmt_goods),10,BigDecimal.ROUND_HALF_DOWN);
		for(int k=0;k<orderFromSto.size();k++){
			Map<String,Object> ms = orderFromSto.get(k);
			order.setFrom_sto(Integer.parseInt(ms.get("from_sto").toString()));
			order.setOrder_id(genOrderId());
			if(k==0){
				order_id = order.getOrder_id();
				price = new BigDecimal(ms.get("price").toString());
				g_price = new BigDecimal(ms.get("g_price").toString());
				order.setOrder_rel(order_id);
				order.setPmt_order(new BigDecimal(price.multiply(lv).intValue()));
				order.setCost_freight(new BigDecimal(0.0));
				if(order.getPayment().equals("offline")){
					order.setTotal_amount(new BigDecimal(price.subtract(order.getPmt_order()).intValue()));
					order.setFinal_amount(new BigDecimal(price.subtract(order.getPmt_order()).intValue()));
					order.setWipe_price(price.subtract(new BigDecimal(price.intValue())));
					order.setPmt_goods(g_price.subtract(price));
					order.setCost_item(price.subtract(order.getWipe_price()));
				}else{
					order.setTotal_amount(price.subtract(order.getPmt_order()));
					order.setFinal_amount(price.subtract(order.getPmt_order()));
					order.setPmt_goods(g_price.subtract(price));
					order.setCost_item(price);
				}
				resultTotalAmount = resultTotalAmount.add(order.getTotal_amount());
				order.setScore_g(g_price);
				
			}else{
				order.setOrder_rel(order_id);
				/*if( totalAmount.subtract(price).doubleValue()<=new BigDecimal(0.0).doubleValue() 
					|| costItem.subtract(price).doubleValue()<=new BigDecimal(0.0).doubleValue()){
					log.error("OrderServiceImpl separateOrder=====>应付金额有误");
					return "应付金额有误";
				}*/
				if(order.getPayment().equals("offline")){
					order.setTotal_amount(new BigDecimal(totalAmount.subtract(price.subtract(order.getPmt_order())).intValue()));
					order.setFinal_amount(new BigDecimal(totalAmount.subtract(price.subtract(order.getPmt_order())).intValue()));
					order.setWipe_price(totalAmount.subtract(price.subtract(order.getPmt_order())).subtract(new BigDecimal(totalAmount.subtract(price.subtract(order.getPmt_order())).intValue())));
					order.setPmt_goods(pmt_goods.subtract(order.getPmt_goods()));
					order.setCost_item(costItem.subtract(g_price).subtract(order.getPmt_goods()).subtract(order.getWipe_price()));
				}else{
					order.setTotal_amount(totalAmount.subtract(price.subtract(order.getPmt_order())));
					order.setFinal_amount(totalAmount.subtract(price.subtract(order.getPmt_order())));
					order.setPmt_goods(pmt_goods.subtract(order.getPmt_goods()));
					order.setCost_item(costItem.subtract(g_price).subtract(order.getPmt_goods()));
				}
				resultTotalAmount = resultTotalAmount.add(order.getTotal_amount());
				order.setScore_g(costItem.subtract(g_price));
				if(pmtOrder.subtract(pmt_goods).subtract(new BigDecimal(price.multiply(lv).intValue())).doubleValue()>=costFreight.doubleValue()){
					order.setPmt_order(pmtOrder.subtract(costFreight).subtract(pmt_goods).subtract(new BigDecimal(price.multiply(lv).intValue())));
					order.setCost_freight(new BigDecimal(0.0));
				}else{
					order.setPmt_order(pmtOrder.subtract(pmt_goods).subtract(new BigDecimal(price.multiply(lv).intValue())));
					order.setCost_freight(costFreight);
				}
			}
			List<Map<String,Object>> list = this.getOrderGoods(order);
			if(gift!=null && gift.size()>0){
				for(Map<String,Object> map :list){
					for(CartGoods cg:gift){
						if(map.get("product_id").toString().equals(cg.getProduct_id())){
							map.put("goods_type", "gift");
						}
					}
				}
			}
			BigDecimal weight=new BigDecimal(0.0);
			int itemnum=0;
			for(Map<String,Object> map :list){
				if("normal".equals(map.get("goods_type").toString())){
					weight = weight.add(new BigDecimal(map.get("weight").toString()));
					itemnum++;
				}
			}
			order.setItemnum(itemnum);
			order.setWeight(weight);
			if(StringUtils.isBlank(checkSum.getType())){
				order.setSource("android");
			}else{
				if(checkSum.getType().toLowerCase().equals("android")){
					order.setSource("android");
				}else if(checkSum.getType().toLowerCase().equals("iphone")){
					order.setSource("iphone");
				}else if(checkSum.getType().toLowerCase().equals("ipad")){
					order.setSource("iphone");
				}else{
					order.setSource("android");
				}
			}
			orderDao.saveOrder(order);//保存订单表
			this.saveOrderLog(order);//保存订单日志表
			this.saveProducts(checkSum,order, list);
		}
		order.setTotal_amount(resultTotalAmount);
		return null;
	}
	/**
	 * 判断是否有库存
	 * @param list
	 * @param cp
	 * @param checkSum
	 * @return
	 */
	private String goodsStock(String prod_id, OrderParameter cp,Checksum checkSum) throws Exception{
		GoodsStockByIdParameters param = new GoodsStockByIdParameters();
		param.setProduct_id(prod_id);
		param.setPvc_id(cp.getPvc_id());
		param.setCountry_id(cp.getCountry_id());
		param.setLocal_id(cp.getLocal_id());
		String json = logisticsService.queryGoodsStockById(checkSum, param);//查询库存
		JSONObject returnJson = JSONObject.fromObject(json);
		if(returnJson.getInt("flag")==0){
			String error = returnJson.getString("error");
			log.error("OrderController productStatus 比酷返回错误信息：" + error);
			return "购物车中存在无货商品";
		}else{
			JSONObject msgJson = JSONObject.fromObject(returnJson.getString("msg"));
			JSONObject prodstock =JSONObject.fromObject(msgJson.getString("prodstock"));
			String[] prod_ids = prod_id.split(",");
			for(int i=0;i<prod_ids.length;i++){
				JSONObject o =JSONObject.fromObject(prodstock.get(prod_ids[i].trim()));
				int order_ok = o.getInt("order_ok");//接单属性 0：无限制，1、仓库接单，2、预采购量接单
				int amt = o.getInt("amt");//可用库存量
				int NOTICE_QTY = o.getInt("NOTICE_QTY");//通知单压单数量
				int PO_SCHD_QTY = o.getInt("PO_SCHD_QTY");//采购在途数量
				if(order_ok==0 || amt>0 ){
					continue;
				}else{
					if(PO_SCHD_QTY-NOTICE_QTY>=0){
						continue;
					}else{
						log.error("OrderServiceImpl goodsStock=====>购物车中存在无货商品");
						return "购物车中存在无货商品";
					}
				}
			}
			return "";
		}
	
	}
	/**
	 * 保存订单商品、订单货品、特卖商品
	 * @param order
	 * @param list
	 * @throws Exception
	 */
	private void saveProducts(Checksum checkSum,OrderParameter order,List<Map<String,Object>> list)throws Exception{
		String[] quantity = order.getQuantity().split(",");
		//String[] goods = order.getGoods_id().split(",");
		String[] pro = order.getProduct_id().split(",");
		List goodsProm = new ArrayList();
		for(Map<String,Object> cg :list){
			Map maps = new HashMap();
	    	maps.put("goods_id", cg.get("goods_id"));
	    	maps.put("price", cg.get("g_price"));
	    	goodsProm.add(maps);
		}
		
		String hxl_type = checkSum.getHxl_type();
		String webService = new SystemConfig("clientService").getProperty(hxl_type);
		String goodsJson = HttpRequest.sendPost(new SystemConfig(webService).getProperty("goods_prom_url"), "goods_info="+JSONArray.fromObject(goodsProm));
		JSONObject returngoodsJson = JSONObject.fromObject(goodsJson);
		log.info("OrderController saveProducts=====>商品促销信息:"+goodsJson);
		if("1".equals(returngoodsJson.getString("code"))){
			if(returngoodsJson.get("data")==null||"".equals(returngoodsJson.get("data"))){
			}else{
				JSONArray object = JSONArray.fromObject(returngoodsJson.getString("data"));
				for(int i=0;i<object.size();i++){
					JSONObject o = object.getJSONObject(i);
					for(Map<String,Object> cg :list){
						if(cg.get("goods_id").toString().equals(o.getString("goods_id"))){
							cg.put("pro_price",o.getString("price"));
							break;
						}
					}
				}
			}
		}
		for(Map<String,Object> map :list){
			OrderObjects obj = new OrderObjects();
			obj.setOrder_id(order.getOrder_id());
			obj.setGoods_id(map.get("goods_id").toString());
			obj.setBn(map.get("bn").toString());
			obj.setName(map.get("name").toString());
			obj.setPrice(Double.parseDouble(map.get("g_price").toString()));
			for(int i=0;i<pro.length;i++){
				if(pro[i].trim().equals(map.get("product_id").toString())){
					obj.setQuantity(Double.parseDouble(quantity[i].trim()));
					obj.setAmount(Double.parseDouble(map.get("price").toString())* obj.getQuantity());
					obj.setWeight(Double.parseDouble(map.get("weight").toString())* obj.getQuantity());
					obj.setScore(Double.parseDouble(map.get("g_price").toString())* obj.getQuantity());
				}	
			}
			if("normal".equals(map.get("goods_type").toString())){
				obj.setObj_type("goods");
				obj.setObj_alias("商品区块");
			}
			if("gift".equals(map.get("goods_type").toString())){
				obj.setObj_type("gift");
				obj.setObj_alias("赠品区块");
				obj.setScore(0);
			}
			Integer objId = this.saveOrderObjects(obj);
			
			//如果是特卖商品，保存到特卖订单表
			if("1".equals(map.get("limitbuy").toString())){
				this.saveLimitBuyObject(obj);
			}
			
			List<Map<String,Object>> itemsList = this.getOrderItems(map.get("product_id").toString());
			for(Map<String,Object> ma :itemsList){
				OrderItems item = new OrderItems();
				item.setObj_id(objId+"");
				item.setOrder_id(order.getOrder_id());
				item.setGoods_id(ma.get("goods_id").toString());
				item.setProduct_id(ma.get("product_id").toString());
				item.setBn(ma.get("bn").toString());
				item.setG_price(Double.parseDouble(ma.get("g_price").toString()));
				item.setName(ma.get("name").toString());
				item.setType_id(ma.get("type_id").toString());
				item.setCost(Double.parseDouble(ma.get("cost").toString()));
				if(map.get("goods_id").toString().equals(item.getGoods_id())){
					if(map.get("pro_price")==null){
						item.setPrice(Double.parseDouble(ma.get("price").toString()));
					}else{
						item.setPrice(Double.parseDouble(map.get("pro_price").toString()));
					}
				}
				
				item.setWeight(Double.parseDouble(ma.get("weight").toString()));
				item.setScore(Double.parseDouble(ma.get("price").toString()));
				for(int i=0;i<pro.length;i++){
					if(pro[i].trim().equals(ma.get("product_id").toString())){
						item.setNums(Double.parseDouble(quantity[i].trim()));
						item.setAmount(Double.parseDouble(ma.get("price").toString())* item.getNums());
					}	
				}
				if("normal".equals(ma.get("goods_type").toString())){
					item.setItem_type("product");
				}
				if("gift".equals(map.get("goods_type").toString())){
					item.setItem_type("gift");
					item.setScore(0);
				}
				saveOrderItems(item);
			}
		}
	}
	/**
	 * 随机生成订单号
	 * @return
	 * @throws Exception
	 */
	private String genOrderId() throws Exception{
		int code = (int)(Math.random()*99999);//随机生成1-5位的数字[0-99998]
		String orderId= "";
		boolean bol=false;
		do{
			if(code==99999){
				code=0;
			}
			code++;
			orderId = code+"";
			for(int i=orderId.length()+1;i<=5;i++){
				orderId="0"+orderId;
			}
			SimpleDateFormat sdf = new SimpleDateFormat("yyMMddHHmm");
			orderId=sdf.format(new Date())+orderId;
			bol = this.getOrderId(orderId);
		}while(bol);
		return orderId;
	}

	@Override
	public Address getAddress(OrderParameter order) throws Exception {
		return orderDao.getAddress(order);
	}

	@Override
	public List<Map<String, Object>> getOrderGoods(OrderParameter order)
			throws Exception {
		return orderDao.getOrderGoods(order);
	}

	@Override
	public Integer saveOrderObjects(OrderObjects obj) throws Exception {
		return orderDao.saveOrderObjects(obj);
	}

	@Override
	public List<Map<String, Object>> getOrderItems(String product_id) throws Exception {
		return orderDao.getOrderItems(product_id);
	}

	@Override
	public Integer saveOrderItems(OrderItems item) throws Exception {
		return orderDao.saveOrderItems(item);
	}

	@Override
	public void saveOrderLog(OrderParameter order) throws Exception {
		
		orderDao.saveOrderLog(order);
	}
	//根据商品id获取商品价格和所在的仓库
	@Override
	@Deprecated
	public List<Map<String, Object>> getOrderFromSto(List<CartGoods> list,OrderParameter order,List<CartGoods> gift)
			throws Exception {
		String[] quantity = order.getQuantity().split(",");
		for (int i=0; i<quantity.length ;i++) {
			quantity[i] = quantity[i].trim();
		}
		String[] products = order.getProduct_id().split(",");
		for (int i=0; i<products.length ;i++) {
			products[i] = products[i].trim();
		}
		List<Map<String, Object>> listFromSto = null;
		double price1 = 0.0;
		double price2 = 0.0;
		double g_price1 = 0.0;
		double g_price2 = 0.0;
		List<String> giftProduct_ids = new ArrayList<String>();
		if (gift != null && gift.size() > 0) {
			for (CartGoods cg : gift) {
				giftProduct_ids.add(cg.getProduct_id());
			}
		}
		for(CartGoods cartGoods :list){//分单遍历
			if(giftProduct_ids.size()>0){
				if(giftProduct_ids.contains(cartGoods.getProduct_id())){
					cartGoods.setPrice("0");
					cartGoods.setQuantity("1");
					cartGoods.setG_price("0");
				}
			}
//			if(gift!=null && gift.size()>0){
//				for(CartGoods cg : gift){
//					if(map.getProduct_id().equals(cg.getProduct_id())){//赠品
//						map.setPrice("0");
//						map.setQuantity("1");
//						map.setG_price("0");
//					}
//				}
//			}
			int index = Arrays.binarySearch(products, cartGoods.getProduct_id());
			if(index >= 0){
				Double d = Double.parseDouble(cartGoods.getPrice())*Double.parseDouble(quantity[index]);
				Double f_d = Double.parseDouble(cartGoods.getG_price())*Double.parseDouble(quantity[index]);
				cartGoods.setPrice(d.toString());//计算价格
				cartGoods.setG_price(f_d.toString());
			}
//			for(int i=0;i<products.length;i++){
//				if(products[i].trim().equals(map.getProduct_id())){
//					Double d = Double.parseDouble(map.getPrice())*Double.parseDouble(quantity[i].trim());
//					Double f_d = Double.parseDouble(map.getG_price())*Double.parseDouble(quantity[i].trim());
//					map.setPrice(d.toString());//计算价格
//					map.setG_price(f_d.toString());
//				}
//			}
			
			if("1".equals(cartGoods.getFrom_sto())){//属于网销的归一类
				price1 += Double.parseDouble(cartGoods.getPrice());
				g_price1 += Double.parseDouble(cartGoods.getG_price());
			}
			if("2".equals(cartGoods.getFrom_sto())){//属于四大仓的归一类
				price2 += Double.parseDouble(cartGoods.getPrice());
				g_price2 += Double.parseDouble(cartGoods.getG_price());
			}
		}
		
		if(price1==0.0 && price2==0.0){//购物车为空情况
			return null;
		}else if(price1==0.0 && price2!=0.0){//购物车购买的商品只属于四大仓库或者赠品可能是属于网销仓的，不分单处理
			listFromSto = new ArrayList<Map<String, Object>>();
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("from_sto", 2);
			map.put("price", price2);
			map.put("g_price", g_price2);
			Map<String, Object> map2 = new HashMap<String, Object>();
			map2.put("from_sto", 1);
			map2.put("price", 0);
			map2.put("g_price", 0);
			listFromSto.add(map);
			listFromSto.add(map2);
			return listFromSto;
		}else if(price1!=0.0 && price2==0.0){//购物车购买的商品只属于网销仓库或者赠品可能是属于四大仓的，不分单处理
			listFromSto = new ArrayList<Map<String, Object>>();
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("from_sto", 1);
			map.put("price", price1);
			map.put("g_price", g_price1);
			Map<String, Object> map2 = new HashMap<String, Object>();
			map2.put("from_sto", 2);
			map2.put("price", 0);
			map2.put("g_price", 0);
			listFromSto.add(map);
			listFromSto.add(map2);
			return listFromSto;
		}else{
			if(price1>=price2){//购买车购买的商品属于四大仓与网销，按价格低的顺序先保存
				listFromSto = new ArrayList<Map<String, Object>>();
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("from_sto", 2);
				map.put("price", price2);
				map.put("g_price", g_price2);
				Map<String, Object> map2 = new HashMap<String, Object>();
				map2.put("from_sto", 1);
				map2.put("price", price1);
				map2.put("g_price", g_price1);
				listFromSto.add(map);
				listFromSto.add(map2);
				return listFromSto;
			}else{//购买车购买的商品属于四大仓与网销，按价格低的顺序先保存
				listFromSto = new ArrayList<Map<String, Object>>();
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("from_sto", 1);
				map.put("price", price1);
				map.put("g_price", g_price1);
				Map<String, Object> map2 = new HashMap<String, Object>();
				map2.put("from_sto", 2);
				map2.put("price", price2);
				map2.put("g_price", g_price2);
				listFromSto.add(map);
				listFromSto.add(map2);
				return listFromSto;
			}
		}
	}

	@Override
	public void deleteCartInfo(OrderParameter order) throws Exception {
		Map map = new HashMap();
		map.put("memberId",order.getMember_id());
		String[] pro = order.getProduct_id().split(",");
		String[] good = order.getGoods_id().split(",");
		String[] obj_ident = new String[good.length];
		for(int i=0;i<good.length;i++){
			obj_ident[i]="goods_"+good[i].trim()+"_"+pro[i].trim();
		}
		map.put("obj_ident", obj_ident);
		cartDaoImpl.deleteCart(map);
	}
	@Override
	public void deleteCartCoupons(OrderParameter order) throws Exception {
		Map map = new HashMap();
		String obj_ident="coupon_"+order.getMemc_code();
		map.put("obj_ident", obj_ident);
		map.put("member_id", order.getMember_id());
		cartDaoImpl.deleteCartCoupons(map);
	}
	
	@Override
	public void saveLimitBuyObject(OrderObjects obj) throws Exception {
		orderDao.saveLimitBuyObject(obj);
	}

	//我的订单列表
	@Override
	public List<OrderList> getOrderList(Checksum checkSum,String member_id,Integer pageNum) throws Exception {
		List<Map<String,Object>> list = orderDao.getOrderList(checkSum,member_id,pageNum);
		List<OrderList> orderList = new ArrayList<OrderList>();;
		Map<String,Map<String,Object>> orderMap = new LinkedHashMap<String,Map<String,Object>>();
		for(Map<String,Object> map:list){
			orderMap.put(map.get("order_id").toString(),map);
		}
		for(String str : orderMap.keySet()){
			OrderList order = new OrderList();
			Map m = orderMap.get(str);
			order.setOrder_id(str);
			order.setOrder_rel(m.get("order_rel").toString());
			order.setCreatetime(m.get("createtime").toString());
			order.setStatus(m.get("status").toString());
			if(m.get("status").toString().contains("等待支付")){
				order.setStatusflag("1");//等待支付
			}else{
				order.setStatusflag("0");//显示订单跟踪
			}
			order.setTotal_amount(m.get("total_amount").toString());
			
			//发货地转成汉字发前台
			order.setFrom_sto(m.get("from_sto").toString());
			//发货地转成汉字发前台
			if(m.get("from_sto").toString().equalsIgnoreCase("1")){
				order.setShipping_to("");//大陆  人为定的
			}
			if(m.get("from_sto").toString().equalsIgnoreCase("4")){
				order.setShipping_to("");//6大仓
			}
			if(m.get("from_sto").toString().equalsIgnoreCase("5")){
				order.setShipping_to("");
			}
			if(m.get("from_sto").toString().equalsIgnoreCase("2")){
				order.setShipping_to("保税");
			}
			if(m.get("from_sto").toString().equalsIgnoreCase("3")){
				order.setShipping_to("直邮");
			}
			List<Map<String,String>> goodsList = new ArrayList<Map<String,String>>();
			for(Map<String,Object> map:list){
				if(str.equals(map.get("order_id").toString())){
					Map<String,String> ms= new HashMap<String,String>(); 
					ms.put("url",map.get("url").toString());
					ms.put("name",map.get("name").toString());
					ms.put("quantitiy",map.get("quantity").toString());
					ms.put("obj_type",map.get("obj_type").toString());
					ms.put("from_sto", map.get("from_sto").toString());
					ms.put("product_id", map.get("product_id").toString());
					//发货地转成汉字发前台
					if(map.get("from_sto").toString().equalsIgnoreCase("1")){
						ms.put("shipping_to","");
					}
					if(map.get("from_sto").toString().equalsIgnoreCase("4")){
						ms.put("shipping_to","");
					}
					if(map.get("from_sto").toString().equalsIgnoreCase("5")){
						ms.put("shipping_to","");
					}
					if(map.get("from_sto").toString().equalsIgnoreCase("2")){
						ms.put("shipping_to", "保税");
					}
					if(map.get("from_sto").toString().equalsIgnoreCase("3")){
						ms.put("shipping_to", "直邮");
					}
					goodsList.add(ms);
				}
			}
			order.setGoodsList(goodsList);
			orderList.add(order);
		}
		return orderList;
	}
	
	
	//我的订单列表
	@Override
	public List<OrderList> getOrderListOne(Checksum checkSum,String member_id,Integer pageNum) throws Exception {

		List<Map<String,Object>> list = orderDao.getAPPOrderList(checkSum,member_id,pageNum);
		List<OrderList> orderList = new ArrayList<OrderList>();;
		Map<String,Map<String,Object>> orderMap = new LinkedHashMap<String,Map<String,Object>>();
		for(Map<String,Object> map:list){
			orderMap.put(map.get("order_id").toString(),map);
		}
		for(String str : orderMap.keySet()){
			OrderList order = new OrderList();
			Map m = orderMap.get(str);
			order.setOrder_id(str);
			order.setOrder_rel(m.get("order_rel").toString());
			order.setCreatetime(m.get("createtime").toString());
			order.setStatus(m.get("status").toString());
			order.setTimez(m.get("timez").toString());
			if(m.get("status").toString().contains("等待支付")){
				order.setStatusflag("1");//等待支付
			}else{
				order.setStatusflag("0");//显示订单跟踪
			}
			order.setTotal_amount(m.get("total_amount").toString());
			
			//发货地转成汉字发前台
			order.setFrom_sto(m.get("from_sto").toString());
			//发货地转成汉字发前台
			if(m.get("from_sto").toString().equalsIgnoreCase("1")){
				order.setShipping_to("");//大陆  人为定的
			}
			if(m.get("from_sto").toString().equalsIgnoreCase("4")){
				order.setShipping_to("");//大陆  人为定的
			}
			if(m.get("from_sto").toString().equalsIgnoreCase("2")){
				order.setShipping_to("保税");
			}
			if(m.get("from_sto").toString().equalsIgnoreCase("3")){
				order.setShipping_to("直邮");
			}
			if(m.get("from_sto").toString().equalsIgnoreCase("5")){
				order.setShipping_to("");
			}
			List<Map<String,String>> goodsList = new ArrayList<Map<String,String>>();
			for(Map<String,Object> map:list){
				if(str.equals(map.get("order_id").toString())){
					Map<String,String> ms= new HashMap<String,String>(); 
					ms.put("url",map.get("url").toString());
					ms.put("name",map.get("name").toString());
					ms.put("quantitiy",map.get("quantity").toString());
					ms.put("obj_type",map.get("obj_type").toString());
					ms.put("from_sto", map.get("from_sto").toString());
					ms.put("product_id", map.get("product_id").toString());
					//发货地转成汉字发前台
					if(map.get("from_sto").toString().equalsIgnoreCase("1")){
						ms.put("shipping_to","");
					}
					if(map.get("from_sto").toString().equalsIgnoreCase("4")){
						ms.put("shipping_to","");
					}
					if(map.get("from_sto").toString().equalsIgnoreCase("5")){
						ms.put("shipping_to","");
					}
					if(map.get("from_sto").toString().equalsIgnoreCase("2")){
						ms.put("shipping_to", "保税");
					}
					if(map.get("from_sto").toString().equalsIgnoreCase("3")){
						ms.put("shipping_to", "直邮");
					}
					goodsList.add(ms);
				}
			}
			order.setGoodsList(goodsList);
			orderList.add(order);
		}
		return orderList;
	}

	@Override
	public List<Map> getAPPOrderList(Checksum checkSum,String member_id,Integer pageNum) throws Exception {
		List<OrderList> orderLists = getOrderListOne(checkSum,member_id, pageNum);
		Map<String, List<OrderList>> map = new HashMap<String, List<OrderList>>();
		for (OrderList orderList : orderLists) {
			String order_rel = orderList.getOrder_rel();
			List<OrderList> list = map.get(order_rel);
			if(list==null){
				list = new ArrayList<OrderList>();
			}
			list.add(orderList);
			map.put(order_rel, list);
		}
		Map<String, List<OrderList>> hemap = new HashMap<String, List<OrderList>>();
		Map<String, List<OrderList>> fenmap = new HashMap<String, List<OrderList>>();
		
		Set<String> mapkeySet = map.keySet();
		for (String order_rel : mapkeySet) {
			List<OrderList> list = map.get(order_rel);
			OrderList orderList = list.get(0);
			String status = orderList.getStatus();
			if("已完成".equals(status) || "等待收货".equals(status)){
				fenmap.put(order_rel, list);
			}else{//取消 等待审核 等待支付
				hemap.put(order_rel, list);
			}
		}
		
		
		Map<Long, Map> sMap = new HashMap<Long,Map>();
		
		Set<String> hekeySet = hemap.keySet();
		for (String order_rel : hekeySet) {
			List<OrderList> list = hemap.get(order_rel);
			
			OrderList orderList = list.get(0);
			if(list.size()>1){
				orderList.setFrom_sto("");
				orderList.setShipping_to("");
			}
			BigDecimal total_amount = new BigDecimal(0.00);
			List<Map<String, String>> goodsList = new ArrayList<Map<String,String>>();
			for (OrderList ol : list) {
				total_amount = total_amount.add(new BigDecimal(ol.getTotal_amount()));
				goodsList.addAll(ol.getGoodsList());
			}
			orderList.setOrder_id(order_rel);
			orderList.setTotal_amount(total_amount.toString());
			orderList.setGoodsList(goodsList);
			long createtime = Long.parseLong(orderList.getTimez());
			List<OrderList> ols = new ArrayList<OrderList>();
			ols.add(orderList);
			Map m = new HashMap();
			m.put("count",String.valueOf(ols.size()) );
			m.put("orderType", "fold");
			m.put("orderList", ols);
			sMap.put(createtime, m);
		}
		
		Set<String> fenkeySet = fenmap.keySet();
		for (String order_rel : fenkeySet) {
			List<OrderList> list = fenmap.get(order_rel);
			long createtime = Long.parseLong(list.get(0).getTimez());
			Map m = new HashMap();
			m.put("count", String.valueOf(list.size()));
			m.put("orderType", "unfold");
			m.put("orderList", list);
			sMap.put(createtime, m);
		}
		Set<Long> skeySet = sMap.keySet();
		List<Long> lsList = new ArrayList<Long>();
		for (Long l : skeySet) {
			lsList.add(l);
		}
		Collections.sort(lsList);
		
		List<Map> rMaps = new ArrayList<Map>();
		for (int i = lsList.size()-1; i >=0 ; i--) {
			Map m = sMap.get(lsList.get(i));
			rMaps.add(m);
		}
		return rMaps;
	}
	
	@Override
	public OrderDetail getOrderDetail(Checksum checkSum,String order_id, String member_id)throws Exception {
		OrderDetail orderDetail = orderDao.getOrderDetail(order_id, member_id);
		if (orderDetail == null) {
			return null;
		}
		List<Map<String, String>> goodsList = orderDao.getOrderGoodsList(checkSum,orderDetail.getOrder_id());
		orderDetail.setGoodsList(goodsList);
		int quantity = 0;
		for (Map<String, String> map : goodsList) {
			if ("0".equals(map.get("obj_type"))) {// 判断不是赠品
				quantity += Integer.parseInt(map.get("quantity").toString());
			}
			//判断订单中的商品是否都评价过了      1：已评价 0：未评价
			String product_id = map.get("product_id");
			boolean flag = commentDao.isProductComment(order_id,product_id);
			if(flag){
				map.put("isProductComment", "1");
			}else{
				map.put("isProductComment", "0");
			}
		}
		orderDetail.setQuantity(quantity + "");
		List<Comment> commentList = commentDao.getCommentList(checkSum,member_id,
				order_id, 99999999, 1);
		String is_comments = "1";//1：已评价 0：未评价
		if (commentList == null || commentList.size() == 0) {
			is_comments = "0";
		}
		for (Comment com : commentList) {
			if (com.getIs_comments().equals("0")) {
				is_comments = "0";
				break;
			}
		}
		orderDetail.setIs_comments(is_comments);
		return orderDetail;
	}

	//查询物流信息 
	@Override
	public String getOrderExp(String bk_ord_no,Checksum checkSum) throws Exception {
		log.info("OrderServiceImpl getOrderExp====================param:" + bk_ord_no);
		// RestTemplate restTemplate = new RestTemplate();
		if (checkSum == null) {
			checkSum = new Checksum();
		}
		String key = Md5.encipher(checkSum.toString() + bk_ord_no
				+ Constant.PRIVATE_KEY);
		checkSum.setSign(key);
		String hxl_type = checkSum.getHxl_type();
		String webService = new SystemConfig("clientService").getProperty(hxl_type);
		String url = new SystemConfig(webService).getProperty("order_exp_url") + "?sign="
				+ checkSum.getSign() + "&api_key=" + checkSum.getApi_key()
				+ "&nonce=" + checkSum.getNonce() + "&version="
				+ checkSum.getVersion() + "&sign_method="
				+ checkSum.getSign_method() + "&timestamp="
				+ checkSum.getTimestamp() + "&type=" + checkSum.getType()
				+ "&ord_no=" + bk_ord_no;

		Map map = new HashMap();
		String message = restTemplate.getForObject(url, String.class, map);
		log.info("OrderServiceImpl getOrderExp====================response.get_return() : "
				+ message.toString());
		return message.toString();
	}

	@Override
	public List<Map<String, Object>> getOrderLogList(String order_id)
			throws Exception {
		return orderDao.getOrderLogList(order_id);
	}

	@Override
	public Map<String, Object> getOrderInfo(String order_id) throws Exception {
		return orderDao.getOrderInfo(order_id);
	}

	@Override
	//取消订单  释放库存
	public synchronized void cancelOrder(Checksum checkSum ,String order_id, String member_id,String flag,String reason) throws Exception {
		//判断库存  特卖是否压单   海外
		// is_limitbuy('0','1')  是否特卖 0:否 1:是             used_stor('0','1','2')  压单状态   0:不压单  1:网站压单  2:比酷压单网站压单 sdb_b2c_orders_item
		//通过order_id 取的订单货品表
		//is_overseas;   发货地  1大货   2保税  3海外 4 6大仓     大陆，保税的商品比酷和本地都锁库存了             海外的只在本地锁库存  sdb_b2c_orders
		
		Map<String, Object> orderProductBean = new HashMap<String, Object>();//订单下对应的商品集合      既在比酷又在本地还库存的
		Map<String, String> orderBkIds = new HashMap<String, String>();  //取消订单 同步比酷用到的比酷id
		List<CartGoods> productIdandNums = new ArrayList<CartGoods>(); //取消订单        只还本地库存的
		//Map<String, List<CartGoods>> cancelbendi = new HashMap<String, List<CartGoods>>();//只还本地库存的所有商品 
		List<CancelOrderItemsInventory> orderitemsss = orderDao.getYaDanProductByOrder_id(order_id,member_id);
		if(null != orderitemsss){
			for(CancelOrderItemsInventory oitem : orderitemsss){
				//只有大陆，保税的商品才会走取消比酷接品返回库存量
				if(oitem.getIs_overseas().equals("1")|| oitem.getIs_overseas().equals("2")||oitem.getIs_overseas().equals("4")){
					//即取消本地库存又取消比酷库存
					if(oitem.getIs_limitbuy().equals("1") && oitem.getUsed_store().equals("2")){
					//调比酷接口取消库存用到的orderBk_id
						
						orderBkIds.put(oitem.getOrder_id(), oitem.getBk_no_id());
				    //把该订单货品数量加回到占有量上  (sdb_b2c_products  limitbuy_us_quantity已占库存量)传入值 Product_id Nums
						//orderDao.addNumsToLimitbuyusQuantity(oitem);
					}
					//只释放本地库存量  特买商品只在本地锁库存
					else if(oitem.getIs_limitbuy().equals("1") && oitem.getUsed_store().equals("1")){
						//orderDao.addNumsToLimitbuyusQuantity(oitem);
						CartGoods idandnums = new CartGoods();
						idandnums.setProduct_id(oitem.getProduct_id());
						idandnums.setQuantity(oitem.getNums());
						idandnums.setOrder_id(oitem.getOrder_id());
						idandnums.setGoodsflag(oitem.getIs_limitbuy());
						productIdandNums.add(idandnums);
						//cancelbendi.put(oitem.getOrder_id(), productIdandNums);//只取消本地库存商品
					}else{//大陆和保税的普通货品    如果比酷压单只对比酷释放    本地不压单
						if(oitem.getUsed_store().equals("2")){
							//调比酷接口取消库存用到的orderBk_id
							orderBkIds.put(oitem.getOrder_id(), oitem.getBk_no_id());
						}
					}
					
				}else{//海外的只在本地锁库存(返回要本地库存)
					if(!oitem.getUsed_store().equals("0")){
						CartGoods idandnums = new CartGoods();
						idandnums.setProduct_id(oitem.getProduct_id());
						idandnums.setQuantity(oitem.getNums());
						idandnums.setOrder_id(oitem.getOrder_id());
						idandnums.setGoodsflag(oitem.getIs_limitbuy());
						productIdandNums.add(idandnums);
						//cancelbendi.put(oitem.getOrder_id(), productIdandNums);//只取消本地库存商品
					}
				}
			}
			
			for(String key : orderBkIds.keySet()){//既在比酷又在本地还库存的
				List<CartGoods> ordercancel = new ArrayList<CartGoods>();
				for(CancelOrderItemsInventory oitems : orderitemsss){
					if(key.equals(oitems.getOrder_id())){
						CartGoods cg = new CartGoods();
						cg.setQuantity(oitems.getNums());
						cg.setProduct_id(oitems.getProduct_id());
						cg.setGoodsflag(oitems.getIs_limitbuy());
						ordercancel.add(cg);
					}
				}
				orderProductBean.put(key, ordercancel);//既在比酷又在本地还库存的
			}
			
			//调用取消比酷接口和本地库存
			for(String key : orderBkIds.keySet()){//2
				if(!orderBkIds.get(key).toString().equals("0")){//订单比酷id
					if("3".equals(flag)){
						
						handlerspecialSelling((List<CartGoods>) orderProductBean.get(key), "minus");
						updateUsed_storeByOrder_id(key.toString(), "0");
						
					}else
					{
					ResultObject ro = this.cancelBikuOrder(checkSum,orderBkIds.get(key).toString());
					if(ro.getFlag().equals("1")){//释放本地库存
						handlerspecialSelling((List<CartGoods>) orderProductBean.get(key), "minus");
						updateUsed_storeByOrder_id(key.toString(), "0");
					}
					}
				}
			}
			//只对本地库存释放的商品
			if(productIdandNums.size()>0 && null != productIdandNums){
				this.handlerspecialSelling( productIdandNums , "minus");
				for(CartGoods productIdandNum : productIdandNums){
					if(StringUtil.isNotEmpty(productIdandNum.getOrder_id())){
						this.updateUsed_storeByOrder_id(productIdandNum.getOrder_id(),"0");
					}
				}
			}
			
			//只对本地库存释放的商品
			/*if(cancelbendi.size()>0){//2
				for(String key : cancelbendi.keySet()){
					if(!cancelbendi.get(key).toString().equals("0")){//订单id
						handlerspecialSelling((List<CartGoods>) cancelbendi.get(key), "minus");
						updateUsed_storeByOrder_id(key.toString(), "0");
					}
				}
			}*/
			
		}

		if(flag.equals("1")||"3".equals(flag)){
			int result = orderDao.cancelOrder(order_id, member_id);//取消订单
			if(result>0){//取消成功
				log.info("取消订单，保存订单日志");
				orderDao.saveCancelOrderLog(order_id, member_id,reason);//保存订单日志
				//返回优惠券 消除使用记录 设置优惠券为未使用状态
				String order_rel = getOrder_relByOrder_id(order_id);
				List<String> memc_codes = orderDao.getUseMemc_codes(order_rel);
				orderDao.updateUserCoupons(memc_codes, member_id, false);
				orderDao.deleteCouponsUse(order_rel);
			}
		}
	}
	
	
	//取消订单 同步比酷
	public ResultObject cancelBikuOrder(Checksum checkSum,String orderbk_id) throws Exception{
		/*String returnStr = order_id ;
		returnStr = returnStr.replaceAll("null", "");
		String key = Md5.encipher( checkSum.toString() + returnStr + Constant.PRIVATE_KEY );
		checkSum.setSign(key);*/
		String hxl_type = checkSum.getHxl_type();
		String webService = new SystemConfig("clientService").getProperty(hxl_type);
			String url = new SystemConfig(webService).getProperty("cancelOrder_url")+"?sign=&api_key=&nonce="
					+"&version=&sign_method=&timestamp=&type="+checkSum.getType()
					+"&ord_id="+orderbk_id;
			log.info("cancelBikuOrder========url===>>>"+url);
			String message = restTemplate.postForObject(StringUtil.stringfromUtf8Toiso(url) ,null , String.class,  new HashMap());
			System.out.println(message);
			JSONObject msgJson = JSONObject.fromObject(message);
			log.info("cancelBikuOrder========msgJson===>>>"+msgJson);
			if("1".equals(msgJson.getString("flag"))){//返回正确信息
				JSONObject info = msgJson.getJSONObject("resultObject");
				Map<String,Object> resultMap = new HashMap<String, Object>();
//				if(Constant.IVALUE3_FLAG){
					resultMap.put("ord_id", info.getString("ord_id"));//比酷id
					resultMap.put("ord_no", info.getString("ord_id"));//比酷no
//				}else{
//					resultMap.put("ord_id", info.getString("ord_id"));//比酷id
//					resultMap.put("ord_no", info.getString("ord_no"));//比酷no
//				}
				return new ResultObject("1", "success", resultMap);
			}else{
				return new ResultObject("-1",msgJson.getString("result"), null);
			}
	}

	@Override
	public int getOrderNumbers(String member_id) throws Exception {
		return orderDao.getOrderNumbers(member_id);
	}

	@Override
	public int isExistOrder(String member_id) throws Exception {
		return orderDao.isExistOrder(member_id);
	}

	@Override
	public Map<String, Object> getAppRuleOrder(String actionCondition)
			throws Exception {
		return orderDao.getAppRuleOrder(actionCondition);
	}

	@Override
	public List<Map<String, Object>> getPrductInfoByOrder_rel(String order_rel)
			throws Exception {
		return orderDao.getPrductInfoByOrder_rel(order_rel);
	}

	@Override
	public List<OrderPay> getPayGoodsByOrder_rel(String order_rel)
			throws Exception {
		return orderDao.getPayGoodsByOrder_rel(order_rel);
	}

	@Override
	public void updateItemPriceByitem_id(OrderItems orderItems, String item_id)
			throws Exception {
		Map map = new HashMap();
		double price = orderItems.getPrice();
		double amount = orderItems.getAmount();
		double score = orderItems.getScore();
		map.put("item_id", item_id);
		map.put("price", price);
		map.put("amount", amount);
		map.put("score", score);
		orderDao.updateItemPriceByitem_id(map);
	}

	@Override
	public void updateObjectsPriceByobj_id(OrderObjects orderObjects,
			String obj_id) throws Exception {
		Map map = new HashMap();
		double price = orderObjects.getPrice();
		double amount = orderObjects.getAmount();
		double score = orderObjects.getScore();
		map.put("obj_id", obj_id);
		map.put("price", price);
		map.put("amount", amount);
		map.put("score", score);
		orderDao.updateObjectsPriceByobj_id(map);
	}

	@Override
	public void updateOrderPriceByOrder_id(OrderParameter orderParameter,
			String order_id) throws Exception {
		Map map = new HashMap();
		BigDecimal total_amount = orderParameter.getTotal_amount();
		BigDecimal final_amount = orderParameter.getFinal_amount();
		BigDecimal score_g = orderParameter.getScore_g();
		map.put("order_id", order_id);
		map.put("total_amount", total_amount);
		map.put("final_amount", final_amount);
		map.put("score_g", score_g);
		orderDao.updateOrderPriceByOrder_id(map);
	}

	
	@Override
	public void updateOrderCost_freightByOrder_id(BigDecimal cost_freight,
			String order_id) throws Exception {
		Map map = new HashMap();
		map.put("order_id", order_id);
		map.put("cost_freight", cost_freight);
		orderDao.updateOrderCost_freightByOrder_id(map);
	}

	@Override
	public void updateItemgGPriceByItem_id(double g_price, String item_id)
			throws Exception {
		Map map = new HashMap();
		map.put("item_id", item_id);
		map.put("g_price", g_price);
		orderDao.updateItemgGPriceByItem_id(map);
	}

	@Override
	public void updateOrderCost_itemByOrder_id(BigDecimal cost_item,
			String order_id) throws Exception {
		Map map = new HashMap();
		map.put("order_id", order_id);
		map.put("cost_item", cost_item);
		orderDao.updateOrderCost_itemByOrder_id(map);
	}

	@Override
	public void updateOrderItemIs_limitbuyByItem_id(String is_limitbuy,
			String item_id) throws Exception {
		Map map = new HashMap();
		map.put("item_id", item_id);
		map.put("is_limitbuy", is_limitbuy);
		orderDao.updateOrderItemIs_limitbuyByItem_id(map);
	}

	@Override
	public ResultObject secondaryPay(List<OrderPay> orderPays,OrderParameter order,Checksum checkSum,HttpServletRequest request) throws Exception {
		String order_rel = order.getOrder_rel();
		order.setSource(checkSum.getType());
		//验证订单是否属于该用户
		String member_id = order.getMember_id();
		String member = orderDao.getOrderMember_id(order_rel);
		if(!member_id.trim().equals(member.trim())){
			return new ResultObject("-1","当前订单不属于当前用户",null);
		}
		//判断是否已压单
		long startTime = System.currentTimeMillis();
		log.info("OrderServiceImpl ------secondaryPay---->二次支付接口调用开始");
//		if(Constant.IVALUE3_FLAG){
			String amount = orderDao.getOrderAmount(order_rel);
			order.setTotal_amount(new BigDecimal(amount));
			//同步比酷,如果已经同步就不在同步,否则再次同步
			String biKuid = orderDao.getBiKuIDByOr(order_rel);
			if(StringUtils.isBlank(biKuid) || "0".equals(biKuid)){
				ResultObject syncBiku = syncBiku(order,checkSum);
				if(syncBiku!=null){
					log.info("OrderServiceImpl-----syncBiku===json==>"+syncBiku.getError());
					return syncBiku;
				}
			}
//		}else{
//			for (OrderPay orderPay : orderPays) {
//				String used_store = orderPay.getUsed_store();
//				if(!"0".equals(used_store)){
//					cancelOrder(checkSum,order_rel, order.getMember_id(), "2","0");//取消压单,本地比酷都取消
//					log.info("=========订单已取消==========");
//					break;
//				}
//			}
//			
//			List<OrderParameter> ops = orderDao.getOrderParameters(order_rel);
//			List<OrderItems> ois = orderDao.getOrderItemss(order_rel);
//			List<OrderObjects> obs = orderDao.getOrderObjectss(order_rel);
//			
//			List<OrderPay> priceOrderPays = new ArrayList<OrderPay>();//惠买价格变动的商品
//			List<OrderPay> islimitbuyOrderPays = new ArrayList<OrderPay>();//特卖变动的商品
//			
//			for (OrderPay orderPay : orderPays) {
//				//判断库存,预售,有效无效
//				List<CartGoods> cartGoodss = new ArrayList<CartGoods>();
//				CartGoods cartGoods = new CartGoods();
//				BeanUtils.copyProperties(cartGoods, orderPay);
//				cartGoodss.add(cartGoods);
//				ResultObject resultObject = productStatus(cartGoodss, order, checkSum);
//				if(resultObject!=null){
//					log.error("OrderController saveAppOrder====productStatus=>"+resultObject.getError());
//					return new ResultObject("-1",resultObject.getError(),null);
//				}
//				//判断惠买价格变动
//				if(!orderPay.getPrice().equals(orderPay.getY_price())){
//					priceOrderPays.add(orderPay);
//				}
//				//判断销售价格变动
//				else if(!orderPay.getG_price().equals(orderPay.getY_g_price())){
//					priceOrderPays.add(orderPay);
//				}
//				//判断是否特卖有变动
//				if("1".equals(orderPay.getGoodsflag())){
//					if(!"1".equals(orderPay.getIs_limitbuy())){
//						islimitbuyOrderPays.add(orderPay);
//					}
//				}else{
//					if(!"0".equals(orderPay.getIs_limitbuy())){
//						islimitbuyOrderPays.add(orderPay);
//					}
//				}
//			}
//			
//			if(priceOrderPays.size()>0){
//				for (OrderPay orderPay : priceOrderPays) {
//					String item_id = orderPay.getItem_id();
//					String obj_id = orderPay.getObj_id();
//					double quantity = Double.parseDouble(orderPay.getQuantity());
//					double price = Double.parseDouble(orderPay.getPrice());
//					double amount = quantity*price;
//					for (OrderItems oi : ois) {
//						String item_id2 = oi.getItem_id()+"";
//						if(item_id.equals(item_id2)){
//							List<CouponsNew> cns = new ArrayList<CouponsNew>();
//							if(oi.getPmt_price()>0){
//								cns = couponsDaoImpl.getCouponsNewByitem_id(item_id2);
//							}
//							
//							double total_cpns_price = 0.0;
//							for (CouponsNew couponsNew : cns) {
//								total_cpns_price += Double.parseDouble(couponsNew.getCpns_price());
//							}
//							if(total_cpns_price<=amount){
//								oi.setPmt_price(total_cpns_price);
//							}else{
//								oi.setPmt_price(0.0);
//							}
//							oi.setG_price(Double.parseDouble(orderPay.getG_price()));
//							oi.setPrice(price);
//							oi.setAmount(amount);
//							if("normal".equals(orderPay.getGoods_type().toString())){
//								oi.setScore(amount);
//							}
//							if("gift".equals(orderPay.getGoods_type().toString())){
//								oi.setScore(0);
//							}
//						}
//					}
//					for (OrderObjects ob : obs) {
//						String obj_id2 = ob.getObj_id()+"";
//						if(obj_id.equals(obj_id2)){
//							ob.setPrice(price);
//							ob.setAmount(amount);
//							if("normal".equals(orderPay.getGoods_type().toString())){
//								ob.setScore(amount);
//							}
//							if("gift".equals(orderPay.getGoods_type().toString())){
//								ob.setScore(0);
//							}
//						}
//					}
//				}
//				
//				for (OrderParameter op : ops) {
//					String order_id = op.getOrder_id();
//					double total_amount = 0.0;
//					double pmt_order = 0.0;
//					for (OrderItems oi : ois) {
//						String order_id2 = oi.getOrder_id();
//						if(order_id.equals(order_id2)){
//							total_amount += oi.getAmount();
//							pmt_order += oi.getPmt_price();
//						}
//					}
//					op.setTotal_amount(new BigDecimal(total_amount-pmt_order));
//					op.setFinal_amount(new BigDecimal(total_amount-pmt_order));
//					op.setCost_item(new BigDecimal(total_amount));
//					op.setScore_g(new BigDecimal(total_amount));
//				}
//				
//				BigDecimal zTotal_amount = new BigDecimal(0.0);
//				for (OrderParameter op : ops) {
//					zTotal_amount = zTotal_amount.add(op.getTotal_amount());
//				}
//				List<CartGoods> cgs = new ArrayList<CartGoods>();
//				CartGoods cg = new CartGoods();
//				cg.setPrice(zTotal_amount.toString());
//				cg.setQuantity("1");
//				cg.setG_price("0");
//				cgs.add(cg);
//				Map<String, BigDecimal> orderCTPCP = getOrderCTPCP(cgs,null);
//				BigDecimal cost_freight = orderCTPCP.get("cost_freight");
//				BigDecimal total_amount = orderCTPCP.get("total_amount");
//				order.setTotal_amount(total_amount);
//				for (OrderParameter op : ops) {
//					if(op.getOrder_id().equals(order_rel)){
//						op.setTotal_amount(op.getTotal_amount().add(cost_freight));
//						op.setCost_freight(cost_freight);
//						op.setFinal_amount(op.getTotal_amount().add(cost_freight));
//					}
//				}
//				
//				for (OrderItems oi : ois) {
//					orderDao.updatePrice4OrderItems(oi);
//				}
//				for (OrderObjects ob : obs) {
//					orderDao.updatePrice4OrderObjects(ob);
//				}
//				for (OrderParameter op : ops) {
//					orderDao.updatePrice4OrderParameter(op);
//				}
//			}
//			
//			//特卖变动的商品
//			if(islimitbuyOrderPays.size()>0){
//				for (OrderPay orderPay : islimitbuyOrderPays) {
//					String is_limitbuy = "";
//					if("1".equals(orderPay.getGoodsflag())){
//						is_limitbuy = "1";
//					}else{
//						is_limitbuy = "0";
//					}
//					//更新item表 islimitbuy
//					String item_id = orderPay.getItem_id();
//					log.info("OrderServiceImpl-----updateOrderItemIs_limitbuyByItem_id=====>"+item_id );
//					updateOrderItemIs_limitbuyByItem_id(is_limitbuy ,item_id);
//				}
//			}
//			
//			//提示
//			if(priceOrderPays.size()>0){
//				Map map = new HashMap();
//				map.put("order_id", order.getOrder_rel());
//				//总付金额
//				BigDecimal total_amount = order.getTotal_amount();
//				map.put("total_amount", total_amount);
//				log.error("OrderServiceImpl ---------->惠买价格已变动,请从新提交订单");
//				return new ResultObject("-2","部分商品价格调整!",map);
//			}else{
//				BigDecimal total_total_amount = new BigDecimal(0.0);
//				BigDecimal total_pmt_price = new BigDecimal(0.0);
//				for (OrderPay orderPay : orderPays) {
//					double price = Double.parseDouble(orderPay.getPrice());
//					String quantity = orderPay.getQuantity();
//					total_total_amount = total_total_amount.add(new BigDecimal(price).multiply(new BigDecimal(quantity)));
//					String pmt_price = orderPay.getPmt_price();
//					total_pmt_price = total_pmt_price.add(new BigDecimal(pmt_price));
//				}
//				Map <String, BigDecimal> pmt = new HashMap <String, BigDecimal> ();
//				pmt.put("pmt_order",total_pmt_price);
//				List<CartGoods> cgs = new ArrayList<CartGoods>();
//				CartGoods cg = new CartGoods();
//				cg.setPrice(total_total_amount.toString());
//				cg.setQuantity("1");
//				cg.setG_price("0");
//				cgs.add(cg);
//				Map<String, BigDecimal> orderCTPCP = getOrderCTPCP(cgs,pmt);
//				BigDecimal total_amount = orderCTPCP.get("total_amount");
//				order.setTotal_amount(total_amount);
//			}
//			
//			List<OrderPay> bsOrderPay = new ArrayList<OrderPay>();
//			for (OrderPay orderPay : orderPays) {
//				String area_name = orderPay.getArea_name();
//				if("跨境保税".equals(area_name)){
//					bsOrderPay.add(orderPay);
//				}
//			}
//			double totalPrice = 0;
//			int totalQuantity = 0;
//			for (OrderPay orderPay : bsOrderPay) {
//				String price = orderPay.getPrice();
//				String quantity = orderPay.getQuantity();
//				if(quantity.indexOf('.')>0){
//					quantity = quantity.substring(0,quantity.indexOf('.'));
//				}
//				totalQuantity += Integer.parseInt(quantity);
//				totalPrice += new BigDecimal(price).multiply(new BigDecimal(quantity)).doubleValue();
//			}
//			if(totalPrice > 0 && totalQuantity > 0){
//				if(totalPrice>=500){
//					if(totalQuantity>1){//不通过
//						log.error("OrderServiceImpl ---------->保税区商品价格大于等于500但没有超过1000" );
//						return new ResultObject("-1","亲，您购买的保税区商品已经超过500元，会产生额外的行邮税费，建议您分次下单（使订单金额低于500元），节省开支哟。",null);
//					}else {
//						if(totalPrice>=1000){//不通过
//							log.error("OrderServiceImpl-------->保税区商品价格大于等于1000且数量大于1" );
//							return new ResultObject("-1","保税区商品价格大于等于1000,请从新下单",null);
//						}
//					}
//				}
//			}
//			//压单本地
//			List<CartGoods> specialSelling = new ArrayList<CartGoods>();//特卖商品
//			for (OrderPay orderPay : orderPays) {
//				String goodsflag = orderPay.getGoodsflag();
//				if("1".equals(goodsflag)){
//					CartGoods cartGoods = new CartGoods();
//					cartGoods.setQuantity(orderPay.getQuantity());
//					cartGoods.setGoodsflag(goodsflag);
//					cartGoods.setProduct_id(orderPay.getProduct_id());
//					specialSelling.add(cartGoods);
//				}
//			}
//			if(specialSelling.size()>0){
//				handlerspecialSelling(specialSelling,"add");
//				//修改订单状态
//				List<String> is_limitbuy = new ArrayList<String>();
//				is_limitbuy.add("1");
//				List<String> is_overseas = new ArrayList<String>();
//				is_overseas.add("1");is_overseas.add("2");is_overseas.add("3");is_overseas.add("4");
//				log.info("OrderServiceImpl-----updateUsed_storeByOrder_rel=====>"+order.getOrder_rel());
//				updateUsed_storeByOrder_rel(order.getOrder_rel(), "1", is_limitbuy, is_overseas);
//			}
//			//同步比酷
//			ResultObject syncBiku = syncBiku(order,checkSum);
//			if(syncBiku!=null){
//				log.info("OrderServiceImpl-----syncBiku===json==>"+syncBiku.getError());
//				return syncBiku;
//			}
//		}
		long endTime = System.currentTimeMillis();
		log.info("OrderServiceImpl ------secondaryPay---->二次支付接口调用结束,用时"+(endTime-startTime)+"毫秒");
		if("web".equals(order.getSource())){
			Map map = new HashMap();
			map.put("order_id", order.getOrder_rel());
			//总付金额
			BigDecimal total_amount = order.getTotal_amount();
			map.put("total_amount", total_amount);
			return new ResultObject("1","success",map);
		}else{
			return new ResultObject("s1","success",order);
		}
	}

	@Override
	public ResultObject productStatus(List<CartGoods> cartGoodss, OrderParameter op  ,Checksum checkSum) throws Exception {
		Map<String,List<CartGoods>> cartGoodsListmap = new HashMap<String, List<CartGoods>>();
		List<String> areaIDs = cartDaoImpl.getNotOverseasAreaID();
		for (String areaID : areaIDs) {
			cartGoodsListmap.put(areaID, null);
		}
		if(cartGoodss!=null && cartGoodss.size()>0){
			for (CartGoods cartGoods : cartGoodss) {
				if("false".equals(cartGoods.getDisabled()) && "true".equals(cartGoods.getMarketable())){
					if("true".equals(cartGoods.getYwhflag())){
						//判断预售
						if(!"2".equals(cartGoods.getGoodsflag())){
							//判断是不是特卖
							if("1".equals(cartGoods.getGoodsflag())){
								//判断数量是否大于了库存剩余总量	  n<=(总量-已占)
								int surplus_limitbuy_quantity = Integer.parseInt(cartGoods.getLimitbuy_quantity())-Integer.parseInt(cartGoods.getLimitbuy_us_quantity());
								if(surplus_limitbuy_quantity<=0){
									return new ResultObject("-1","部分商品已抢光!",null);
								}
								int quantity = Integer.parseInt(cartGoods.getQuantity());
								if(quantity <= surplus_limitbuy_quantity){
									//判断数量是否大于了每人限购   n<=每人限购量 
									int limitbuy_sg_quantity = Integer.parseInt(cartGoods.getLimitbuy_sg_quantity());
									if( quantity <= limitbuy_sg_quantity ){
										//非海外的在比酷再次验证库存
										if(!"3".equals(cartGoods.getArea_type())){
											String from_sto = cartGoods.getFrom_sto();
											if(cartGoodsListmap.containsKey(from_sto)){
												List<CartGoods> NotOverseasList = null;
												if(cartGoodsListmap.get(from_sto)==null){
													NotOverseasList = new ArrayList<CartGoods>();
													NotOverseasList.add(cartGoods);
													cartGoodsListmap.put(from_sto, NotOverseasList);
												}else {
													NotOverseasList = cartGoodsListmap.get(from_sto);
													NotOverseasList.add(cartGoods);
													cartGoodsListmap.put(from_sto, NotOverseasList);
												}
											}else {
												return new ResultObject("-1","发货地区不存在",null);
											}
										}
									}else {
										return new ResultObject("-1","部分商品库存不足!",null);
									}
								}else {
									return new ResultObject("-1","部分商品库存不足!",null);
								}
							}else if("3".equals(cartGoods.getGoodsflag())){
								return new ResultObject("-1","部分商品已下架!",null);
							}else{
								//非海外的在比酷再次验证库存
								if(!"3".equals(cartGoods.getArea_type())){
									String from_sto = cartGoods.getFrom_sto();
									if(cartGoodsListmap.containsKey(from_sto)){
										List<CartGoods> NotOverseasList = null;
										if(cartGoodsListmap.get(from_sto)==null){
											NotOverseasList = new ArrayList<CartGoods>();
											NotOverseasList.add(cartGoods);
											cartGoodsListmap.put(from_sto, NotOverseasList);
										}else {
											NotOverseasList = cartGoodsListmap.get(from_sto);
											NotOverseasList.add(cartGoods);
											cartGoodsListmap.put(from_sto, NotOverseasList);
										}
									}else {
										return new ResultObject("-1","发货地区不存在",null);
									}
								}
							}
						}else {
							return new ResultObject("-1","部分商品未开始销售!",null);
						}
					}else{
						return new ResultObject("-1","部分商品已无货!",null);
					}
				}else{
					return new ResultObject("-1","部分商品已下架!",null);
				}
			}
		}
		//
		Set<String> keySet = cartGoodsListmap.keySet();
		for (String from_sto : keySet) {
			List<CartGoods> list = cartGoodsListmap.get(from_sto);
			if (list!=null) {
				String bk_id = "";
				for (CartGoods cartGoods : list) {
					bk_id += cartGoods.getBiku_id()+",";
				}
				bk_id = bk_id.substring(0, bk_id.length()-1);
				ResultObject ro = getGoodsStock( bk_id, op, list, checkSum);
				if(ro!=null){
					return ro;
				}
			}
		}
		return null;
	}
	
	@Override
	public ResultObject getGoodsStock(String bk_id, OrderParameter op , List<CartGoods> cartGoodss, Checksum checkSum) throws Exception {
		if(StringUtils.isBlank(bk_id)){
			log.error("查询比酷货物状态比酷id为空");
			return new ResultObject("-1","查询比酷货物状态比酷id为空",null);
		}else{
			char charAt = bk_id.charAt(0);
			char charAt2 = bk_id.charAt(bk_id.length()-1);
			if(charAt==',' || charAt2==','){
				log.error("查询比酷货物状态个别比酷id为空");
				return new ResultObject("-1","查询比酷货物状态个别比酷id为空",null);
			}
		}
		
		log.info("getGoodsStock------bk_id--->"+bk_id);
		GoodsStockByIdParameters param = new GoodsStockByIdParameters();
		param.setProduct_id(bk_id);
		param.setPvc_id(op.getPvc_id());
		param.setCountry_id(op.getCountry_id());
		param.setLocal_id(op.getLocal_id());
		String json = logisticsService.queryGoodsStockById(checkSum, param);//查询库存
		JSONObject returnJson = JSONObject.fromObject(json);
		if(returnJson.getInt("flag")==0){
			CartGoods cartGoods  = cartGoodss.get(0);
			String area_type = cartGoods.getArea_type();
			String error = returnJson.getString("error");
			log.error("CartController getGoodsStock=====>比酷返回错误信息：" + error);
			if("4".equals(area_type)){
				return new ResultObject("-4","此地区不支持购买您选购的商品，请修改收货地址或选购的商品!",null);
			}
			return new ResultObject("-1","购物车部分商品无效!",null);
		}else{
			JSONObject msgJson = JSONObject.fromObject(returnJson.getString("msg"));
			JSONObject prodstock =JSONObject.fromObject(msgJson.getString("prodstock"));
			String[] ids = bk_id.split(",");
			for(int i=0;i<ids.length;i++){
				CartGoods cartGoods  = cartGoodss.get(i);
				String area_type = cartGoods.getArea_type();
				JSONObject o =JSONObject.fromObject(prodstock.get(ids[i].trim()));
//					int pro_id = o.getInt("prod_id");//产品id
//					int order_ok = o.getInt("order_ok");//接单属性 0：无限制，1、仓库接单，2、预采购量接单
				int amt = o.getInt("amt");//可用库存量
//					int GOOD_QTY = o.getInt("GOOD_QTY");//库存数量
//					int TEL_QTY = o.getInt("TEL_QTY");//订单压单数量
//					int TRAC_OUT_QTY = o.getInt("TRAC_OUT_QTY");//仓库压单数量
//				int NOTICE_QTY = o.getInt("NOTICE_QTY");//通知单压单数量
//				int PO_SCHD_QTY = o.getInt("PO_SCHD_QTY");//采购在途数量
				if(amt>0){//可用库存量
					if(!(amt-Integer.parseInt(cartGoods.getQuantity())>=0)){
						if("4".equals(area_type)){
							return new ResultObject("-4","此地区不支持购买您选购的商品，请修改收货地址或选购的商品!",null);
						}
						return new ResultObject("-1","购物车部分商品无效!",null);
					}
				}else if(amt==0){
//					if(PO_SCHD_QTY-NOTICE_QTY> 0){
//						if(!(PO_SCHD_QTY-NOTICE_QTY>=Integer.parseInt(cartGoods.getQuantity()))){
//							return new ResultObject("-1","购物车部分商品无效!",null);
//						}
//					}else{
					if("4".equals(area_type)){
						return new ResultObject("-4","此地区不支持购买您选购的商品，请修改收货地址或选购的商品!",null);
					}
						return new ResultObject("-1","购物车部分商品无效!",null);
//					}
//				}else{
//					return new ResultObject("-1","购物车部分商品无效!",null);
//				}
				}
			}
		}
		return null;
	}

	@Override
	public String getOrder_relByOrder_id(String order_id) throws Exception {
		return orderDao.getOrder_relByOrder_id(order_id);
	}

	@Override
	public String getShip_area(String order_rel)  throws Exception {
		return orderDao.getShip_area(order_rel);
	}

	@Override
	public ResultObject saveOrder4Memc(List<CartGoods> list,
			OrderParameter order, List<CartGoods> gift, Checksum checkSum,
			HttpServletRequest request) throws Exception {
		String memc_code = order.getMemc_code();
		log.info("OrderController saveOrder-------->优惠券号:"+memc_code);
		
		ResultObject ro = verificationCashCoupons(order.getMemc_code(), list);
		if(!"1".equals(ro.getFlag())){
			return ro;
		}
		Map msgr = (Map) ro.getMsg();
		List<CartGoods> cgs = (List<CartGoods>) msgr.get("products");
		List<CouponsNew> couponsNews = (List<CouponsNew>) msgr.get("coupons");
		order.setCreatetime(System.currentTimeMillis()/1000);
		order.setLast_modified(System.currentTimeMillis()/1000);
		if(!"web".equals(checkSum.getType()) && !"h5wap".equals(checkSum.getType())){
			/**首单立减**/
			BigDecimal sumPrice = new BigDecimal(0.00);
			if(cgs!=null && cgs.size()>0){
				for(CartGoods cg :cgs){
					BigDecimal multiply = new BigDecimal(cg.getPrice()).multiply(new BigDecimal(cg.getQuantity()));
					sumPrice = sumPrice.add(multiply.subtract(cg.getPmt()));
				}
			}
			int count = isExistOrder(order.getMember_id());
			if(count==0){
				Map appOrderm = getAppRuleOrder("appfirstorder");
				if(appOrderm!=null){
					String action_solution = appOrderm.get("action_solution")==null ? "0":appOrderm.get("action_solution").toString();
					//分摊首单立减
					BigDecimal solu = new BigDecimal(action_solution);
					if(sumPrice.doubleValue()<=solu.doubleValue()){
						for(CartGoods cg :list){
							BigDecimal xamt = new BigDecimal(cg.getPrice()).multiply(new BigDecimal(cg.getQuantity()));
							cg.setPmt(xamt);
						}
					}else{
						int size = cgs.size();
						int index = 0;
						BigDecimal dec = new BigDecimal(0.00);
						for(CartGoods cg :cgs){
							index++;
							if(index==size){
								BigDecimal subtract = solu.subtract(dec);
								BigDecimal xamt = new BigDecimal(cg.getPrice()).multiply(new BigDecimal(cg.getQuantity()));
								BigDecimal sub = xamt.subtract(cg.getPmt());
								if(subtract.doubleValue()>=sub.doubleValue()){
									cg.setPmt(xamt);
								}else{
									cg.setPmt(cg.getPmt().add(subtract));
								}
							}else{
								BigDecimal xamt = new BigDecimal(cg.getPrice()).multiply(new BigDecimal(cg.getQuantity()));
								BigDecimal sub = xamt.subtract(cg.getPmt());
								BigDecimal divide = sub.divide(sumPrice,6,BigDecimal.ROUND_DOWN);//商品小计所占比例
								BigDecimal multiply = solu.multiply(divide);
								multiply = getBLJ(multiply);
								cg.setPmt(cg.getPmt().add(multiply));
								dec = dec.add(multiply);
							}
						}
					}
				}
			}
		}
		
		Map<String, BigDecimal> orderCTPCP = this.getOrderCTPCP(cgs,null);
		
		Map<String, List<List<CartGoods>>> blanketOrder = getBlanketOrder(cgs);
		OrderParameter order2 = this.savehandlerOrder(couponsNews, orderCTPCP,order,blanketOrder,checkSum);
		//处理特卖
		List<CartGoods> specialSelling = new ArrayList<CartGoods>();//特卖商品
		for (CartGoods cartGoods : list) {
			String goodsflag = cartGoods.getGoodsflag();
			if("1".equals(goodsflag)){
				specialSelling.add(cartGoods);
			}
		}
		if(specialSelling.size()>0){
			//修改库存占用
			this.handlerspecialSelling(specialSelling,"add");
			//修改压单状况
			List<String> is_limitbuy = new ArrayList<String>();
			is_limitbuy.add("1");
			List<String> is_overseas = new ArrayList<String>();
			is_overseas.add("1");is_overseas.add("2");is_overseas.add("3");is_overseas.add("4");
			updateUsed_storeByOrder_rel(order2.getOrder_rel(), "1", is_limitbuy,is_overseas);
		}
		
		//同步比酷
		ResultObject syncBiku = syncBiku(order2,checkSum);
		if(syncBiku!=null){
			log.error("json=result==>>"+syncBiku.getError());
			//清除优惠券使用记录
			String order_rel = order.getOrder_rel();
			if(StringUtils.isNotBlank(order.getMemc_code())){
				//返还优惠券
				List<String> memc_codes = orderDao.getUseMemc_codes(order_rel);
				orderDao.updateUserCoupons(memc_codes, order.getMember_id(), false);
				orderDao.deleteCouponsUse(order_rel);
				orderDao.deleteCouponsItemUse(order_rel);
			}
			//一次同步失败删除本地订单
			orderDao.deleteOrderItemsByOrder_rel(order_rel);
			orderDao.deleteOrderObjectsByOrder_rel(order_rel);
			orderDao.deleteOrderByOrder_rel(order_rel);
			return syncBiku;
		}else{
			//删除优惠券
			//deleteCashCoupons(memc_code);
			//删除购物车
			deleteCartInfo(order);
			order2.setPayment(order.getPayment());
			return new ResultObject("1","",order2);
		}
	}

	@Override
	public ResultObject verificationCashCoupons(String memc_code,List<CartGoods> cartGoodss)
			throws Exception {
		ComparatorCartGoods ccg = new ComparatorCartGoods();
		Collections.sort(cartGoodss,ccg);
		log.info("CartServiceImpl-----UsedCoupons--->开始调用优惠券验证程序");
		/**
		 *  使用顺序 满减->代金
		 *  	单品->分类->全场
		 *  张数限制 满减和代金可一起使用,一共可使用2张
		 *  分摊  	当前商品金额/商品总额 * 优惠额
		 *  	单品直接减去优惠 负数则置为0                <-----|
		 *  	分类分摊到该分类所属的商品上 负数则置为0    <--------|
		 *  	全场分单所有商品上 负数则置为0      <-------------|
		 * 		最后一个商品优惠价格是总优惠减去其他商品优惠---------|
		 * 
		 */
		long startTime = System.currentTimeMillis();
		/**定义不可用优惠券集合,不可用的全部放置在其中**/
		String[] codes = memc_code.split(",");
		log.info("CartServiceImpl-----UsedCoupons--->memc_code---"+memc_code);
		List<String> memc_codes = new ArrayList<String>();
		/**去除重复优惠券**/
		List<String> memc_codest = new ArrayList<String>();
		for (String code : codes) {
			String scode = code.trim();
			memc_codes.add(scode);
			String upperCase = scode.toUpperCase();
			if(!memc_codest.contains(upperCase)) {
				memc_codest.add(upperCase);
			}else{
				log.error("CartServiceImpl-----UsedCoupons--->该优惠券已经添加过了:"+code);
				return new ResultObject("-1","该优惠券已经添加过了",null);
			}
		}
		//整笔订单只能使用两张优惠券,代金券和满减券可以同时使用
		int len = codes.length;
		if(len>2){
			log.error("CartServiceImpl-----UsedCoupons--->订单只能使用2张优惠券["+len);
			return new ResultObject("-1","订单只能使用2张优惠券",null);
		}
		int nums = memc_codes.size();//总使用张数
		/** 重新拼接优惠券 **/
		memc_code = "";
		for (String str : memc_codes) {
			memc_code += str+",";
		}
		memc_code = memc_code.substring(0, memc_code.length()-1);
		log.info("CartServiceImpl-----UsedCoupons--->codes长度-->:"+nums);
		/**获取已使用的优惠券**/
		String isUsed = couponsDaoImpl.getIsUsedCoupons(memc_code);
		log.info("CartServiceImpl-----UsedCoupons--->isUsed-->:"+isUsed);
		if(isUsed!=null){
			log.error("CartServiceImpl-----UsedCoupons--->优惠券["+isUsed+"]已经使用过");
			return new ResultObject("-1","该优惠券已经使用",null);
		}
		
		List<CouponsNew> couponsNews = getCouponsNewByMemc_code(memc_code);
		if(couponsNews==null || couponsNews.size()==0){
			log.error("CartServiceImpl-----UsedCoupons--->优惠券输入错误");
			return new ResultObject("-1","优惠券输入错误",null);
		}
		/**部分优惠券无效**/
		if(couponsNews.size()<nums){
			for (CouponsNew couponsNew : couponsNews) {
				memc_codes.contains(couponsNew.getMemc_code());
				memc_codes.remove(couponsNew.getMemc_code());
			}
			if(memc_codes!=null && memc_codes.size()>0){
				log.error("CartServiceImpl-----UsedCoupons--->优惠券输入错误");
				return new ResultObject("-1","优惠券输入错误",null);
			}
		}
		/**验证优惠券使用个数格式**/
		int x = 0;
		for (CouponsNew couponsNew : couponsNews) {
			String cpns_type = couponsNew.getCpns_type();
			if("2".equals(cpns_type)){
				x++;
			}
		}
		if(x>1){
			return new ResultObject("-1","满减券一次最多只能使用1张",null);
		}
		/**验证优惠券时间**/
		String startTimeError = "";
		String endTimeError = "";
		for (CouponsNew couponsNew : couponsNews) {
			String cpns_id = couponsNew.getCpns_id();
			if(cpns_id==null){
				log.error("CartServiceImpl-----UsedCoupons--->优惠券已作废");
				return new ResultObject("-1","优惠券已作废",null);
			}
			long from_time = Long.parseLong(couponsNew.getFrom_time());
			long to_time = Long.parseLong(couponsNew.getTo_time());
			long ctime = System.currentTimeMillis()/1000;
			if(ctime<from_time){
				startTimeError += couponsNew.getMemc_code()+",";
			}
			if(ctime>to_time){
				endTimeError += couponsNew.getMemc_code()+",";
			}
		}
		if(startTimeError.length()>0){
			startTimeError = startTimeError.substring(0, startTimeError.length()-1);
			log.error("CartServiceImpl-----UsedCoupons--->优惠券["+startTimeError+"]还不到使用日期");
			return new ResultObject("-1","该优惠券未到使用时间",null);
		}
		if(endTimeError.length()>0){
			endTimeError = endTimeError.substring(0, endTimeError.length()-1);
			log.error("CartServiceImpl-----UsedCoupons--->优惠券["+endTimeError+"]已过期");
			return new ResultObject("-1","该优惠券已过期",null);
		}
		
//		String type_used = "";
//		//Set<String> cpns_types = new HashSet<String>();
//		for (CouponsNew couponsNew : couponsNews) {
//			String cpns_type = couponsNew.getCpns_type();
//			type_used = cpns_type;
//			//cpns_types.add(cpns_type);
//		}
//		/**验证优惠券代金券与满减券是否同时使用**/
//		if(cpns_types.size()>1){
//			log.error("CartServiceImpl-----UsedCoupons--->订单中代金券和满减券不能同时使用");
//			return new ResultObject("-1", "订单中代金券和满减券不能同时使用" ,null);
//		}
//		/**验证优惠券总使用张数**/
//		CouponsType couponsType = couponsDaoImpl.getCouponsTypeByType_used(type_used);
//		int order_limit = Integer.parseInt(couponsType.getOrder_limit());
//		int goods_limit = Integer.parseInt(couponsType.getGoods_limit());
//		if(cartGoodss.size()==1){
//			if(nums > goods_limit){
//				log.error("CartServiceImpl-----UsedCoupons--->订单最多只能使用["+goods_limit+"]张优惠券");
//				return new ResultObject("-1", "此类型优惠券在订单最多只能使用["+goods_limit+"]张" ,null);
//			}
//		}
//		if(nums > order_limit){
//			log.error("CartServiceImpl-----UsedCoupons--->订单最多只能使用["+order_limit+"]张优惠券");
//			return new ResultObject("-1", "此类型优惠券在订单最多只能使用["+order_limit+"]张" ,null);
//		}
		
		List<CouponsNew> dcn = new ArrayList<CouponsNew>();//代金券
		List<CouponsNew> mcn = new ArrayList<CouponsNew>();//满减券
		
		for (CouponsNew couponsNew : couponsNews) {
			String cpns_type = couponsNew.getCpns_type();
			if("1".equals(cpns_type)){
				dcn.add(couponsNew);
			}else if("2".equals(cpns_type)){
				mcn.add(couponsNew);
			}
		}
		
		//先使用满减券
		if(mcn.size()>0){
			List<CouponsNew> mcn1 = new ArrayList<CouponsNew>();//指定商品
			List<CouponsNew> mcn2 = new ArrayList<CouponsNew>();//指定分类
			List<CouponsNew> mcn3 = new ArrayList<CouponsNew>();//全场
			for (CouponsNew couponsNew : mcn) {
				String cpns_goods_type = couponsNew.getCpns_goods_type();
				if("1".equals(cpns_goods_type)){//指定商品
					mcn1.add(couponsNew);
				}else if("2".equals(cpns_goods_type) || "4".equals(cpns_goods_type)){
					mcn2.add(couponsNew);
				}else if("3".equals(cpns_goods_type)){
					mcn3.add(couponsNew);
				}
			}
			//先使用指定商品的优惠券
			if(mcn1.size()>0){
				for (CouponsNew couponsNew : mcn1) {
					List<CouponsGoods> couponsGoodss = couponsNew.getCouponsGoodss();
					List<String> product_ids = new ArrayList<String>();
					for (CouponsGoods couponsGoods : couponsGoodss) {
						String product_id = couponsGoods.getProduct_id();
						product_ids.add(product_id);
					}
					List<CartGoods> ft = new ArrayList<CartGoods>();//需要分摊的商品
					
					for (CartGoods cg : cartGoodss){
						String product_id = cg.getProduct_id();
						if(product_ids.contains(product_id)){
							ft.add(cg);
						}
					}
					if(ft.size()==0){//没有商品可用改优惠券
						return new ResultObject("-1", "该优惠券无法使用在当前订单中" ,null);
					}else{
						BigDecimal amt = new BigDecimal(0.00);//商品已优惠后的剩余总额
						BigDecimal toamt = new BigDecimal(0.00);//商品总额
						for (CartGoods cartGoods : ft) {//计算可使用商品的总金额
							BigDecimal decimal = new BigDecimal(cartGoods.getPrice()).multiply(new BigDecimal(cartGoods.getQuantity()));
							BigDecimal pmt = cartGoods.getPmt();
							amt = amt.add(decimal.subtract(pmt));
							toamt = toamt.add(decimal);
						}
						//满减券门槛金额
						String cpns_limit_price = couponsNew.getCpns_limit_price();//
						if(toamt.doubleValue()<Double.parseDouble(cpns_limit_price)){
							return new ResultObject("-1", "优惠券不满足满减要求" ,null);
						}
						//判断优惠金额是否大于等于当前商品总金额
						double cpns_price = Double.parseDouble(couponsNew.getCpns_price());
						
						if(amt.doubleValue()<cpns_price){
							for (CartGoods cartGoods : ft) {//设置为全额优惠
								BigDecimal decimal = new BigDecimal(cartGoods.getPrice()).multiply(new BigDecimal(cartGoods.getQuantity()));
								cartGoods.setPmt(decimal);
							}
						}else{
							int size = ft.size();
							int index = 0;
							BigDecimal dec = new BigDecimal(0.00);
							for (CartGoods cartGoods : ft) {//分摊
								BigDecimal pmt = cartGoods.getPmt();//已优惠金额
								index++;
								if(index==size){
									BigDecimal decimal = new BigDecimal(cartGoods.getPrice()).multiply(new BigDecimal(cartGoods.getQuantity()));
									BigDecimal sydec = decimal.subtract(pmt);//优惠剩余
									BigDecimal subtract = new BigDecimal(cpns_price).subtract(dec);
									if(subtract.doubleValue()>=sydec.doubleValue()){
										cartGoods.setPmt(decimal);
									}else{
										cartGoods.setPmt(pmt.add(subtract));//优惠叠加
									}
								}else{
									BigDecimal decimal = new BigDecimal(cartGoods.getPrice()).multiply(new BigDecimal(cartGoods.getQuantity()));
									BigDecimal subtract = decimal.subtract(pmt);
									BigDecimal divide = subtract.divide(amt,6,BigDecimal.ROUND_DOWN);
									BigDecimal multiply = new BigDecimal(cpns_price).multiply(divide);
									multiply = getBLJ(multiply);
									cartGoods.setPmt(pmt.add(multiply));//优惠叠加
									dec = dec.add(multiply);
								}
							}
						}
						Map<String, BigDecimal> pb = new HashMap<String, BigDecimal>();
						for (CartGoods cartGoods : ft) {
							String product_id = cartGoods.getProduct_id();
							BigDecimal pmt = cartGoods.getPmt();
							pb.put(product_id, pmt);
						}
						for (CartGoods cg : cartGoodss){
							String product_id = cg.getProduct_id();
							BigDecimal bigDecimal = pb.get(product_id);
							if(bigDecimal!=null){
								cg.setPmt(bigDecimal);
							}
						}
					}
				}
			}
			//其次使用指定分类的优惠券
			if(mcn2.size()>0){
				for (CouponsNew couponsNew : mcn2) {
					List<CouponsGoods> couponsGoodss = couponsNew.getCouponsGoodss();
					List<String> product_ids = new ArrayList<String>();
					for (CouponsGoods couponsGoods : couponsGoodss) {
						String product_id = couponsGoods.getProduct_id();
						product_ids.add(product_id);
					}
					List<CartGoods> ft = new ArrayList<CartGoods>();//需要分摊的商品
					for (CartGoods cg : cartGoodss){
						String product_id = cg.getProduct_id();
						if(product_ids.contains(product_id)){
							ft.add(cg);
						}
					}
					if(ft.size()==0){//没有商品可用改优惠券
						return new ResultObject("-1", "该优惠券无法使用在当前订单中" ,null);
					}else{
						BigDecimal amt = new BigDecimal(0.00);//商品已优惠后的剩余总额
						BigDecimal toamt = new BigDecimal(0.00);//商品总额
						for (CartGoods cartGoods : ft) {//计算可使用商品的总金额
							BigDecimal decimal = new BigDecimal(cartGoods.getPrice()).multiply(new BigDecimal(cartGoods.getQuantity()));
							BigDecimal pmt = cartGoods.getPmt();
							amt = amt.add(decimal.subtract(pmt));
							toamt = toamt.add(decimal);
						}
						//满减券门槛金额
						String cpns_limit_price = couponsNew.getCpns_limit_price();//
						if(toamt.doubleValue()<Double.parseDouble(cpns_limit_price)){
							return new ResultObject("-1", "优惠券不满足满减要求" ,null);
						}
						//判断优惠金额是否大于等于当前商品总金额
						double cpns_price = Double.parseDouble(couponsNew.getCpns_price());
						
						if(amt.doubleValue()<cpns_price){
							for (CartGoods cartGoods : ft) {//设置为全额优惠
								BigDecimal decimal = new BigDecimal(cartGoods.getPrice()).multiply(new BigDecimal(cartGoods.getQuantity()));
								cartGoods.setPmt(decimal);
							}
						}else{
							int size = ft.size();
							int index = 0;
							BigDecimal dec = new BigDecimal(0.00);
							for (CartGoods cartGoods : ft) {//分摊
								BigDecimal pmt = cartGoods.getPmt();//已优惠金额
								index++;
								if(index==size){
									BigDecimal decimal = new BigDecimal(cartGoods.getPrice()).multiply(new BigDecimal(cartGoods.getQuantity()));
									BigDecimal sydec = decimal.subtract(pmt);//优惠剩余
									BigDecimal subtract = new BigDecimal(cpns_price).subtract(dec);
									if(subtract.doubleValue()>=sydec.doubleValue()){
										cartGoods.setPmt(decimal);
									}else{
										cartGoods.setPmt(pmt.add(subtract));//优惠叠加
									}
								}else{
									BigDecimal decimal = new BigDecimal(cartGoods.getPrice()).multiply(new BigDecimal(cartGoods.getQuantity()));
									BigDecimal subtract = decimal.subtract(pmt);
									BigDecimal divide = subtract.divide(amt,6,BigDecimal.ROUND_DOWN);
									BigDecimal multiply = new BigDecimal(cpns_price).multiply(divide);
									multiply = getBLJ(multiply);
									cartGoods.setPmt(pmt.add(multiply));//优惠叠加
									dec = dec.add(multiply);
								}
							}
						}
						Map<String, BigDecimal> pb = new HashMap<String, BigDecimal>();
						for (CartGoods cartGoods : ft) {
							String product_id = cartGoods.getProduct_id();
							BigDecimal pmt = cartGoods.getPmt();
							pb.put(product_id, pmt);
						}
						for (CartGoods cg : cartGoodss){
							String product_id = cg.getProduct_id();
							BigDecimal bigDecimal = pb.get(product_id);
							if(bigDecimal!=null){
								cg.setPmt(bigDecimal);
							}
						}
					}
				}			
			}
			//最后使用全场优惠券
			if(mcn3.size()>0){
				for (CouponsNew couponsNew : mcn3) {
					BigDecimal amt = new BigDecimal(0.00);//商品已优惠后的剩余总额
					BigDecimal toamt = new BigDecimal(0.00);//商品总额
					for (CartGoods cartGoods : cartGoodss) {//计算可使用商品的总金额
						BigDecimal decimal = new BigDecimal(cartGoods.getPrice()).multiply(new BigDecimal(cartGoods.getQuantity()));
						BigDecimal pmt = cartGoods.getPmt();
						amt = amt.add(decimal.subtract(pmt));
						toamt = toamt.add(decimal);
					}
					//满减券门槛金额
					String cpns_limit_price = couponsNew.getCpns_limit_price();//
					if(toamt.doubleValue()<Double.parseDouble(cpns_limit_price)){
						return new ResultObject("-1", "优惠券不满足满减要求" ,null);
					}
					//判断优惠金额是否大于等于当前商品总金额
					double cpns_price = Double.parseDouble(couponsNew.getCpns_price());
					if(amt.doubleValue()<cpns_price){
						for (CartGoods cartGoods : cartGoodss) {//设置为全额优惠
							BigDecimal decimal = new BigDecimal(cartGoods.getPrice()).multiply(new BigDecimal(cartGoods.getQuantity()));
							cartGoods.setPmt(decimal);
						}
					}else{
						int size = cartGoodss.size();
						int index = 0;
						BigDecimal dec = new BigDecimal(0.00);
						for (CartGoods cartGoods : cartGoodss) {//分摊
							BigDecimal pmt = cartGoods.getPmt();//已优惠金额
							index++;
							if(index==size){
								BigDecimal decimal = new BigDecimal(cartGoods.getPrice()).multiply(new BigDecimal(cartGoods.getQuantity()));
								BigDecimal sydec = decimal.subtract(pmt);//优惠剩余
								BigDecimal subtract = new BigDecimal(cpns_price).subtract(dec);
								if(subtract.doubleValue()>=sydec.doubleValue()){
									cartGoods.setPmt(decimal);
								}else{
									cartGoods.setPmt(pmt.add(subtract));//优惠叠加
								}
							}else{
								BigDecimal decimal = new BigDecimal(cartGoods.getPrice()).multiply(new BigDecimal(cartGoods.getQuantity()));
								BigDecimal subtract = decimal.subtract(pmt);
								BigDecimal divide = subtract.divide(amt,6,BigDecimal.ROUND_DOWN);
								BigDecimal multiply = new BigDecimal(cpns_price).multiply(divide);
								multiply = getBLJ(multiply);
								cartGoods.setPmt(pmt.add(multiply));//优惠叠加
								dec = dec.add(multiply);
							}
						}
					}
				}
			}
		}
		
		//再使用代金券
		if(dcn.size()>0){
			List<CouponsNew> dcn1 = new ArrayList<CouponsNew>();//指定商品
			List<CouponsNew> dcn2 = new ArrayList<CouponsNew>();//指定分类
			List<CouponsNew> dcn3 = new ArrayList<CouponsNew>();//全场
			for (CouponsNew couponsNew : dcn) {
				String cpns_goods_type = couponsNew.getCpns_goods_type();
				if("1".equals(cpns_goods_type)){//指定商品
					dcn1.add(couponsNew);
				}else if("2".equals(cpns_goods_type) ||  "4".equals(cpns_goods_type)){
					dcn2.add(couponsNew);
				}else if("3".equals(cpns_goods_type)){
					dcn3.add(couponsNew);
				}
			}
			//先使用指定商品的优惠券
			if(dcn1.size()>0){
				for (CouponsNew couponsNew : dcn1) {
					List<CouponsGoods> couponsGoodss = couponsNew.getCouponsGoodss();
					List<String> product_ids = new ArrayList<String>();
					for (CouponsGoods couponsGoods : couponsGoodss) {
						String product_id = couponsGoods.getProduct_id();
						product_ids.add(product_id);
					}
					List<CartGoods> ft = new ArrayList<CartGoods>();//需要分摊的商品
					for (CartGoods cg : cartGoodss){
						String product_id = cg.getProduct_id();
						if(product_ids.contains(product_id)){
							ft.add(cg);
						}
					}
					if(ft.size()==0){//没有商品可用改优惠券
						return new ResultObject("-1", "该优惠券无法使用在当前订单中" ,null);
					}else{
						BigDecimal amt = new BigDecimal(0.00);//商品已优惠后的剩余总额
						for (CartGoods cartGoods : ft) {//计算可使用商品的总金额
							BigDecimal decimal = new BigDecimal(cartGoods.getPrice()).multiply(new BigDecimal(cartGoods.getQuantity()));
							BigDecimal pmt = cartGoods.getPmt();
							amt = amt.add(decimal.subtract(pmt));
						}
						//判断优惠金额是否大于等于当前商品总金额
						double cpns_price = Double.parseDouble(couponsNew.getCpns_price());
						
						if(amt.doubleValue()<cpns_price){
							for (CartGoods cartGoods : ft) {//设置为全额优惠
								BigDecimal decimal = new BigDecimal(cartGoods.getPrice()).multiply(new BigDecimal(cartGoods.getQuantity()));
								cartGoods.setPmt(decimal);
							}
						}else{
							int size = ft.size();
							int index = 0;
							BigDecimal dec = new BigDecimal(0.00);
							for (CartGoods cartGoods : ft) {//分摊
								BigDecimal pmt = cartGoods.getPmt();//已优惠金额
								index++;
								if(index==size){
									BigDecimal decimal = new BigDecimal(cartGoods.getPrice()).multiply(new BigDecimal(cartGoods.getQuantity()));
									BigDecimal sydec = decimal.subtract(pmt);//优惠剩余
									BigDecimal subtract = new BigDecimal(cpns_price).subtract(dec);
									if(subtract.doubleValue()>=sydec.doubleValue()){
										cartGoods.setPmt(decimal);
									}else{
										cartGoods.setPmt(pmt.add(subtract));//优惠叠加
									}
								}else{
									BigDecimal decimal = new BigDecimal(cartGoods.getPrice()).multiply(new BigDecimal(cartGoods.getQuantity()));
									BigDecimal subtract = decimal.subtract(pmt);
									BigDecimal divide = subtract.divide(amt,6,BigDecimal.ROUND_DOWN);
									BigDecimal multiply = new BigDecimal(cpns_price).multiply(divide);
									multiply = getBLJ(multiply);
									cartGoods.setPmt(pmt.add(multiply));//优惠叠加
									dec = dec.add(multiply);
								}
							}
						}
						Map<String, BigDecimal> pb = new HashMap<String, BigDecimal>();
						for (CartGoods cartGoods : ft) {
							String product_id = cartGoods.getProduct_id();
							BigDecimal pmt = cartGoods.getPmt();
							pb.put(product_id, pmt);
						}
						for (CartGoods cg : cartGoodss){
							String product_id = cg.getProduct_id();
							BigDecimal bigDecimal = pb.get(product_id);
							if(bigDecimal!=null){
								cg.setPmt(bigDecimal);
							}
						}
					}
				}
			}
			//其次使用指定分类的优惠券
			if(dcn2.size()>0){
				for (CouponsNew couponsNew : dcn2) {
					List<CouponsGoods> couponsGoodss = couponsNew.getCouponsGoodss();
					List<String> product_ids = new ArrayList<String>();
					for (CouponsGoods couponsGoods : couponsGoodss) {
						String product_id = couponsGoods.getProduct_id();
						product_ids.add(product_id);
					}
					List<CartGoods> ft = new ArrayList<CartGoods>();//需要分摊的商品
					for (CartGoods cg : cartGoodss){
						String product_id = cg.getProduct_id();
						if(product_ids.contains(product_id)){
							ft.add(cg);
						}
					}
					if(ft.size()==0){//没有商品可用改优惠券
						return new ResultObject("-1", "该优惠券无法使用在当前订单中" ,null);
					}else{
						BigDecimal amt = new BigDecimal(0.00);//商品已优惠后的剩余总额
						for (CartGoods cartGoods : ft) {//计算可使用商品的总金额
							BigDecimal decimal = new BigDecimal(cartGoods.getPrice()).multiply(new BigDecimal(cartGoods.getQuantity()));
							BigDecimal pmt = cartGoods.getPmt();
							amt = amt.add(decimal.subtract(pmt));
						}
						//判断优惠金额是否大于等于当前商品总金额
						double cpns_price = Double.parseDouble(couponsNew.getCpns_price());
						
						if(amt.doubleValue()<cpns_price){
							for (CartGoods cartGoods : ft) {//设置为全额优惠
								BigDecimal decimal = new BigDecimal(cartGoods.getPrice()).multiply(new BigDecimal(cartGoods.getQuantity()));
								cartGoods.setPmt(decimal);
							}
						}else{
							int size = ft.size();
							int index = 0;
							BigDecimal dec = new BigDecimal(0.00);
							for (CartGoods cartGoods : ft) {//分摊
								BigDecimal pmt = cartGoods.getPmt();//已优惠金额
								index++;
								if(index==size){
									BigDecimal decimal = new BigDecimal(cartGoods.getPrice()).multiply(new BigDecimal(cartGoods.getQuantity()));
									BigDecimal sydec = decimal.subtract(pmt);//优惠剩余
									BigDecimal subtract = new BigDecimal(cpns_price).subtract(dec);
									if(subtract.doubleValue()>=sydec.doubleValue()){
										cartGoods.setPmt(decimal);
									}else{
										cartGoods.setPmt(pmt.add(subtract));//优惠叠加
									}
								}else{
									BigDecimal decimal = new BigDecimal(cartGoods.getPrice()).multiply(new BigDecimal(cartGoods.getQuantity()));
									BigDecimal subtract = decimal.subtract(pmt);
									BigDecimal divide = subtract.divide(amt,6,BigDecimal.ROUND_DOWN);
									BigDecimal multiply = new BigDecimal(cpns_price).multiply(divide);
									multiply = getBLJ(multiply);
									cartGoods.setPmt(pmt.add(multiply));//优惠叠加
									dec = dec.add(multiply);
								}
							}
						}
						Map<String, BigDecimal> pb = new HashMap<String, BigDecimal>();
						for (CartGoods cartGoods : ft) {
							String product_id = cartGoods.getProduct_id();
							BigDecimal pmt = cartGoods.getPmt();
							pb.put(product_id, pmt);
						}
						for (CartGoods cg : cartGoodss){
							String product_id = cg.getProduct_id();
							BigDecimal bigDecimal = pb.get(product_id);
							if(bigDecimal!=null){
								cg.setPmt(bigDecimal);
							}
						}
					}
				}			
			}
			//最后使用全场优惠券
			if(dcn3.size()>0){
				for (CouponsNew couponsNew : dcn3) {
					BigDecimal amt = new BigDecimal(0.00);//商品已优惠后的剩余总额
					for (CartGoods cartGoods : cartGoodss) {//计算可使用商品的总金额
						BigDecimal decimal = new BigDecimal(cartGoods.getPrice()).multiply(new BigDecimal(cartGoods.getQuantity()));
						BigDecimal pmt = cartGoods.getPmt();
						amt = amt.add(decimal.subtract(pmt));
					}
					//判断优惠金额是否大于等于当前商品总金额
					double cpns_price = Double.parseDouble(couponsNew.getCpns_price());
					if(amt.doubleValue()<cpns_price){
						for (CartGoods cartGoods : cartGoodss) {//设置为全额优惠
							BigDecimal decimal = new BigDecimal(cartGoods.getPrice()).multiply(new BigDecimal(cartGoods.getQuantity()));
							cartGoods.setPmt(decimal);
						}
					}else{
						int size = cartGoodss.size();
						int index = 0;
						BigDecimal dec = new BigDecimal(0.00);
						for (CartGoods cartGoods : cartGoodss) {//分摊
							BigDecimal pmt = cartGoods.getPmt();//已优惠金额
							index++;
							if(index==size){
								BigDecimal decimal = new BigDecimal(cartGoods.getPrice()).multiply(new BigDecimal(cartGoods.getQuantity()));
								BigDecimal sydec = decimal.subtract(pmt);//优惠剩余
								BigDecimal subtract = new BigDecimal(cpns_price).subtract(dec);
								if(subtract.doubleValue()>=sydec.doubleValue()){
									cartGoods.setPmt(decimal);
								}else{
									cartGoods.setPmt(pmt.add(subtract));//优惠叠加
								}
							}else{
								BigDecimal decimal = new BigDecimal(cartGoods.getPrice()).multiply(new BigDecimal(cartGoods.getQuantity()));
								BigDecimal subtract = decimal.subtract(pmt);
								BigDecimal divide = subtract.divide(amt,6,BigDecimal.ROUND_DOWN);
								BigDecimal multiply = new BigDecimal(cpns_price).multiply(divide);
								multiply = getBLJ(multiply);
								cartGoods.setPmt(pmt.add(multiply));//优惠叠加
								dec = dec.add(multiply);
							}
						}
					}
				}
			}
		}

//		/**验证代金券是否可以使用在该订单中**/
//		int size = cartGoodss.size();
//		Map<CouponsNew, List<CartGoods>> couponsNewMap = new HashMap<CouponsNew, List<CartGoods>>();
//		
//		for (CouponsNew couponsNew : couponsNews) {
//			List<CouponsGoods> couponsGoodss = couponsNew.getCouponsGoodss();
//			List<String> product_ids2 = new ArrayList<String>();
//			for (CouponsGoods couponsGoods : couponsGoodss) {
//				String product_id = couponsGoods.getProduct_id();
//				product_ids2.add(product_id);
//			}
//			int index = 0;
//			for (CartGoods cartGoods : cartGoodss) {
//				String cpns_goods_type = couponsNew.getCpns_goods_type();
//				/**
//				 * 1代表指定商品都可使用
//				 * 2代表指定分类都可使用
//				 * 3代表所有商品都可使用
//				 **/
//				if("3".equals(cpns_goods_type)){
//					if(couponsNewMap.get(couponsNew)==null){
//						List<CartGoods> cgs = new ArrayList<CartGoods>();
//						cgs.add(cartGoods);
//						couponsNewMap.put(couponsNew,cgs);
//					}else{
//						List<CartGoods> cgs = couponsNewMap.get(couponsNew);
//						cgs.add(cartGoods);
//						couponsNewMap.put(couponsNew,cgs);
//					}
//				}else{
//					String product_id = cartGoods.getProduct_id();
//					if(!product_ids2.contains(product_id)){
//						index++;
//					}else{
//						/**可使用的话,插入某一优惠券可以使用在那些商品上**/
//						if(couponsNewMap.get(couponsNew)==null){
//							List<CartGoods> cgs = new ArrayList<CartGoods>();
//							cgs.add(cartGoods);
//							couponsNewMap.put(couponsNew,cgs);
//						}else{
//							List<CartGoods> cgs = couponsNewMap.get(couponsNew);
//							cgs.add(cartGoods);
//							couponsNewMap.put(couponsNew,cgs);
//						}
//					}
//				}
//			}
//			if(index==size){
//				String code = couponsNew.getMemc_code();
//				log.error("CartServiceImpl-----UsedCoupons--->优惠券["+code+"]不可以使用在该订单中!");
//				return new ResultObject("-1","抱歉，此优惠券只能购买指定的商品",null);
//			}
//		}
//		/**优惠券由大到小排序根据价格 ,商品也同样根据价格由大到小排序**/
//		ComparatorCouponsNew ccn = new ComparatorCouponsNew();
//		Collections.sort(couponsNews, ccn);
//		Set<CouponsNew> keySet = couponsNewMap.keySet();
//		for (CouponsNew couponsNew : keySet) {
//			List<CartGoods> list = couponsNewMap.get(couponsNew);
//			ComparatorCartGoods ccg = new ComparatorCartGoods();
//			Collections.sort(list,ccg);
//			System.out.println();
//		}
//		int goods_limit = 0;
//		for (CouponsNew couponsNew : couponsNews) {
//			List<CartGoods> list = couponsNewMap.get(couponsNew);
//			double cpns_price = Double.parseDouble(couponsNew.getCpns_price());
//			for (CartGoods cartGoods : list) {
//				double quantity = Double.parseDouble(cartGoods.getQuantity());
//				double price = Double.parseDouble(cartGoods.getPrice());
//				double total_price = quantity*price;
//				if("3".equals(cartGoods.getArea_type())){
//					/**海外商品判断单价而不是小计**/
//					total_price = price;
//				}
//				List<CouponsNew> cns = cartGoods.getCouponsNews();
//				double cps = 0.0;
//				for (CouponsNew cn : cns) {
//					double cp = Double.parseDouble(cn.getCpns_price());
//					cps+=cp;
//				}
//				/**判断如果使用了当前优惠券是否会长度过长或价格超标**/
//				if((cpns_price+cps) <= total_price){
//					if((cns.size()+1)<=goods_limit){
//						if("2".equals(couponsNew.getCpns_type())){
//							double cpns_limit_price = Double.parseDouble(couponsNew.getCpns_limit_price());
//							if(total_price >= cpns_limit_price){
//								cns.add(couponsNew);
//								/**如果可以使用当前优惠券则在商品上设置使用了该优惠券**/
//								cartGoods.setCouponsNews(cns);
//								break;
//							}
//						}else{
//							cns.add(couponsNew);
//							cartGoods.setCouponsNews(cns);
//							break;
//						}
//					}
//				}
//			}
//		}
//		/**取出使用过的优惠券,再从总使用优惠券中去除已使用过的
//		 * 剩余的则是长度过长或价格超标的
//		 * 判断货物已使用的优惠券是否满足最大长度,若满足则是长度过长,不满足则是价格超标
//		 * **/
//		List<CartGoods> cgs = new ArrayList<CartGoods>();
//		Set<CouponsNew> keySet2 = couponsNewMap.keySet();
//		for (CouponsNew couponsNew : keySet2) {
//			List<CartGoods> list = couponsNewMap.get(couponsNew);
//			for (CartGoods cartGoods : list) {
//				List<CouponsNew> cns = cartGoods.getCouponsNews();
//				if(cns.size()>0){
//					if(!cgs.contains(cartGoods)){
//						cgs.add(cartGoods);
//					}
//				}
//			}
//		}
//		List<CouponsNew> cns = new ArrayList<CouponsNew>();
//		for (CartGoods cg : cgs) {
//			List<CouponsNew> list = cg.getCouponsNews();
//			for (CouponsNew couponsNew : list) {
//				cns.add(couponsNew);
//			}
//		}
//		List<CouponsNew> cns2 = new ArrayList<CouponsNew>();
//		for (CouponsNew couponsNew : couponsNews) {
//			if(!cns.contains(couponsNew)){
//				cns2.add(couponsNew);
//			}
//		}
//		
//		for (CouponsNew couponsNew : cns2) {
//			List<CartGoods> list = couponsNewMap.get(couponsNew);
//			for (CartGoods cartGoods : list) {
//				List<CouponsNew> couponsNews2 = cartGoods.getCouponsNews();
//				if(couponsNews2.size()==goods_limit){
//					log.error("CartServiceImpl-----UsedCoupons--->"+cartGoods.getName()+"最多只能使用["+goods_limit+"]张优惠券");
//					return new ResultObject("-1","订单中针对单品使用优惠券张数过多",null);
//				}else{
//					if("2".equals(couponsNew.getCpns_type())){
//						log.error("CartServiceImpl-----UsedCoupons--->"+cartGoods.getName()+"所使用优惠券["+couponsNew.getMemc_code()+"]商品未达到满减要求");
//						return new ResultObject("-1","优惠券未达到满减要求",null);
//					}else{
//						log.error("CartServiceImpl-----UsedCoupons--->"+cartGoods.getName()+"所使用优惠券累积额度过高,不能再使用["+couponsNew.getMemc_code()+"]");
//						return new ResultObject("-1","订单中针对单品使用优惠券额度过高",null);
//					}
//				}
//			}
//		}
//		/**加入其它未使用优惠券的商品**/
//		Map<String,CartGoods> cgm = new HashMap<String,CartGoods>();
//		for (CartGoods cg : cgs) {
//			cgm.put(cg.getProduct_id(), cg);
//		}
//		for (CartGoods cg : cartGoodss) {
//			if(!cgm.containsKey(cg.getProduct_id())){
//				cgm.put(cg.getProduct_id(), cg);
//			}
//		}
//		cgs = new ArrayList<CartGoods>();
//		Set<String> cgmkeySet = cgm.keySet();
//		for (String product_id : cgmkeySet) {
//			CartGoods cartGoods = cgm.get(product_id);
//			cgs.add(cartGoods);
//		}
//		/**处理优惠券具体信息**/
//		BigDecimal pmt_order = new BigDecimal(0.0);
//		List<CouponsNew> couponsNewsr1 = new ArrayList<CouponsNew>();
//		for (CartGoods cartGoods : cgs) {
//			List<CouponsNew> cnsr = cartGoods.getCouponsNews();
//			for (CouponsNew couponsNew : cnsr) {
//				couponsNew.setUse_flag("1");
//				couponsNew.setUse_falg_desc("可使用");
//				couponsNewsr1.add(couponsNew);
//				pmt_order = pmt_order.add(new BigDecimal(couponsNew.getCpns_price()));
//			}
//		}
//		Map <String, BigDecimal> pmt = new HashMap <String, BigDecimal> ();
//		pmt.put("pmt_order",pmt_order);
//		Map amount = orderServiceImpl.getOrderCTPCP(cgs,pmt);
//		List<CouponsRequest> Coupons = new ArrayList<CouponsRequest>();
//		for (CouponsNew couponsNew : couponsNewsr1) {
//			CouponsRequest cr = new CouponsRequest();
//			BeanUtils.copyProperties(cr, couponsNew);
//			if("1".equals(cr.getCpns_type())){
//				String cpns_price = cr.getCpns_price();
//				cr.setCpns_type_hz(cpns_price+"元");
//			}else if("2".equals(cr.getCpns_type())){
//				String cpns_limit_price = cr.getCpns_limit_price();
//				cr.setCpns_type_hz("满"+cpns_limit_price+"元使用");
//			}
//			String from_time = formatTime(cr.getFrom_time());
//			String to_time = formatTime(cr.getTo_time());
//			cr.setFrom_time(from_time);
//			cr.setTo_time(to_time);
//			Coupons.add(cr);
//		}
//		log.info("优惠券使用个数------------>"+Coupons.size());
//		for (CouponsRequest couponsRequest : Coupons) {
//			log.info("优惠券详细信息---------->:"+couponsRequest.toString());
//		}
//		Set keySetw = amount.keySet();
//		for (Object key : keySetw) {
//			Object value = amount.get(key);
//			String str = value.toString();
//			amount.put(key, str);
//		}
		
		Map amount = new HashMap();
		amount.put("coupons", couponsNews);
		amount.put("products", cartGoodss);
		long endTime = System.currentTimeMillis();
		log.info("CartServiceImpl-----UsedCoupons--->优惠券验证接口调用完毕,用时:"+(endTime-startTime)+"毫秒");
		return new ResultObject("1","success",amount);
	}
	
//	private String formatTime(String time) {
//		long parseLong = Long.parseLong(time)*1000;
//		Date date = new Date(parseLong);
//		DateFormat mFormat = new SimpleDateFormat("yyyy年MM月dd日");
//		String format = mFormat.format(date);
//		return format;
//	}
	
	@Override
	public List<CouponsNew> getCouponsNewByMemc_code(String memc_code) throws Exception {
		return couponsDaoImpl.getCouponsNewByMemc_code(memc_code);
	}

	@Override
	public List<String> getOrder_ids(String order_rel) throws Exception {
		return orderDao.getOrder_ids(order_rel);
	}

	@Override
	public List<Map> getWebOrderList(Checksum checkSum,String member_id, Integer pageNum,
			String flag) throws Exception {
		List<OrderList> orderLists = getWebOrderListy(checkSum,member_id, pageNum, flag);
		Map<String, List<OrderList>> map = new HashMap<String, List<OrderList>>();
		for (OrderList orderList : orderLists) {
			String order_rel = orderList.getOrder_rel();
			List<OrderList> list = map.get(order_rel);
			if(list==null){
				list = new ArrayList<OrderList>();
			}
			list.add(orderList);
			map.put(order_rel, list);
		}
		Map<String, List<OrderList>> hemap = new HashMap<String, List<OrderList>>();
		Map<String, List<OrderList>> fenmap = new HashMap<String, List<OrderList>>();
		
		Set<String> mapkeySet = map.keySet();
		for (String order_rel : mapkeySet) {
			List<OrderList> list = map.get(order_rel);
			OrderList orderList = list.get(0);
			String status = orderList.getStatus();
			if("已完成".equals(status) || "等待收货".equals(status)){
				fenmap.put(order_rel, list);
			}else{//取消 等待审核 等待支付
				hemap.put(order_rel, list);
			}
		}
		
		
		Map<Long, Map> sMap = new HashMap<Long,Map>();
		
		Set<String> hekeySet = hemap.keySet();
		for (String order_rel : hekeySet) {
			List<OrderList> list = hemap.get(order_rel);
			OrderList orderList = list.get(0);
			BigDecimal total_amount = new BigDecimal(0.00);
			List<Map<String, String>> goodsList = new ArrayList<Map<String,String>>();
			for (OrderList ol : list) {
				total_amount = total_amount.add(new BigDecimal(ol.getTotal_amount()));
				goodsList.addAll(ol.getGoodsList());
			}
			orderList.setOrder_id(order_rel);
			orderList.setTotal_amount(total_amount.toString());
			orderList.setGoodsList(goodsList);
			long createtime = Long.parseLong(orderList.getTimez());
			List<OrderList> ols = new ArrayList<OrderList>();
			ols.add(orderList);
			Map m = new HashMap();
			m.put("count",String.valueOf(ols.size()) );
			m.put("orderType", "fold");
			m.put("orderList", ols);
			sMap.put(createtime, m);
		}
		
		Set<String> fenkeySet = fenmap.keySet();
		for (String order_rel : fenkeySet) {
			List<OrderList> list = fenmap.get(order_rel);
			long createtime = Long.parseLong(list.get(0).getTimez());
			Map m = new HashMap();
			m.put("count", String.valueOf(list.size()));
			m.put("orderType", "unfold");
			if(list.size()==1){
				m.put("orderType", "fold");
			}
			m.put("orderList", list);
			sMap.put(createtime, m);
		}
		Set<Long> skeySet = sMap.keySet();
		List<Long> lsList = new ArrayList<Long>();
		for (Long l : skeySet) {
			lsList.add(l);
		}
		Collections.sort(lsList);
		
		List<Map> rMaps = new ArrayList<Map>();
		for (int i = lsList.size()-1; i >=0 ; i--) {
			Map m = sMap.get(lsList.get(i));
			rMaps.add(m);
		}
		return rMaps;
	}

	@Override
	public List<OrderList> getWebOrderListy(Checksum checkSum,String member_id, Integer pageNum,String status) throws Exception {
		List<Map<String,Object>> list = orderDao.getWebOrderList(checkSum,member_id,pageNum,status);
		List<OrderList> orderList = new ArrayList<OrderList>();;
		Map<String,Map<String,Object>> orderMap = new LinkedHashMap<String,Map<String,Object>>();
		for(Map<String,Object> map:list){
			orderMap.put(map.get("order_id").toString(),map);
		}
		for(String str : orderMap.keySet()){
			OrderList order = new OrderList();
			Map m = orderMap.get(str);
			order.setOrder_id(str);
			order.setOrder_rel(m.get("order_rel").toString());
			order.setCreatetime(m.get("createtime").toString());
			order.setStatus(m.get("status").toString());
			order.setTimez(m.get("timez").toString());
			order.setShip_name(m.get("ship_name").toString());
			String source = m.get("source").toString();
			order.setSource(source);
			String payment = m.get("payment").toString();
			order.setPayment(payment);
			if("offline".equals(payment)){
				order.setPaymentDesc("货到付款");
			}else{
				order.setPaymentDesc("在线支付");
			}
			if(m.get("status").toString().contains("等待支付")){
				order.setStatusflag("1");//等待支付
			}else if(m.get("status").toString().contains("已完成")){
				order.setStatusflag("2");
			}else if(m.get("status").toString().contains("取消")){
				order.setStatusflag("3");
			}else if(m.get("status").toString().contains("等待审核")){
				order.setStatusflag("4");
			}else if(m.get("status").toString().contains("等待收货")){
				order.setStatusflag("5");
			}
			order.setTotal_amount(m.get("total_amount").toString());
			
			//发货地转成汉字发前台
			order.setFrom_sto(m.get("from_sto").toString());
			//发货地转成汉字发前台
			if(m.get("from_sto").toString().equalsIgnoreCase("1")){
				order.setShipping_to("");//大陆  人为定的
			}
			if(m.get("from_sto").toString().equalsIgnoreCase("4")){
				order.setShipping_to("");//6大仓
			}
			if(m.get("from_sto").toString().equalsIgnoreCase("5")){
				order.setShipping_to("");//
			}
			if(m.get("from_sto").toString().equalsIgnoreCase("2")){
				order.setShipping_to("保税");
			}
			if(m.get("from_sto").toString().equalsIgnoreCase("3")){
				order.setShipping_to("直邮");
			}
			List<Map<String,String>> goodsList = new ArrayList<Map<String,String>>();
			for(Map<String,Object> map:list){
				if(str.equals(map.get("order_id").toString())){
					Map<String,String> ms= new HashMap<String,String>(); 
					ms.put("url",map.get("url").toString());
					ms.put("name",map.get("name").toString());
					ms.put("quantitiy",map.get("quantity").toString());
					ms.put("obj_type",map.get("obj_type").toString());
					ms.put("from_sto", map.get("from_sto").toString());
					ms.put("product_id", map.get("product_id").toString());
					//发货地转成汉字发前台
					if(map.get("from_sto").toString().equalsIgnoreCase("1")){
						ms.put("shipping_to","");
					}
					if(map.get("from_sto").toString().equalsIgnoreCase("4")){
						ms.put("shipping_to","");
					}
					if(map.get("from_sto").toString().equalsIgnoreCase("4")){
						ms.put("shipping_to","");
					}
					if(map.get("from_sto").toString().equalsIgnoreCase("2")){
						ms.put("shipping_to", "保税");
					}
					if(map.get("from_sto").toString().equalsIgnoreCase("3")){
						ms.put("shipping_to", "直邮");
					}
					goodsList.add(ms);
				}
			}
			order.setGoodsList(goodsList);
			orderList.add(order);
		}
		return orderList;
	}

	@Override
	public Integer verificationOZF(String order_rel) throws Exception {
		return orderDao.verificationOZF(order_rel);
	}

	@Override
	public int getWebOrderNum(String member_id,String status) throws Exception {
		return orderDao.getWebOrderNum(member_id, status);
	}

	@Override
	public void cancelLocalOrder(Checksum checkSum, String order_id,
			String member_id, String flag, String reason)
			throws Exception {
		//判断库存  特卖是否压单   海外
		// is_limitbuy('0','1')  是否特卖 0:否 1:是             used_stor('0','1','2')  压单状态   0:不压单  1:网站压单  2:比酷压单网站压单 sdb_b2c_orders_item
		//通过order_id 取的订单货品表
		//is_overseas;   发货地  1大货   2保税  3海外 4 6大仓     大陆，保税的商品比酷和本地都锁库存了             海外的只在本地锁库存  sdb_b2c_orders
		
		Map<String, Object> orderProductBean = new HashMap<String, Object>();//订单下对应的商品集合      既在比酷又在本地还库存的
		Map<String, String> orderBkIds = new HashMap<String, String>();  //取消订单 同步比酷用到的比酷id
		List<CartGoods> productIdandNums = new ArrayList<CartGoods>(); //取消订单        只还本地库存的
		//Map<String, List<CartGoods>> cancelbendi = new HashMap<String, List<CartGoods>>();//只还本地库存的所有商品 
		List<CancelOrderItemsInventory> orderitemsss = orderDao.getYaDanProductByOrder_id(order_id,member_id);
		if(null != orderitemsss){
			for(CancelOrderItemsInventory oitem : orderitemsss){
				//只有大陆，保税的商品才会走取消比酷接品返回库存量
				if(oitem.getIs_overseas().equals("1")|| oitem.getIs_overseas().equals("2")||oitem.getIs_overseas().equals("4")){
					//即取消本地库存又取消比酷库存
					if(oitem.getIs_limitbuy().equals("1") && oitem.getUsed_store().equals("2")){
					//调比酷接口取消库存用到的orderBk_id
						
						orderBkIds.put(oitem.getOrder_id(), oitem.getBk_no_id());
				    //把该订单货品数量加回到占有量上  (sdb_b2c_products  limitbuy_us_quantity已占库存量)传入值 Product_id Nums
						//orderDao.addNumsToLimitbuyusQuantity(oitem);
					}
					//只释放本地库存量  特买商品只在本地锁库存
					else if(oitem.getIs_limitbuy().equals("1") && oitem.getUsed_store().equals("1")){
						//orderDao.addNumsToLimitbuyusQuantity(oitem);
						CartGoods idandnums = new CartGoods();
						idandnums.setProduct_id(oitem.getProduct_id());
						idandnums.setQuantity(oitem.getNums());
						idandnums.setOrder_id(oitem.getOrder_id());
						idandnums.setGoodsflag(oitem.getIs_limitbuy());
						productIdandNums.add(idandnums);
						//cancelbendi.put(oitem.getOrder_id(), productIdandNums);//只取消本地库存商品
					}else{//大陆和保税的普通货品    如果比酷压单只对比酷释放    本地不压单
						if(oitem.getUsed_store().equals("2")){
							//调比酷接口取消库存用到的orderBk_id
							orderBkIds.put(oitem.getOrder_id(), oitem.getBk_no_id());
						}
					}
					
				}else{//海外的只在本地锁库存(返回要本地库存)
					if(!oitem.getUsed_store().equals("0")){
						CartGoods idandnums = new CartGoods();
						idandnums.setProduct_id(oitem.getProduct_id());
						idandnums.setQuantity(oitem.getNums());
						idandnums.setOrder_id(oitem.getOrder_id());
						idandnums.setGoodsflag(oitem.getIs_limitbuy());
						productIdandNums.add(idandnums);
						//cancelbendi.put(oitem.getOrder_id(), productIdandNums);//只取消本地库存商品
					}
				}
			}
			
			for(String key : orderBkIds.keySet()){//既在比酷又在本地还库存的
				List<CartGoods> ordercancel = new ArrayList<CartGoods>();
				for(CancelOrderItemsInventory oitems : orderitemsss){
					if(key.equals(oitems.getOrder_id())){
						CartGoods cg = new CartGoods();
						cg.setQuantity(oitems.getNums());
						cg.setProduct_id(oitems.getProduct_id());
						cg.setGoodsflag(oitems.getIs_limitbuy());
						ordercancel.add(cg);
					}
				}
				orderProductBean.put(key, ordercancel);//既在比酷又在本地还库存的
			}
			
			//调用取消本地库存
			for(String key : orderBkIds.keySet()){//2
				if(!orderBkIds.get(key).toString().equals("0")){//订单比酷id
					handlerspecialSelling((List<CartGoods>) orderProductBean.get(key), "minus");
					updateUsed_storeByOrder_id(key.toString(), "0");
				}
			}
			//只对本地库存释放的商品
			if(productIdandNums.size()>0 && null != productIdandNums){
				this.handlerspecialSelling( productIdandNums , "minus");
				for(CartGoods productIdandNum : productIdandNums){
					if(StringUtil.isNotEmpty(productIdandNum.getOrder_id())){
						this.updateUsed_storeByOrder_id(productIdandNum.getOrder_id(),"0");
					}
				}
			}
		}
		if(flag.equals("1")||"3".equals(flag)){
			int result = orderDao.cancelOrder(order_id, member_id);//取消订单
			if(result>0){//取消成功
				log.info("取消订单，保存订单日志");
				orderDao.saveCancelOrderLog(order_id, member_id,reason);//保存订单日志
			}
		}
	}

	@Override
	public boolean getAlipayStatus(String order_rel) throws Exception {
		return orderDao.getAlipayStatus( order_rel);
	}
}
