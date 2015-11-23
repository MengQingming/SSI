package com.hm.hxl.order.util;

import com.caucho.hessian.client.HessianProxyFactory;
//import com.hm.payments.alipay.hessian.IAlipayControllerService;
//import com.hm.payments.kqpay.hessian.IKqPayControllerService;
//import com.hm.payments.unionpay.hessian.IUnionPayControllerService;

public class HessianUtil {
	
	public final static String ALIPAY_PATH = "http://10.0.129.59:8087/payments/services/hessian/pay/alipay"; 
	public final static String KQPAY_PATH = "http://10.0.129.59:8087/payments/services/hessian/pay/kqpay"; 
	public final static String UNIONPAY_PATH = "http://10.0.129.59:8087/payments/services/hessian/pay/unionpay"; 
	
	
	@SuppressWarnings("unchecked")
	public static <T> T getInterface(Class<T> clazz,String path) throws Exception{
		HessianProxyFactory factory = new HessianProxyFactory();
		T t = (T) factory.create(clazz, path);
		return t;
	}
	
//	public static IAlipayControllerService getAlipayInterface() throws Exception{
//		return getInterface(IAlipayControllerService.class, ALIPAY_PATH);
//	}
//	
//	public static IKqPayControllerService getKqpayInterface() throws Exception{
//		return getInterface(IKqPayControllerService.class, KQPAY_PATH);
//	}
//	
//	public static IUnionPayControllerService getUnionpayInterface() throws Exception{
//		return getInterface(IUnionPayControllerService.class, UNIONPAY_PATH);
//	}
	
}
