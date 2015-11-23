package com.hm.hxl.order.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.springframework.web.client.RestTemplate;

/** 
 * <pre>
 * 类名: UrlUrils 
 * 作用: TODO(这里用一句话描述这个类的作用) 
 * 作者: yanpengjie 
 * 日期: 2015年3月23日 上午11:47:52 
 * </pre>
 **/

@SuppressWarnings({ "unchecked", "rawtypes" })

public class UrlUtils {
	
	public static final String ABOUTUS = "aboutUs";
	public static final String ALIPAY = "alipay";
	public static final String ARTICLE = "article";
	public static final String BANNER = "banner";
	public static final String BRAND = "brand";
	public static final String CART = "cart";
	public static final String COMMENT = "comment";
	public static final String COUPONS = "coupons";
	public static final String AREA = "area";
	public static final String LOGISTICS = "logistics";
	public static final String MAIN = "Main";
	public static final String ORDER = "order";
	public static final String PAYMENTS = "payments";
	public static final String PRODUCT = "product";
	public static final String SCREEN = "screen";
	public static final String UNIONPAY = "unionpay";
	public static final String USER = "user";
	public static final String VERSION = "version";
	public static final String THIRDORDER = "thirdOrder";
	public static final String KQPAY = "kqpay";
	public static final String KD = "kd";
	
	private static RestTemplate restTemplate = new RestTemplate();
    
	/**
	 * <pre>
	 * 方法名: theCustomUrl
	 * 作用: 针对所有包自定义URL
	 * @param ip ip地址,如果为""或null,默认127.0.0.1 即localhost
	 * @param point 端口,,如果为""或null,默认80端口
	 * @param method 执行的方法 如:getBrandGroupProList
	 * @param parentPackage 执行的方法所在的包路径 如:cart 在UrlUtil类内有对应字段可选
	 * @param param 参数
	 * @return 
	 * 返回值: String url
	 * @Throws 
	 * </pre>
	 */
    public static String theCustomUrl(String url,String parentPackage,String method,Map<String,Object> param){
    	String s2 = url+"/services/"+parentPackage+"/"+method;
    	String params = "";
    	if(param!=null && param.size()>0){
    		Set<String> keySet = param.keySet();
    		int size = keySet.size();
    		int index = 0;
    		for (String key : keySet) {
    			index++;
				Object value = param.get(key);
				if(index==1 && size>index){
					params += ("?"+key+"="+value+"&");
				}else if(index==1 && size==index){
					params += ("?"+key+"="+value);
				}else if(index==size){
					params += (key+"="+value);
				}else
					params += (key+"="+value+"&");
			}
    	}
    	String urlz = s2+params;
		return urlz;
    }
    
    /**
     * <pre>
     * 方法名: runUrl
     * 作用: 运行url,并打印结果
     * @param url 
     * 返回值: void
     * @Throws 
     * </pre>
     */
	public static String runUrl(String url){
		System.out.println(url);
		try{
			Map map = new HashMap();
			String message = restTemplate.postForObject(url, null, String.class, map);
			System.out.println(message.toString());
			return message;
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
}
