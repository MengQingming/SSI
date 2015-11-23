package com.hm.hxl.order.order.service.impl;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.hm.hxl.order.order.model.GoodsStockByIdParameters;
import com.hm.hxl.order.order.service.LogisticsService;
import com.hm.hxl.order.util.Checksum;
import com.hm.hxl.order.util.Constant;
import com.hm.hxl.order.util.Md5;
import com.hm.hxl.order.util.SystemConfig;

/**
 * 类名: LogisticsServiceImpl 
 * 作用: 查询仓库存量service实现
 * 作者: yanpengjie 
 * 日期: 2015-7-17 下午12:55:23 
 * 版本: V 1.0
 *
 */
@SuppressWarnings({"unused"})
@Service
public class LogisticsServiceImpl implements LogisticsService {
	
	/**日志**/
	private static Logger log = Logger.getLogger(LogisticsServiceImpl.class);
	
	@Autowired
	private RestTemplate restTemplate;

	 @SuppressWarnings({"rawtypes","unchecked"})
	@Override
	public String queryGoodsStockById(Checksum checkSum,GoodsStockByIdParameters param) throws Exception {
		log.info("LogisticsServiceImpl queryGoodsStockById====================param:" + JSONObject.fromObject( param ).toString());
		// RestTemplate restTemplate = new RestTemplate();
		 if(checkSum==null){checkSum =new Checksum();}
		 String key = Md5.encipher( checkSum.toString() + param.toString() + Constant.PRIVATE_KEY );
		 checkSum.setSign(key);
		 String hxl_type = checkSum.getHxl_type();
		 String webService = new SystemConfig("clientService").getProperty(hxl_type);
	     String url = new SystemConfig(webService).getProperty("logistics_url")+"?sign="+checkSum.getSign()+"&api_key="+checkSum.getApi_key()+"&nonce="+checkSum.getNonce()
	     	+"&version="+checkSum.getVersion()+"&sign_method="+checkSum.getSign_method()+"&timestamp="+checkSum.getTimestamp()+"&type="+checkSum.getType()
	     	+"&prod_id="+param.getProduct_id()+"&pvc_id="+param.getPvc_id()+"&local_id="+param.getLocal_id()+"&country_id="+param.getCountry_id();
	    
		Map map = new HashMap();
		Object message = restTemplate.getForObject(url, Object.class, map);
		log.info("LogisticsServiceImpl queryGoodsStockById====================response.get_return() : " + message.toString());
		return message.toString();
	}
	
	public static void main(String[] args) throws Exception{
		LogisticsServiceImpl s = new LogisticsServiceImpl();
		GoodsStockByIdParameters param = new GoodsStockByIdParameters();
		param.setProduct_id("93732");
		param.setPvc_id("11");
		param.setLocal_id("11021");
		param.setCountry_id("11001");
		String json = s.queryGoodsStockById(null,param);
		JSONObject returnJson = JSONObject.fromObject(json);
		JSONObject msgJson = JSONObject.fromObject(returnJson.getString("msg"));
		JSONObject prodstock =JSONObject.fromObject(msgJson.getString("prodstock"));
		JSONObject o =JSONObject.fromObject(prodstock.get("93732"));
		int pro_id = o.getInt("prod_id");//产品id
		int order_ok = o.getInt("order_ok");//接单属性 0：无限制，1、仓库接单，2、预采购量接单
		int amt = o.getInt("amt");//可用库存量
		int GOOD_QTY = o.getInt("GOOD_QTY");//库存数量
		int TEL_QTY = o.getInt("TEL_QTY");//订单压单数量
		int TRAC_OUT_QTY = o.getInt("TRAC_OUT_QTY");//仓库压单数量
		int NOTICE_QTY = o.getInt("NOTICE_QTY");//通知单压单数量
		int PO_SCHD_QTY = o.getInt("PO_SCHD_QTY");//采购在途数量
		System.out.println(order_ok+"-"+amt+"-"+NOTICE_QTY+"-"+PO_SCHD_QTY+"-"+Md5.encipher("75"));
	}
}
