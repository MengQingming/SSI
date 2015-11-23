package com.hm.hxl.order.order.service.impl;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hm.hxl.order.order.dao.ICartDao;
import com.hm.hxl.order.order.dao.ICouponsDao;
import com.hm.hxl.order.order.model.AddCartParameter;
import com.hm.hxl.order.order.model.Address;
import com.hm.hxl.order.order.model.Cart;
import com.hm.hxl.order.order.model.CartGoods;
import com.hm.hxl.order.order.model.CartParameter;
import com.hm.hxl.order.order.model.ComparatorCartGoods;
import com.hm.hxl.order.order.model.ComparatorCouponsNew;
import com.hm.hxl.order.order.model.Coupons;
import com.hm.hxl.order.order.model.CouponsGoods;
import com.hm.hxl.order.order.model.CouponsNew;
import com.hm.hxl.order.order.model.CouponsRequest;
import com.hm.hxl.order.order.model.CouponsType;
import com.hm.hxl.order.order.model.EditCartParameter;
import com.hm.hxl.order.order.model.GoodsStockByIdParameters;
import com.hm.hxl.order.order.model.MemberInfo;
import com.hm.hxl.order.order.model.ProductPara;
import com.hm.hxl.order.order.model.PromptMessage;
import com.hm.hxl.order.order.model.ResultObject;
import com.hm.hxl.order.order.service.ICartService;
import com.hm.hxl.order.order.service.IOrderService;
import com.hm.hxl.order.order.service.LogisticsService;
import com.hm.hxl.order.util.Checksum;
import com.hm.hxl.order.util.HttpRequest;
import com.hm.hxl.order.util.Md5;
import com.hm.hxl.order.util.SystemConfig;

/**
 * 类名: CartServiceImpl 
 * 作用: 购物车service实现
 * 作者: yanpengjie 
 * 日期: 2015-7-17 下午12:55:12 
 * 版本: V 1.0
 *
 */
@SuppressWarnings({"rawtypes","unchecked"})
@Service
public class CartServiceImpl implements ICartService {

	/**日志**/
	private static Logger log = Logger.getLogger(CartServiceImpl.class);
	/**购物车dao**/
	@Autowired
	private ICartDao cartDaoImpl;
	
	@Autowired
	private ICouponsDao couponsDaoImpl;
	@Autowired
	private IOrderService orderServiceImpl;
	@Autowired
	private LogisticsService logisticsService;

	@Override
	public List<CartGoods> getCartGoodsByMemberId(Cart cart) throws Exception {
		return cartDaoImpl.getCartGoodsByMemberId(cart);
		
	}
	
	@Override
	public List<CartGoods> getCartGoodsByProductId(Cart cart) throws Exception {
		return cartDaoImpl.getCartGoodsByProductId(cart);
		
	}
	
	@Override
	public List<CartGoods> getCartGoods(Cart cart) throws Exception {
		return cartDaoImpl.getCartGoods(cart);
	}

	@Override
	public int getCartNumbers(Cart cart) throws Exception {
		return cartDaoImpl.getCartNumbers(cart);
		
	}
	
	@Override
	public boolean isCart(AddCartParameter ap) throws Exception {
		Map map = new HashMap();
		map.put("memberId",ap.getMember_id());
		map.put("obj_ident", "goods_"+ap.getGoods_id()+"_"+ap.getProduct_id());
		return cartDaoImpl.isCart(map);
	}

	
	@Override
	public synchronized void saveOrUpdateCart(AddCartParameter ap) throws Exception {
		Map map = new HashMap();
		map.put("memberId",ap.getMember_id());
		map.put("member_ident", Md5.encipher(ap.getMember_id()));
		map.put("time", System.currentTimeMillis()/1000);
		
		String[] good = ap.getGoods_id().split(",");
		String[] pro = ap.getProduct_id().split(",");
		String[] quantity = ap.getQuantity().split(",");
		for(int i=0;i<good.length;i++){
			map.put("obj_ident", "goods_"+good[i].trim()+"_"+pro[i].trim());
			map.put("quantity", quantity[i].trim());
			String json = "a:4:{s:8:\"goods_id\";s:"+good[i].trim().length()+":\""+good[i].trim()
			+"\";s:10:\"product_id\";s:"+pro[i].trim().length()
			+":\""+pro[i].trim()+"\";s:7:\"adjunct\";a:0:{}s:14:\"extends_params\";N;}";
			map.put("params", json);
			boolean bol = cartDaoImpl.isCart(map);
			if(bol){
				cartDaoImpl.updateCart(map);
			}else{
				cartDaoImpl.addCart(map);
			}
		}
		
	}
	
	
	@Override
	public void addCart(AddCartParameter ap) throws Exception {
		Map map = new HashMap();
		map.put("goodsId", ap.getGoods_id());
		map.put("productId",ap.getProduct_id());
		map.put("memberId",ap.getMember_id());
		map.put("obj_ident", "goods_"+ap.getGoods_id()+"_"+ap.getProduct_id());
		map.put("member_ident", Md5.encipher(ap.getMember_id()));
		map.put("quantity", ap.getQuantity());
		map.put("time", System.currentTimeMillis()/1000);
		String json = "a:4:{s:8:\"goods_id\";s:"+ap.getGoods_id().length()+":\""+ap.getGoods_id()
			+"\";s:10:\"product_id\";s:"+ap.getProduct_id().length()
			+":\""+ap.getProduct_id()+"\";s:7:\"adjunct\";a:0:{}s:14:\"extends_params\";N;}";
		map.put("params", json);
		cartDaoImpl.addCart(map);
	}

	@Override
	public void updateCart(AddCartParameter ap) throws Exception {
		Map map = new HashMap();
		map.put("memberId",ap.getMember_id());
		map.put("obj_ident", "goods_"+ap.getGoods_id()+"_"+ap.getProduct_id());
		map.put("quantity", ap.getQuantity());
		map.put("time", System.currentTimeMillis()/1000);
		cartDaoImpl.updateCart(map);
	}

	@Override
	public synchronized void deleteCart(EditCartParameter ap) throws Exception {
		Map map = new HashMap();
		map.put("memberId",ap.getMember_id());
		String[] pro = ap.getProduct_id().split(",");
		String[] good = ap.getGoods_id().split(",");
		String[] obj_ident = new String[good.length];
		for(int i=0;i<good.length;i++){
			obj_ident[i]="goods_"+good[i].trim()+"_"+pro[i].trim();
		}
		map.put("obj_ident", obj_ident);
		cartDaoImpl.deleteCart(map);
	}

	@Override
	public synchronized void editCart(EditCartParameter ap) throws Exception {
		Map map = new HashMap();
		map.put("memberId",ap.getMember_id());
		map.put("time", System.currentTimeMillis()/1000);
		String[] pro = ap.getProduct_id().split(",");
		String[] good = ap.getGoods_id().split(",");
		String[] quantity = ap.getQuantity().split(",");
		for(int i=0;i<good.length;i++){
			map.put("obj_ident", "goods_"+good[i].trim()+"_"+pro[i].trim());
			map.put("quantity", quantity[i].trim());
			cartDaoImpl.editCart(map);
		}
	}

	@Override
	public Address getDefaultAddrs(Cart ap) throws Exception {
		return cartDaoImpl.getDefaultAddrs(ap);
	}

	@Override
	public List<Coupons> getCoupons(Checksum checksum,Cart ap) throws Exception {
		try{
			String hxl_type = checksum.getHxl_type();
			String webService = new SystemConfig("clientService").getProperty(hxl_type);
			String json = HttpRequest.sendGet(new SystemConfig(webService).getProperty("get_coupons_url"), "member_id="+ap.getMember_id());
			JSONObject returnJson = JSONObject.fromObject(json);
			log.info("CartController getCoupons=====>优惠券信息:"+json);
			if("1".equals(returnJson.getString("code"))){
				if(returnJson.get("data")==null||"".equals(returnJson.get("data"))){
					return null;
				}
				JSONObject objectss = JSONObject.fromObject(returnJson.getString("data"));
				JSONArray object = JSONArray.fromObject(objectss.getString("cpns_list"));
				String memc_code="";
				for(int i=0;i<object.size();i++){
					JSONObject o = object.getJSONObject(i);
					//if(o.getString("cpns_used").equals("0")){
					memc_code +=o.getString("memc_code")+",";
					//}
				}
				if(StringUtils.isNotBlank(memc_code)){
					memc_code = memc_code.substring(0,memc_code.length()-1);
				}
				List<Coupons> list = couponsDaoImpl.getCouponsList(ap.getMember_id(),99999999,1);
				List<Coupons> couponsList = new ArrayList<Coupons>();
				String[] memc_codes = memc_code.split(",");
				for(Coupons coupons :list){
					SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
					Date fromtime = format.parse(coupons.getFrom_time());
					Date totime = format.parse(coupons.getTo_time());
					SimpleDateFormat format2 = new SimpleDateFormat("yyyy年MM月dd日");
					coupons.setFrom_time(format2.format(fromtime));
					coupons.setTo_time(format2.format(totime));
					if(StringUtils.isNotBlank(coupons.getCpns_name())){
						String[] str = coupons.getCpns_name().split("\\|");
						if(str.length==3){
							coupons.setCpns_name(str[0].trim());
							if(str[1].trim().contains("折")){
								coupons.setCpns_price(str[1].trim());
							}else{
								coupons.setCpns_price("¥"+str[1].trim());
							}
							coupons.setCpns_use_rule(str[2].trim());
						}
					}
					for(int i=0;i<memc_codes.length;i++){
						if(coupons.getMemc_code().equals(memc_codes[i].trim())){
							couponsList.add(coupons);
							break;
						}
					}
				}
				return couponsList;
			}else{
				return null;
			}
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public synchronized void updateCartIsSelect(Cart ap) throws Exception {
		cartDaoImpl.updateCartIsSelect(ap);
		cartDaoImpl.updateCartIsSelectTrue(ap);
	}

	@Override
	public void deleteCartCoupons(Map map) throws Exception {
		cartDaoImpl.deleteCartCoupons(map);
	}

	@Override
	public String getCartCouponsByMemberId(String member_id) throws Exception {
		return cartDaoImpl.getCartCouponsByMemberId(member_id);
	}

	@Override
	public List<String> getAreaTypeByfrom_stos(List<String> from_stos) throws Exception {
		if(from_stos!=null && from_stos.size()>0){
			return cartDaoImpl.getAreaTypeByfrom_stos(from_stos);
		}
		return null;
	}

	@Override
	public PromptMessage getPromptMessageByAreaType(String areaType)throws Exception  {
		return cartDaoImpl.getPromptMessageByAreaType(areaType);
	}

	@Override
	public List<String> getNotOverseasAreaID() throws Exception {
		return cartDaoImpl.getNotOverseasAreaID();
	}

	@Override
	public MemberInfo getMemberInfoByMemberId(String member_id) throws Exception{
		return cartDaoImpl.getMemberInfoByMemberId(member_id);
	}

	@Override
	public List<CartGoods> getHappyCartGoods(Cart cart) throws Exception {
		return cartDaoImpl.getHappyCartGoods(cart);
	}
	
	@SuppressWarnings("unused")
	@Override
	public ResultObject verificationCashCoupons(String memc_code,List<CartGoods> cartGoodss)
			throws Exception {
		log.info("CartServiceImpl-----UsedCoupons--->开始调用优惠券验证程序");
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
		
		List<CouponsNew> couponsNews = orderServiceImpl.getCouponsNewByMemc_code(memc_code);
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
		/**验证优惠券时间**/
		String startTimeError = "";
		String endTimeError = "";
		a:for (CouponsNew couponsNew : couponsNews) {
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
		
		String type_used = "";
		Set<String> cpns_types = new HashSet<String>();
		for (CouponsNew couponsNew : couponsNews) {
			String cpns_type = couponsNew.getCpns_type();
			type_used = cpns_type;
			cpns_types.add(cpns_type);
		}
		/**验证优惠券代金券与满减券是否同时使用**/
		if(cpns_types.size()>1){
			log.error("CartServiceImpl-----UsedCoupons--->订单中代金券和满减券不能同时使用");
			return new ResultObject("-1", "订单中代金券和满减券不能同时使用" ,null);
		}
		/**验证优惠券总使用张数**/
		CouponsType couponsType = couponsDaoImpl.getCouponsTypeByType_used(type_used);
		int order_limit = Integer.parseInt(couponsType.getOrder_limit());
		int goods_limit = Integer.parseInt(couponsType.getGoods_limit());
		if(cartGoodss.size()==1){
			if(nums > goods_limit){
				log.error("CartServiceImpl-----UsedCoupons--->订单最多只能使用["+goods_limit+"]张优惠券");
				return new ResultObject("-1", "此类型优惠券在订单最多只能使用["+goods_limit+"]张" ,null);
			}
		}
		if(nums > order_limit){
			log.error("CartServiceImpl-----UsedCoupons--->订单最多只能使用["+order_limit+"]张优惠券");
			return new ResultObject("-1", "此类型优惠券在订单最多只能使用["+order_limit+"]张" ,null);
		}
		/**验证代金券是否可以使用在该订单中**/
		int size = cartGoodss.size();
		Map<CouponsNew, List<CartGoods>> couponsNewMap = new HashMap<CouponsNew, List<CartGoods>>();
		
		a:for (CouponsNew couponsNew : couponsNews) {
			List<CouponsGoods> couponsGoodss = couponsNew.getCouponsGoodss();
			List<String> product_ids2 = new ArrayList<String>();
			for (CouponsGoods couponsGoods : couponsGoodss) {
				String product_id = couponsGoods.getProduct_id();
				product_ids2.add(product_id);
			}
			int index = 0;
			for (CartGoods cartGoods : cartGoodss) {
				String cpns_goods_type = couponsNew.getCpns_goods_type();
				if("3".equals(cpns_goods_type)){
					/**3代表所有商品都可使用**/
					if(couponsNewMap.get(couponsNew)==null){
						List<CartGoods> cgs = new ArrayList<CartGoods>();
						cgs.add(cartGoods);
						couponsNewMap.put(couponsNew,cgs);
					}else{
						List<CartGoods> cgs = couponsNewMap.get(couponsNew);
						cgs.add(cartGoods);
						couponsNewMap.put(couponsNew,cgs);
					}
				}else{
					String product_id = cartGoods.getProduct_id();
					if(!product_ids2.contains(product_id)){
						index++;
					}else{
						/**可使用的话,插入某一优惠券可以使用在那些商品上**/
						if(couponsNewMap.get(couponsNew)==null){
							List<CartGoods> cgs = new ArrayList<CartGoods>();
							cgs.add(cartGoods);
							couponsNewMap.put(couponsNew,cgs);
						}else{
							List<CartGoods> cgs = couponsNewMap.get(couponsNew);
							cgs.add(cartGoods);
							couponsNewMap.put(couponsNew,cgs);
						}
					}
				}
			}
			if(index==size){
				String code = couponsNew.getMemc_code();
				log.error("CartServiceImpl-----UsedCoupons--->优惠券["+code+"]不可以使用在该订单中!");
				return new ResultObject("-1","抱歉，此优惠券只能购买指定的商品",null);
			}
		}
		/**优惠券由大到小排序根据价格 ,商品也同样根据价格由大到小排序**/
		ComparatorCouponsNew ccn = new ComparatorCouponsNew();
		Collections.sort(couponsNews, ccn);
		Set<CouponsNew> keySet = couponsNewMap.keySet();
		for (CouponsNew couponsNew : keySet) {
			List<CartGoods> list = couponsNewMap.get(couponsNew);
			ComparatorCartGoods ccg = new ComparatorCartGoods();
			Collections.sort(list,ccg);
			System.out.println();
		}
		
		a:for (CouponsNew couponsNew : couponsNews) {
			List<CartGoods> list = couponsNewMap.get(couponsNew);
			double cpns_price = Double.parseDouble(couponsNew.getCpns_price());
			for (CartGoods cartGoods : list) {
				double quantity = Double.parseDouble(cartGoods.getQuantity());
				double price = Double.parseDouble(cartGoods.getPrice());
				double total_price = quantity*price;
				if("3".equals(cartGoods.getArea_type())){
					/**海外商品判断单价而不是小计**/
					total_price = price;
				}
				List<CouponsNew> cns = cartGoods.getCouponsNews();
				double cps = 0.0;
				for (CouponsNew cn : cns) {
					double cp = Double.parseDouble(cn.getCpns_price());
					cps+=cp;
				}
				/**判断如果使用了当前优惠券是否会长度过长或价格超标**/
				if((cpns_price+cps) <= total_price){
					if((cns.size()+1)<=goods_limit){
						if("2".equals(couponsNew.getCpns_type())){
							double cpns_limit_price = Double.parseDouble(couponsNew.getCpns_limit_price());
							if(total_price >= cpns_limit_price){
								cns.add(couponsNew);
								/**如果可以使用当前优惠券则在商品上设置使用了该优惠券**/
								cartGoods.setCouponsNews(cns);
								break;
							}
						}else{
							cns.add(couponsNew);
							cartGoods.setCouponsNews(cns);
							break;
						}
					}
				}
			}
		}
		/**取出使用过的优惠券,再从总使用优惠券中去除已使用过的
		 * 剩余的则是长度过长或价格超标的
		 * 判断货物已使用的优惠券是否满足最大长度,若满足则是长度过长,不满足则是价格超标
		 * **/
		List<CartGoods> cgs = new ArrayList<CartGoods>();
		Set<CouponsNew> keySet2 = couponsNewMap.keySet();
		for (CouponsNew couponsNew : keySet2) {
			List<CartGoods> list = couponsNewMap.get(couponsNew);
			for (CartGoods cartGoods : list) {
				List<CouponsNew> cns = cartGoods.getCouponsNews();
				if(cns.size()>0){
					if(!cgs.contains(cartGoods)){
						cgs.add(cartGoods);
					}
				}
			}
		}
		List<CouponsNew> cns = new ArrayList<CouponsNew>();
		for (CartGoods cg : cgs) {
			List<CouponsNew> list = cg.getCouponsNews();
			for (CouponsNew couponsNew : list) {
				cns.add(couponsNew);
			}
		}
		List<CouponsNew> cns2 = new ArrayList<CouponsNew>();
		a:for (CouponsNew couponsNew : couponsNews) {
			if(!cns.contains(couponsNew)){
				cns2.add(couponsNew);
			}
		}
		
		for (CouponsNew couponsNew : cns2) {
			List<CartGoods> list = couponsNewMap.get(couponsNew);
			for (CartGoods cartGoods : list) {
				List<CouponsNew> couponsNews2 = cartGoods.getCouponsNews();
				if(couponsNews2.size()==goods_limit){
					log.error("CartServiceImpl-----UsedCoupons--->"+cartGoods.getName()+"最多只能使用["+goods_limit+"]张优惠券");
					return new ResultObject("-1","订单中针对单品使用优惠券张数过多",null);
				}else{
					if("2".equals(couponsNew.getCpns_type())){
						log.error("CartServiceImpl-----UsedCoupons--->"+cartGoods.getName()+"所使用优惠券["+couponsNew.getMemc_code()+"]商品未达到满减要求");
						return new ResultObject("-1","优惠券未达到满减要求",null);
					}else{
						log.error("CartServiceImpl-----UsedCoupons--->"+cartGoods.getName()+"所使用优惠券累积额度过高,不能再使用["+couponsNew.getMemc_code()+"]");
						return new ResultObject("-1","订单中针对单品使用优惠券额度过高",null);
					}
				}
			}
		}
		/**加入其它未使用优惠券的商品**/
		Map<String,CartGoods> cgm = new HashMap<String,CartGoods>();
		for (CartGoods cg : cgs) {
			cgm.put(cg.getProduct_id(), cg);
		}
		for (CartGoods cg : cartGoodss) {
			if(!cgm.containsKey(cg.getProduct_id())){
				cgm.put(cg.getProduct_id(), cg);
			}
		}
		cgs = new ArrayList<CartGoods>();
		Set<String> cgmkeySet = cgm.keySet();
		for (String product_id : cgmkeySet) {
			CartGoods cartGoods = cgm.get(product_id);
			cgs.add(cartGoods);
		}
		/**处理优惠券具体信息**/
		BigDecimal pmt_order = new BigDecimal(0.0);
		List<CouponsNew> couponsNewsr1 = new ArrayList<CouponsNew>();
		for (CartGoods cartGoods : cgs) {
			List<CouponsNew> cnsr = cartGoods.getCouponsNews();
			for (CouponsNew couponsNew : cnsr) {
				couponsNew.setUse_flag("1");
				couponsNew.setUse_falg_desc("可使用");
				couponsNewsr1.add(couponsNew);
				pmt_order = pmt_order.add(new BigDecimal(couponsNew.getCpns_price()));
			}
		}
		Map <String, BigDecimal> pmt = new HashMap <String, BigDecimal> ();
		pmt.put("pmt_order",pmt_order);
		Map amount = orderServiceImpl.getOrderCTPCP(cgs,pmt);
		List<CouponsRequest> Coupons = new ArrayList<CouponsRequest>();
		for (CouponsNew couponsNew : couponsNewsr1) {
			CouponsRequest cr = new CouponsRequest();
			BeanUtils.copyProperties(cr, couponsNew);
			if("1".equals(cr.getCpns_type())){
				String cpns_price = cr.getCpns_price();
				cr.setCpns_type_hz(cpns_price+"元");
			}else if("2".equals(cr.getCpns_type())){
				String cpns_limit_price = cr.getCpns_limit_price();
				cr.setCpns_type_hz("满"+cpns_limit_price+"元使用");
			}
			String from_time = formatTime(cr.getFrom_time());
			String to_time = formatTime(cr.getTo_time());
			cr.setFrom_time(from_time);
			cr.setTo_time(to_time);
			Coupons.add(cr);
		}
		log.info("优惠券使用个数------------>"+Coupons.size());
		for (CouponsRequest couponsRequest : Coupons) {
			log.info("优惠券详细信息---------->:"+couponsRequest.toString());
		}
		Set keySetw = amount.keySet();
		for (Object key : keySetw) {
			Object value = amount.get(key);
			String str = value.toString();
			amount.put(key, str);
		}
		amount.put("coupons", Coupons);
		amount.put("products", cgs);
		long endTime = System.currentTimeMillis();
		log.info("CartServiceImpl-----UsedCoupons--->优惠券验证接口调用完毕,用时:"+(endTime-startTime)+"毫秒");
		return new ResultObject("1","success",amount);
	}
	
	private String formatTime(String time) {
		long parseLong = Long.parseLong(time)*1000;
		Date date = new Date(parseLong);
		DateFormat mFormat = new SimpleDateFormat("yyyy年MM月dd日");
		String format = mFormat.format(date);
		return format;
	}
	
	/**
	 * 方法名: productStatus
	 * <br/> 作用: 判断商品是否下架、有货、无货
	 * <br/> @param list
	 * <br/> @param cp
	 * <br/> @param checkSum
	 * <br/> @return
	 * <br/> @throws Exception 
	 * <br/> 返回值: List<CartGoods>
	 * <br/> @Throws
	 */
	@Override
	public List<CartGoods> productStatus(List<CartGoods> list,CartParameter cp,Checksum checkSum)throws Exception{
		Map<String,List<CartGoods>> cartGoodsListmap = new HashMap<String, List<CartGoods>>();
		List<CartGoods> reAllCartGoods = new ArrayList<CartGoods>();
		List<String> areaIDs = getNotOverseasAreaID();
		for (String areaID : areaIDs) {
			cartGoodsListmap.put(areaID, null);
		}
		if(list!=null && list.size()>0){
			for (CartGoods cartGoods : list) {
				if(!"3".equals(cartGoods.getArea_type())){
					if(StringUtils.isBlank(cartGoods.getBiku_id())){
						log.error("CartController---productStatus---->>查询比酷货物状态比酷id为空,该商品productID为---"+cartGoods.getProduct_id());
						cartGoods.setStatus("2");
						cartGoods.setStatusDesc( "无效");
						reAllCartGoods.add(cartGoods);
						continue;
					}
				}
				
				if("false".equals(cartGoods.getDisabled()) && "true".equals(cartGoods.getMarketable())){
					if("true".equals(cartGoods.getYwhflag())){
						//判断预售
						if(!"2".equals(cartGoods.getGoodsflag())){
							//判断是不是特卖
							if("1".equals(cartGoods.getGoodsflag())){
								//判断数量是否大于了库存剩余总量	  n<=(总量-已占)
								int surplus_limitbuy_quantity = Integer.parseInt(cartGoods.getLimitbuy_quantity())-Integer.parseInt(cartGoods.getLimitbuy_us_quantity());
								int quantity = Integer.parseInt(cartGoods.getQuantity());
								if(quantity <= surplus_limitbuy_quantity){
									//判断数量是否大于了每人限购   n<=每人限购量 
									int limitbuy_sg_quantity = Integer.parseInt(cartGoods.getLimitbuy_sg_quantity());
									if( quantity <= limitbuy_sg_quantity ){
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
												cartGoods.setStatus("2");
												cartGoods.setStatusDesc( "无效");
												reAllCartGoods.add(cartGoods);
											}
										}else{
											cartGoods.setStatus("1");
											cartGoods.setStatusDesc( "有货");
											reAllCartGoods.add(cartGoods);
										}
									}else {
										cartGoods.setStatus("3");
										cartGoods.setStatusDesc( "库存不足");
										reAllCartGoods.add(cartGoods);
									}
								}else {
									cartGoods.setStatus("3");
									cartGoods.setStatusDesc( "库存不足");
									reAllCartGoods.add(cartGoods);
								}
							} else {
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
										cartGoods.setStatus("2");
										cartGoods.setStatusDesc( "无效");
										reAllCartGoods.add(cartGoods);
									}
								}else{
									cartGoods.setStatus("1");
									cartGoods.setStatusDesc( "有货");
									reAllCartGoods.add(cartGoods);
								}
							}
						}else {
							cartGoods.setStatus("2");
							cartGoods.setStatusDesc( "无效");
							reAllCartGoods.add(cartGoods);
						}
					}else{
						cartGoods.setStatus("2");
						cartGoods.setStatusDesc( "无效");
						reAllCartGoods.add(cartGoods);
					}
				}else{
					cartGoods.setStatus("2");
					cartGoods.setStatusDesc( "无效");
					reAllCartGoods.add(cartGoods);
				}
			}
		}
		Set<String> keySet = cartGoodsListmap.keySet();
		for (String from_sto : keySet) {
			List<CartGoods> cartGoodss = cartGoodsListmap.get(from_sto);
			if (cartGoodss!=null) {
				String bk_id = "";
				for (CartGoods cartGoods : cartGoodss) {
					bk_id += cartGoods.getBiku_id()+",";
				}
				bk_id = bk_id.substring(0, bk_id.length()-1);
				List<CartGoods> rCartGoods = getGoodsStock( bk_id, cp, cartGoodss, checkSum);
				if(rCartGoods!=null && rCartGoods.size()>0){
					for (CartGoods cartGoods : rCartGoods) {
						reAllCartGoods.add(cartGoods);
					}
				}
			}
		}
		return reAllCartGoods;
	}
	
//	public List<CartGoods> productStatus(List<CartGoods> list,CartParameter cp,Checksum checkSum)throws Exception{
//		Map<String,List<CartGoods>> cartGoodsListmap = new HashMap<String, List<CartGoods>>();
//		List<CartGoods> reAllCartGoods = new ArrayList<CartGoods>();
//		List<String> areaIDs = cartService.getNotOverseasAreaID();
//		for (String areaID : areaIDs) {
//			cartGoodsListmap.put(areaID, null);
//		}
//		if(list!=null && list.size()>0){
//			for (CartGoods cartGoods : list) {
//				if(!"3".equals(cartGoods.getArea_type())){
//					String from_sto = cartGoods.getFrom_sto();
//					if(cartGoodsListmap.containsKey(from_sto)){
//						List<CartGoods> NotOverseasList = null;
//						if(cartGoodsListmap.get(from_sto)==null){
//							NotOverseasList = new ArrayList<CartGoods>();
//							NotOverseasList.add(cartGoods);
//							cartGoodsListmap.put(from_sto, NotOverseasList);
//						}else {
//							NotOverseasList = cartGoodsListmap.get(from_sto);
//							NotOverseasList.add(cartGoods);
//							cartGoodsListmap.put(from_sto, NotOverseasList);
//						}
//					}else {
//						cartGoods.setStatus("2");
//						cartGoods.setStatusDesc( "无效");
//					}
//				}else{
//					cartGoods.setStatus("1");
//					cartGoods.setStatusDesc( "有货");
//				}
//			}
//		}
//		
//		Set<String> keySet = cartGoodsListmap.keySet();
//		for (String from_sto : keySet) {
//			List<CartGoods> cartGoodss = cartGoodsListmap.get(from_sto);
//			if (cartGoodss!=null) {
//				String bk_id = "";
//				for (CartGoods cartGoods : cartGoodss) {
//					bk_id += cartGoods.getBiku_id()+",";
//				}
//				bk_id = bk_id.substring(0, bk_id.length()-1);
//				List<CartGoods> rCartGoods = getGoodsStock( bk_id, cp, cartGoodss, checkSum);
//				if(rCartGoods!=null && rCartGoods.size()>0){
//					Map<String, CartGoods> cm = new HashMap<String, CartGoods>();
//					for (CartGoods cartGoodsx : rCartGoods) {
//						cm.put(cartGoodsx.getProduct_id(), cartGoodsx);
//					}
//					for (CartGoods cartGoodsf : list) {
//						String product_id = cartGoodsf.getProduct_id();
//						if(cm.containsKey(product_id)){
//							CartGoods cartGoods = cm.get(product_id);
//							cartGoodsf.setStatus(cartGoods.getStatus());
//							cartGoodsf.setStatusDesc(cartGoods.getStatusDesc());
//						}
//					}
//				}
//			}
//		}
//		
//		if(list!=null && list.size()>0){
//			for (CartGoods cartGoods : list) {
//				if (cartGoods.getStatus()==null || "1".equals(cartGoods.getStatus())) {
//					if("false".equals(cartGoods.getDisabled()) && "true".equals(cartGoods.getMarketable())){
//						if("true".equals(cartGoods.getYwhflag())){
//							//判断预售
//							if(!"2".equals(cartGoods.getGoodsflag())){
//								//判断是不是特卖
//								if("1".equals(cartGoods.getGoodsflag())){
//									//判断数量是否大于了库存剩余总量	  n<=(总量-已占)
//									int surplus_limitbuy_quantity = Integer.parseInt(cartGoods.getLimitbuy_quantity())-Integer.parseInt(cartGoods.getLimitbuy_us_quantity());
//									int quantity = Integer.parseInt(cartGoods.getQuantity());
//									if(quantity <= surplus_limitbuy_quantity){
//										//判断数量是否大于了每人限购   n<=每人限购量 
//										int limitbuy_sg_quantity = Integer.parseInt(cartGoods.getLimitbuy_sg_quantity());
//										if( quantity > limitbuy_sg_quantity ){
//											cartGoods.setStatus("3");
//											cartGoods.setStatusDesc( "库存不足");
//										}
//									}else {
//										cartGoods.setStatus("3");
//										cartGoods.setStatusDesc( "库存不足");
//									}
//								}
//							}else {
//								cartGoods.setStatus("2");
//								cartGoods.setStatusDesc( "无效");
//							}
//						}else{
//							cartGoods.setStatus("2");
//							cartGoods.setStatusDesc( "无效");
//						}
//					}else{
//						cartGoods.setStatus("2");
//						cartGoods.setStatusDesc( "无效");
//					}
//				}
//			}
//		}
//		return list;
//	}
	
	/**
	 * 方法名: getGoodsStock
	 * <br/>作用: 根据仓库获取库存信息
	 * <br/>参数:@param bk_id
	 * <br/>参数:@param cp
	 * <br/>参数:@param cartGoodss
	 * <br/>参数:@param checkSum 
	 * <br/>返回值: void
	 * <br/>@Throws 
	 * @throws Exception 
	 */ 
	@SuppressWarnings("unused")
	@Override
	public List<CartGoods> getGoodsStock(String bk_id, CartParameter cp, List<CartGoods> cartGoodss, Checksum checkSum) throws Exception {
		List<CartGoods> returnCartGoodss = null;
		log.info("getGoodsStock------bk_id--->"+bk_id);
		returnCartGoodss = new ArrayList<CartGoods>();
		GoodsStockByIdParameters param = new GoodsStockByIdParameters();
		param.setProduct_id(bk_id);
		param.setPvc_id(cp.getPvc_id());
		param.setCountry_id(cp.getCountry_id());
		param.setLocal_id(cp.getLocal_id());
		String json = logisticsService.queryGoodsStockById(checkSum, param);//查询库存
		log.info("getGoodsStock------json--->"+json);
		JSONObject returnJson = JSONObject.fromObject(json);
		if(returnJson.getInt("flag")==0){
			String error = returnJson.toString();
			log.error("CartController getGoodsStock=====>比酷返回错误信息：" + error);
			for(CartGoods cartGoods :cartGoodss){
				cartGoods.setStatus("2");
				cartGoods.setStatusDesc( "无效");
				returnCartGoodss.add(cartGoods);
			}
		}else{
			JSONObject msgJson = JSONObject.fromObject(returnJson.getString("msg"));
			JSONObject prodstock =JSONObject.fromObject(msgJson.getString("prodstock"));
			
			String[] ids = bk_id.split(",");
			for(int i=0;i<ids.length;i++){
				log.info("getGoodsStock------ids --->"+ids[i].trim());
				CartGoods cartGoods  = cartGoodss.get(i);
				JSONObject o =JSONObject.fromObject(prodstock.get(ids[i].trim()));
				int pro_id = o.getInt("prod_id");//产品id
				int order_ok = o.getInt("order_ok");//接单属性 0：无限制，1、仓库接单，2、预采购量接单
				int amt = o.getInt("amt");//可用库存量
				int GOOD_QTY = o.getInt("GOOD_QTY");//库存数量
				int TEL_QTY = o.getInt("TEL_QTY");//订单压单数量
				int TRAC_OUT_QTY = o.getInt("TRAC_OUT_QTY");//仓库压单数量
				int NOTICE_QTY = o.getInt("NOTICE_QTY");//通知单压单数量
				int PO_SCHD_QTY = o.getInt("PO_SCHD_QTY");//采购在途数量
				if(order_ok==0 ){//一直有货
					cartGoods.setStatus("1");
					cartGoods.setStatusDesc( "有货");
					returnCartGoodss.add(cartGoods);
					continue;
				} 
				if(amt>0){//可用库存量
					if(amt-Integer.parseInt(cartGoods.getQuantity())>=0){
						cartGoods.setStatus("1");
						cartGoods.setStatusDesc( "有货");
						returnCartGoodss.add(cartGoods);
						continue;
					}else{
						cartGoods.setStatus("2");
						cartGoods.setStatusDesc( "无效");//库存不足
						returnCartGoodss.add(cartGoods);
						continue;
					}
				}else if(amt==0){
//					if(PO_SCHD_QTY-NOTICE_QTY> 0){
//						if(PO_SCHD_QTY-NOTICE_QTY>=Integer.parseInt(cartGoods.getQuantity())){
//							cartGoods.setStatus("1");
//							cartGoods.setStatusDesc( "有货");
//							returnCartGoodss.add(cartGoods);
//							continue;
//						}else{
							cartGoods.setStatus("2");
							cartGoods.setStatusDesc( "无效");//库存不足
							returnCartGoodss.add(cartGoods);
							continue;
//						}
//					}else{
//						cartGoods.setStatus("2");
//						cartGoods.setStatusDesc( "无效");
//						returnCartGoodss.add(cartGoods);
//						continue;
//					}
				}else{
					cartGoods.setStatus("2");
					cartGoods.setStatusDesc( "无效");
					returnCartGoodss.add(cartGoods);
					continue;
				}
			}
		}
		return returnCartGoodss;
	}
	
	/**
	 * 
	 * <pre>
	 * 方法名: productStatus
	 * 作用: 单个商品验证库存
	 * @param goods
	 * @param para
	 * @param checkSum
	 * @return
	 * @throws Exception 
	 * 返回值: CartGoods
	 * @Throws 
	 * </pre>
	 */
	@Override
	public CartGoods productStatus( CartGoods goods ,ProductPara para,Checksum checkSum)throws Exception{
		if(!"3".equals(goods.getArea_type())){
			if(StringUtils.isBlank(goods.getBiku_id())){
				log.error("CartController---productStatus---->>查询比酷货物状态比酷id为空,该商品productID为---"+goods.getProduct_id());
				goods.setStatus("2");
				goods.setStatusDesc("无效");
				return goods;
			}
		}
		if("false".equals(goods.getDisabled()) && "true".equals(goods.getMarketable())){
			if("true".equals(goods.getYwhflag())){
				//判断预售
				if(!"2".equals(goods.getGoodsflag())){
					//判断是不是特卖
					if("1".equals(goods.getGoodsflag())){
						//判断数量是否大于了库存剩余总量	  n<=(总量-已占)
						int surplus_limitbuy_quantity = Integer.parseInt(goods.getLimitbuy_quantity())-Integer.parseInt(goods.getLimitbuy_us_quantity());
						int quantity = Integer.parseInt(goods.getQuantity());
						if(quantity <= surplus_limitbuy_quantity){
							//判断数量是否大于了每人限购   n<=每人限购量 
							int limitbuy_sg_quantity = Integer.parseInt(goods.getLimitbuy_sg_quantity());
							if( quantity <= limitbuy_sg_quantity ){
								if("3".equals(goods.getArea_type())){
									goods.setStatus("1");
									goods.setStatusDesc( "有货");
									return goods;
								}
							}else {
								goods.setStatus("3");
								goods.setStatusDesc( "库存不足");
								return goods;
							}
						}else {
							goods.setStatus("3");
							goods.setStatusDesc( "库存不足");
							return goods;
						}
					} else {
						if("3".equals(goods.getArea_type())){
							goods.setStatus("1");
							goods.setStatusDesc( "有货");
							return goods;
						}
					}
				}else {
					goods.setStatus("2");
					goods.setStatusDesc( "无效");
					return goods;
				}
			}else{
				goods.setStatus("2");
				goods.setStatusDesc( "无效");
				return goods;
			}
		}else{
			goods.setStatus("2");
			goods.setStatusDesc( "无效");
			return goods;
		}
		String biku_id = goods.getBiku_id();
		String goodsStock = getGoodsStock(biku_id, para, checkSum);
		if("1".equals(goodsStock)){
			goods.setStatus("1");
			goods.setStatusDesc( "有货");
			return goods;
		}else{
			goods.setStatus("2");
			goods.setStatusDesc( "无效");
			return goods;
		}
	}
	
	
	/**
	 * 方法名: getGoodsStock
	 * <br/>作用: 根据仓库获取库存信息
	 * <br/>参数:@param bk_id
	 * <br/>参数:@param cp
	 * <br/>参数:@param cartGoodss
	 * <br/>参数:@param checkSum 
	 * <br/>返回值: void
	 * <br/>@Throws 
	 * @throws Exception 
	 */ 
	@Override
	public String getGoodsStock(String bk_id, ProductPara para,Checksum checkSum) throws Exception {
		log.info("getGoodsStock 单品------bk_id--->"+bk_id);
		String flag = "1";
		GoodsStockByIdParameters param = new GoodsStockByIdParameters();
		param.setProduct_id(bk_id);
		param.setPvc_id(para.getPvc_id());
		param.setCountry_id(para.getCountry_id());
		param.setLocal_id(para.getLocal_id());
		String json = logisticsService.queryGoodsStockById(checkSum, param);//查询库存
		log.info("getGoodsStock 单品------json--->"+json);
		JSONObject returnJson = JSONObject.fromObject(json);
		if(returnJson.getInt("flag")==0){
			String error = returnJson.toString();
			log.error("getGoodsStock 单品=====>比酷返回错误信息：" + error);
			flag = "2";
		}else{
			JSONObject msgJson = JSONObject.fromObject(returnJson.getString("msg"));
			JSONObject prodstock =JSONObject.fromObject(msgJson.getString("prodstock"));
			String[] ids = bk_id.split(",");
			for(int i=0;i<ids.length;i++){
				log.info("getGoodsStock 单品------ids --->"+ids[i].trim());
				JSONObject o =JSONObject.fromObject(prodstock.get(ids[i].trim()));
				int order_ok = o.getInt("order_ok");//接单属性 0：无限制，1、仓库接单，2、预采购量接单
				int amt = o.getInt("amt");//可用库存量
				if(order_ok==0 ){//一直有货
					flag = "1";
				} 
				if(amt>0){//可用库存量
					if(amt-1>=0){
						flag = "1";
					}else{
						flag = "2";
					}
				}else if(amt==0){
					flag = "2";
				}else{
					flag = "2";
				}
			}
		}
		return flag;
	}

	@Override
	public MemberInfo getMemberInfoByAddr(String member_id, String addr_id)
			throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("member_id",member_id );
		map.put("addr_id", addr_id);
		return cartDaoImpl.getMemberInfoByAddr(map);
	}
}
