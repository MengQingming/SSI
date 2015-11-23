package com.hm.hxl.order.order.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hm.hxl.order.order.dao.ICouponsDao;
import com.hm.hxl.order.order.model.Couponlog4User;
import com.hm.hxl.order.order.model.Coupons;
import com.hm.hxl.order.order.model.CouponsGoods;
import com.hm.hxl.order.order.model.CouponsNew;
import com.hm.hxl.order.order.model.CouponsType;
import com.ibatis.sqlmap.client.SqlMapClient;

@SuppressWarnings({ "rawtypes", "unchecked" })
@Repository
public class CouponsDaoImpl implements ICouponsDao {
	
	@Autowired
	private SqlMapClient sqlMapClient;

	@Override
	public List<Coupons> getCouponsList(String member_id,Integer pageSize,Integer pageNum) throws Exception{
		Map map = new HashMap();
		map.put("member_id", member_id);
		map.put("pageSize", pageSize);
		map.put("pageNum", pageSize*(pageNum-1));
		return sqlMapClient.queryForList("coupons.getCouponsList", map);
	}

	@Override
	public void updateCoupons(String member_id, String memc_code)
			throws Exception {
		Map map = new HashMap();
		map.put("member_id", member_id);
		map.put("memc_code",memc_code);
		sqlMapClient.update("coupons.updateCoupons", map);
	}

	@Override
	public void saveLogCouponsRef(String order_id, String memccode)throws Exception {
		// TODO Auto-generated method stub
		Map map = new HashMap();
		map.put("order_id", order_id);
		map.put("memc_code",memccode);
		sqlMapClient.insert("coupons.saveLogCouponsRef", map);
	}
	
	@Override
	public Map<String, Object> queryCouponLogOrderUser(String order_id,String member_id)throws Exception {
		// TODO Auto-generated method stub
		Map map = new HashMap();
		map.put("member_id", member_id);
		map.put("order_id",order_id);
		return (Map<String, Object>) sqlMapClient.queryForObject("coupons.queryCouponLogOrderUser", map);
	}

	@Override
	public void updateCoupons2(String member_id, String memc_code)throws Exception {
		Map map = new HashMap();
		map.put("member_id", member_id);
		map.put("memc_code",memc_code);
		sqlMapClient.update("coupons.updateCoupons2", map);
	}

	@Override
	public void deleteLogCouponsRef(String order_id, String memc_code)throws Exception {
		// TODO Auto-generated method stub
		Map map = new HashMap();
		map.put("order_id", order_id);
		map.put("memc_code",memc_code);
		sqlMapClient.delete("coupons.deleteLogCouponsRef", map);
	}

	@Override
	public void deleteLogCouponsUser(String order_id, String memc_code) throws Exception {
		// TODO Auto-generated method stub
		Map map = new HashMap();
		map.put("order_id", order_id);
		map.put("memc_code",memc_code);
		sqlMapClient.delete("coupons.deleteLogCouponsUser", map);
	}

	@Override
	public int getCouponsCount(String member_id) throws Exception {
		// TODO Auto-generated method stub
		return (Integer) sqlMapClient.queryForObject("coupons.getCouponsCount", member_id);
	}

	@Override
	public void addCoupons(String member_id, String memc_code, String cnps_id)
			throws Exception {
		Map map = new HashMap();
		map.put("member_id", member_id);
		map.put("memc_code", memc_code);
		map.put("cnps_id",cnps_id);
		sqlMapClient.insert("coupons.addCoupons", map);
	}

	@Override
	public List<CouponsNew> getCouponsNewByMemc_code(String memc_code) throws Exception {
		String[] split = memc_code.split(",");
		List<String> memc_codes = new ArrayList<String>();
		for (String str : split) {
			memc_codes.add(str);
		}
		List<CouponsNew> couponsNews = sqlMapClient.queryForList("coupons.getCouponsNewByMemc_codes", memc_codes);
		for (CouponsNew couponsNew : couponsNews) {
			String cpns_id = couponsNew.getCpns_id();
			List<CouponsGoods> couponsGoodss = sqlMapClient.queryForList("coupons.getCouponsGoodsByCpns_id", cpns_id);
			couponsNew.setCouponsGoodss(couponsGoodss);
		}
		return couponsNews;
	}

	@Override
	public CouponsType getCouponsTypeByType_used(String type_used)
			throws Exception {
		return (CouponsType) sqlMapClient.queryForObject("coupons.getCouponsTypeByType_used", type_used);
	}

	@Override
	public String getIsUsedCoupons(String memc_code) throws Exception {
		String[] split = memc_code.split(",");
		List<String> memc_codes = new ArrayList<String>();
		for (String str : split) {
			memc_codes.add(str);
		}
		String rStr = "";
		List<String> strs = sqlMapClient.queryForList("coupons.getIsUsedCoupons", memc_codes);
		if(strs!=null && strs.size()>0){
			for (String str : strs) {
				rStr += str+",";
			}
		}
		if(rStr.length()>0){
			rStr = rStr.substring(0, rStr.length()-1);
			return rStr;
		}else{
			return null;
		}
	}

	@Override
	public void saveLogCouponsUser(Couponlog4User couponlog) throws Exception {
		sqlMapClient.insert("coupons.saveLogCouponsUser", couponlog);
	}

	@Override
	public List<CouponsNew> getCouponsNewByitem_id(String item_id)
			throws Exception {
		List<CouponsNew> cns = sqlMapClient.queryForList("coupons.getCouponsNewByitem_id", item_id);
		return cns;
	}
}
