package com.hm.hxl.order.order.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hm.hxl.order.order.dao.IOrderDao;
import com.hm.hxl.order.order.model.Address;
import com.hm.hxl.order.order.model.CancelOrderItemsInventory;
import com.hm.hxl.order.order.model.OrderDetail;
import com.hm.hxl.order.order.model.OrderItems;
import com.hm.hxl.order.order.model.OrderObjects;
import com.hm.hxl.order.order.model.OrderParameter;
import com.hm.hxl.order.order.model.OrderPay;
import com.hm.hxl.order.util.Checksum;
import com.hm.hxl.order.util.Constant;
import com.hm.hxl.order.util.ErrorMsg;
import com.hm.hxl.order.util.StringUtil;
import com.hm.hxl.order.util.SystemConfig;
import com.ibatis.sqlmap.client.SqlMapClient;
/**
 * 
 * @author hanhangyun
 *
 */
@SuppressWarnings({"unchecked","rawtypes","unused" })
@Repository
public class OrderDaoImpl implements IOrderDao{

	@Autowired
	private SqlMapClient sqlMapClient;

	@Override
	public boolean getOrderId(String orderId) throws Exception{
		int i = (Integer) sqlMapClient.queryForObject("order.getOrderId", orderId);
		if(i>=1){
			return true;
		}
		return false;
	}

	
	@Override
	public void saveOrder(OrderParameter order) throws Exception {
		sqlMapClient.insert("order.saveOrder", order);
	}

	@Override
	public Address getAddress(OrderParameter order) throws Exception {
		return (Address) sqlMapClient.queryForObject("order.getAddress",order);
	}

	@Override
	public List<Map<String, Object>> getOrderGoods(OrderParameter order) throws Exception {
		return  sqlMapClient.queryForList("order.getOrderGoods",order);
	}

	@Override
	public Integer saveOrderObjects(OrderObjects obj) throws Exception {
		return (Integer) sqlMapClient.insert("order.saveOrderObjects", obj);
	}

	@Override
	public List<Map<String, Object>> getOrderItems(String product_id) throws Exception {
		return  sqlMapClient.queryForList("order.getOrderItems",product_id);
	}

	@Override
	public Integer saveOrderItems(OrderItems item) throws Exception {
		return (Integer) sqlMapClient.insert("order.saveOrderItems", item);
	}

	@Override
	public void saveOrderLog(OrderParameter order) throws Exception {
		sqlMapClient.insert("order.saveOrderLog", order);
	}

	@Override
	public List<Map<String, Object>> getOrderFromSto(OrderParameter order)
			throws Exception {
		return  sqlMapClient.queryForList("order.getOrderFromSto",order);
	}

	@Override
	public void saveLimitBuyObject(OrderObjects obj) throws Exception {
		 sqlMapClient.insert("order.saveLimitBuyObject",obj);
	}

	@Override
	public List<Map<String,Object>> getOrderList(Checksum checkSum,String member_id,Integer pageNum)throws Exception {
		Map map= new HashMap();
		map.put("member_id", member_id);
		String hxl_type = checkSum.getHxl_type();
		String webService = new SystemConfig("clientService").getProperty(hxl_type);
		SystemConfig systemConfig = new SystemConfig(webService);
		String property = systemConfig.getProperty("picture_url");
		map.put("picture_url", new SystemConfig(webService).getProperty("picture_url"));
		map.put("pageSize", Constant.PAGESIZE);
		map.put("pageNum", Constant.PAGESIZE*(pageNum-1));
		return sqlMapClient.queryForList("order.getOrderList",map);
	}

	@Override
	public List<Map<String,Object>> getAPPOrderList(Checksum checkSum,String member_id,Integer pageNum)throws Exception {
		Map map= new HashMap();
		map.put("member_id", member_id);
		String hxl_type = checkSum.getHxl_type();
		String webService = new SystemConfig("clientService").getProperty(hxl_type);
		map.put("picture_url", new SystemConfig(webService).getProperty("picture_url"));
		map.put("pageSize", Constant.PAGESIZE);
		map.put("pageNum", Constant.PAGESIZE*(pageNum-1));
		return sqlMapClient.queryForList("order.getAPPOrderList",map);
	}
	
	@Override
	public List<Map<String, String>> getOrderGoodsList(Checksum checkSum,String order_id)
			throws Exception {
		Map<String,String> map= new HashMap<String,String>();
		map.put("order_id", order_id);
		String hxl_type = checkSum.getHxl_type();
		String webService = new SystemConfig("clientService").getProperty(hxl_type);
		map.put("picture_url", new SystemConfig(webService).getProperty("picture_url"));
		return sqlMapClient.queryForList("order.getOrderGoodsList",map);
	}

	@Override
	public OrderDetail getOrderDetail(String order_id, String member_id)
			throws Exception {
		Map<String,String> map= new HashMap<String,String>();
		map.put("order_id", order_id);
		map.put("member_id", member_id);
		return (OrderDetail) sqlMapClient.queryForObject("order.getOrderDetail",map);
	}

	@Override
	public List<Map<String, Object>> getOrderLogList(String order_id)
			throws Exception {
		return sqlMapClient.queryForList("order.getOrderLogList", order_id);
	}

	@Override
	public Map<String, Object> getOrderInfo(String order_id) throws Exception {
		return (Map<String, Object>) sqlMapClient.queryForObject("order.getOrderInfo", order_id);
	}

	@Override
	public int cancelOrder(String order_id, String member_id) throws Exception {
		Map<String,String> map= new HashMap<String,String>();
		map.put("order_id", order_id);
		map.put("member_id", member_id);
		int i = sqlMapClient.update("order.cancelOrder", map);
		return i;
	}

	@Override
	public void saveCancelOrderLog(String order_id, String member_id,String reason) throws Exception {
		Map<String,String> map= new HashMap<String,String>();
		map.put("order_id", order_id);
		map.put("member_id", member_id);
		if(StringUtil.isEmpty(reason)){//网站用的是1，2，3.....  app端用0
			map.put("reason", "0");
		}else{
			map.put("reason", reason);
		}
		sqlMapClient.update("order.saveCancelOrderLog", map);
	}

	@Override
	public List<Map<String, Object>> getOrderListByOrderRel(String order_rel)
			throws Exception {
		return sqlMapClient.queryForList("order.getOrderListByOrderId", order_rel);
	}

	@Override
	public List<Map<String, Object>> getOrderItemsByOrderId(String order_id)
			throws Exception {
		return sqlMapClient.queryForList("order.getOrderItemsByOrderId", order_id);
	}

	@Override
	public int getOrderNumbers(String member_id) throws Exception {
		return (Integer) sqlMapClient.queryForObject("order.getOrderNumbers",member_id);
	}

	@Override
	public void updateOrderBkid(Map map) throws Exception {
		
		sqlMapClient.update("order.updateOrderBkid", map);
	}

	@Override
	public void deleteLimitBuy(String order_id) throws Exception {
		sqlMapClient.delete("order.deleteLimitBuy", order_id);
	}

	@Override
	public int isExistOrder(String member_id) throws Exception {
		return (Integer) sqlMapClient.queryForObject("order.isExistOrder", member_id);
	}

	@Override
	public Map<String, Object> getAppRuleOrder(String actionCondition)
			throws Exception {
		return (Map<String, Object>) sqlMapClient.queryForObject("order.getAppRuleOrder", actionCondition);
	}

	@Override
	public double getFirstpriceByDt_id(String dt_id) throws Exception {
		return  (Double) sqlMapClient.queryForObject("order.getFirstpriceByDt_id", dt_id);
	}

	@Override
	public List<Map<String, Object>> getBikuOrderInfo(String order_rel) throws Exception {
		return sqlMapClient.queryForList("order.getBikuOrderInfo", order_rel);
	}

	@Override
	public void deleteOrderByOrder_rel(String order_rel)throws Exception  {
		sqlMapClient.delete("order.deleteOrderByOrder_rel", order_rel);
	}

	@Override
	public void deleteOrderItemsByOrder_rel(String order_rel) throws Exception {
		sqlMapClient.delete("order.deleteOrderItemsByOrder_rel", order_rel);
	}

	@Override
	public void deleteOrderObjectsByOrder_rel(String order_rel)
			throws Exception {
		sqlMapClient.delete("order.deleteOrderObjectsByOrder_rel", order_rel);
	}

	@Override
	public Map<String, Object> getPrimageByCurrentTime(String c_template) throws Exception {
		return (Map<String, Object>) sqlMapClient.queryForObject("order.getPrimageByCurrentTime",c_template);
	}


	@Override
	public List<Map<String, Object>> getPrductInfoByOrder_rel(String order_rel)
			throws Exception {
		return sqlMapClient.queryForList("order.getPrductInfoByOrder_rel",order_rel);
	}

    
	
	@Override
	public List<CancelOrderItemsInventory> getYaDanProductByOrder_id(
			String order_id ,String member_id) throws Exception {
		Map<String,String> map= new HashMap<String,String>();
		map.put("order_id", order_id);
		map.put("member_id", member_id);
		return sqlMapClient.queryForList("order.getYaDanProductByOrder_id",map);
	}


	//通过product_id nums  对货品进行库存释放   + nums
	@Override
	public void addNumsToLimitbuyusQuantity(CancelOrderItemsInventory citems)
			throws Exception {
		sqlMapClient.update("order.addNumsToLimitbuyusQuantity", citems);
	}

	@Override
	public void updateUsed_storeByOrder_rel(Map map) throws Exception {
		sqlMapClient.update("order.updateUsed_storeByOrder_rel", map);
	}

	
	@Override
	public void updateUsed_storeByOrder_id(Map map) throws Exception {
		sqlMapClient.update("order.updateUsed_storeByOrder_id", map);
	}


	@Override
	public List<OrderPay> getPayGoodsByOrder_rel(String order_rel)
			throws Exception {
		return sqlMapClient.queryForList("order.getPayGoodsByOrder_rel",order_rel);
	}

	@Override
	public void updateItemPriceByitem_id(Map map)
			throws Exception {
		sqlMapClient.update("order.updateItemPriceByitem_id", map);
	}

	@Override
	public void updateObjectsPriceByobj_id(Map map) throws Exception {
		sqlMapClient.update("order.updateObjectsPriceByobj_id", map);
	}

	@Override
	public void updateOrderPriceByOrder_id(Map map) throws Exception {
		sqlMapClient.update("order.updateOrderPriceByOrder_id", map);
	}

	@Override
	public void updateOrderCost_freightByOrder_id(Map map) throws Exception {
		sqlMapClient.update("order.updateOrderCost_freightByOrder_id", map);
	}

	@Override
	public void updateItemgGPriceByItem_id(Map map) throws Exception {
		sqlMapClient.update("order.updateItemgGPriceByItem_id", map);
	}

	@Override
	public void updateOrderCost_itemByOrder_id(Map map) throws Exception {
		sqlMapClient.update("order.updateOrderCost_itemByOrder_id", map);
	}

	@Override
	public void updateOrderItemIs_limitbuyByItem_id(Map map) throws Exception {
		sqlMapClient.update("order.updateOrderItemIs_limitbuyByItem_id", map);
	}

	@Override
	public String getOrder_relByOrder_id(String order_id) throws Exception {
		return (String) sqlMapClient.queryForObject("order.getOrder_relByOrder_id", order_id);
	}

	@Override
	public String getShip_area(String order_rel) throws Exception  {
		return (String) sqlMapClient.queryForObject("order.getShip_area", order_rel);
	}


	@Override
	public List<OrderParameter> getOrderParameters(String order_rel)
			throws Exception {
		return sqlMapClient.queryForList("order.getOrderParameters", order_rel);
	}


	@Override
	public List<OrderObjects> getOrderObjectss(String order_rel)
			throws Exception {
		return sqlMapClient.queryForList("order.getOrderObjectss", order_rel);
	}


	@Override
	public List<OrderItems> getOrderItemss(String order_rel) throws Exception {
		return sqlMapClient.queryForList("order.getOrderItemss", order_rel);
	}


	@Override
	public void cancelOrderParameterPmt(OrderParameter op) throws Exception {
		sqlMapClient.update("order.cancelOrderParameterPmt", op);
	}


	@Override
	public void cancelOrderItemsPmt(OrderItems oi) throws Exception {
		sqlMapClient.update("order.cancelOrderItemsPmt", oi);
	}


	@Override
	public void saveCouponsItemRel(Map map) throws Exception {
		sqlMapClient.insert("order.saveCouponsItemRel", map);
	}

	@Override
	public void updatePrice4OrderItems(OrderItems oi) throws Exception {
		sqlMapClient.update("order.updatePrice4OrderItems", oi);
	}

	@Override
	public void updatePrice4OrderObjects(OrderObjects ob) throws Exception {
		sqlMapClient.update("order.updatePrice4OrderObjects", ob);
	}

	@Override
	public void updatePrice4OrderParameter(OrderParameter op) throws Exception {
		sqlMapClient.update("order.updatePrice4OrderParameter", op);
	}

	@Override
	public void deleteCouponsUse(String order_rel) throws Exception {
		sqlMapClient.delete("order.deleteCouponsUse", order_rel);
	}

	@Override
	public void deleteCouponsItemUse(String order_rel) throws Exception {
		sqlMapClient.update("order.deleteCouponsItemUse", order_rel);
	}


	@Override
	public List<String> getOrder_ids(String order_rel) throws Exception {
		return sqlMapClient.queryForList("order.getOrder_ids", order_rel);
	}


	@Override
	public List<Map<String, Object>> getWebOrderList(Checksum checkSum,String member_id,
			Integer pageNum, String status) throws Exception {
		Map map= new HashMap();
		map.put("member_id", member_id);
		String hxl_type = checkSum.getHxl_type();
		String webService = new SystemConfig("clientService").getProperty(hxl_type);
		map.put("picture_url", new SystemConfig(webService).getProperty("picture_url"));
		map.put("pageSize", Constant.WEBSIZE);
		map.put("pageNum", Constant.WEBSIZE*(pageNum-1));
		map.put("status", status);
		return sqlMapClient.queryForList("order.getWebOrderList",map);
	}


	@Override
	public Integer verificationOZF(String order_rel) throws Exception {
		return (Integer) sqlMapClient.queryForObject("order.verificationOZF", order_rel);
	}


	@Override
	public int getWebOrderNum(String member_id,String status) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("member_id", member_id);
		map.put("status", status);
		return (Integer) sqlMapClient.queryForObject("order.getWebOrderNum", map);
	}



	@Override
	public String getOrderAmount(String order_rel) throws Exception {
		return (String) sqlMapClient.queryForObject("order.getOrderAmount", order_rel);
	}


	@Override
	public String getBiKuIDByOr(String order_rel) throws Exception {
		return (String) sqlMapClient.queryForObject("order.getBiKuIDByOr", order_rel);
	}


	@Override
	public boolean getAlipayStatus(String order_rel) throws Exception {
		int i = (Integer) sqlMapClient.queryForObject("order.getAlipayStatus", order_rel);
		if(i>=1){
			return true;
		}
		return false;
	}


	@Override
	public void updateUserCoupons(List<String> memc_codes, String member_id,boolean isUse)
			throws Exception {
		if(memc_codes!=null && memc_codes.size()>0){
			Map map = new HashMap();
			map.put("memc_codes", memc_codes);
			map.put("member_id", member_id);
			map.put("disabled", String.valueOf(isUse));
			sqlMapClient.update("order.updateUserCoupons", map);
		}
	}


	@Override
	public List<String> getUseMemc_codes(String order_rel) throws Exception {
		return sqlMapClient.queryForList("order.getUseMemc_codes", order_rel);
	}


	@Override
	public String getOrderMember_id(String order_rel) throws Exception {
		return (String) sqlMapClient.queryForObject("order.getOrderMember_id", order_rel);
	}


	@Override
	public void saveErrorMsg(ErrorMsg msg) throws Exception {
		sqlMapClient.insert("order.saveErrorMsg", msg);
	}

}
