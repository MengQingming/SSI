package com.hm.hxl.order.order.dao;

import java.util.List;
import java.util.Map;

import com.hm.hxl.order.order.model.Couponlog4User;
import com.hm.hxl.order.order.model.Coupons;
import com.hm.hxl.order.order.model.CouponsNew;
import com.hm.hxl.order.order.model.CouponsType;

public interface ICouponsDao {

	public List<Coupons> getCouponsList(String member_id,Integer pageSize,Integer pageNum) throws Exception;
	
	public void updateCoupons(String member_id,String memccode)throws Exception;
	
	public void saveLogCouponsRef(String order_id,String memccode)throws Exception;
	
	public Map<String,Object> queryCouponLogOrderUser(String order_id,String member_id)throws Exception;
	
	public void updateCoupons2(String member_id,String memc_code)throws Exception;
	
	public void deleteLogCouponsRef(String order_id,String memc_code)throws Exception;
	
	public void deleteLogCouponsUser(String order_id,String memc_code)throws Exception;
	
	public int getCouponsCount(String member_id) throws Exception;
	
	public void addCoupons(String member_id,String memc_code,String cnps_id) throws Exception;
	
	public List<CouponsNew> getCouponsNewByMemc_code(String memc_code) throws Exception;

	public CouponsType getCouponsTypeByType_used(String type_used)throws Exception;
	
	public String getIsUsedCoupons(String memc_code)throws Exception;

	public void saveLogCouponsUser(Couponlog4User couponlog)throws Exception;

	public List<CouponsNew> getCouponsNewByitem_id(String item_id)throws Exception;

}
