/**
 * 项目名: hjkClient
 * 文件名: Constant.java
 * Copyright 2014 北京惠买在线网络科技有限公司 
 */
package com.hm.hxl.order.util;

/**
 * 
 * 类 名: Constant
 * <br/>描 述: 初始默认工具类
 * <br/>作 者: yanpengjie
 * <br/>创 建： 2014-8-15
 * <br/>版 本：v1.0
 *
 * <br/>历 史: (版本) 无
 */
public class Constant {
	public static final String SUCCESS = "1";//接口成功
	public static final String ERROR = "-1";//接口错误
	public static final String EXCEPTIONS = "0";//接口异常
	public static final String HM101="HM101";//请求成功
	public static final String HM503="HM503";//签名错误
	public static final String PARAMETER_ERROR = "HM507"; //入参异常
	public static final String NO = "0";
	public static final String YES = "1";
	public static final String UPDATEFAIL= "0";
	public static final String UPDATESUCC = "1";
	public static final String HM500 = "HM500";
	public static final String HM504 = "HM504";//参数错误
	public static final String HM505 = "HM505";//调用比酷，返回失败（flag=0)
	public static final String HM506 = "HM506";//远程调用异常
	
	/**手机号码为空code**/
	public static final String SMS001 = "SMS001";
	/**手机号码为空描述**/
	public static final String SMS001_MESSAGE = "手机号码为空";
	
	/**手机号码错误code**/
	public static final String SMS002 = "SMS002";
	/**手机号码错误描述**/
	public static final String SMS002_MESSAGE = "手机号码错误";
	
	/**短信内容为空code**/
	public static final String SMS003 = "SMS003";
	/**短信内容为空描述**/
	public static final String SMS003_MESSAGE = "短信内容为空";
	
	/**调用失败,短信网关返回未知错误code**/
	public static final String SMS004 = "SMS004";
	/**调用失败,短信网关返回未知错误描述**/
	public static final String SMS004_MESSAGE = "接口调用失败";
	
	/**短信网关没有返回code**/
	public static final String SMS005 = "SMS005";
	/**短信网关没有返回描述**/
	public static final String SMS005_MESSAGE = "短信网关没有返回";
	
	
	/**
	 * 操作类型
	 */
	public static final Integer ADD_GOODS = 1;//新增商品
	public static final Integer UPDATE_GOODS = 2;//修改商品
	public static final Integer DOWN_GOODS = 3;//下架商品
	
	 // 网站分类ID
	public static final String  CAT_ID = "0";
	
	/*
	 * datasyn webservice parameters
	 */
	public static final Integer DATA_SYN_SUCCEED = 1;
	public static final Integer DATA_SYN_EXCEPTION = 0;
	public static final Integer DATA_SYN_FAILED = -1;
	//商品产品参数
	public static final String DATA_SYN_COMMON_HMDS_200 = "HMDS200";//未捕获异常
	public static final String DATA_SYN_COMMON_HMDS_201 = "HMDS201";//参数格式化异常
	public static final String DATA_SYN_COMMON_HMDS_202 = "HMDS202";//json字符串转java对象异常
	public static final String DATA_SYN_COMMON_HMDS_203 = "HMDS203";//添加更新成功
	
	//视频参数
	public static final String DATA_SYN_VIDEO_HMDS_204 = "HMDS204";//未捕获异常
	public static final String DATA_SYN_VIDEO_HMDS_205 = "HMDS205";//视频参数格式化异常
	public static final String DATA_SYN_VIDEO_HMDS_206 = "HMDS206";//json字符串转java对象异常
	public static final String DATA_SYN_VIDEO_HMDS_207 = "HMDS207";//添加视频成功
	
	public static final String CONFIRM_MSG_ADD="已确认上架";
	public static final String CONFIRM_MSG_DEL="已确认下架";
	public static final String CONFIRM_MSG_CHANGE="已确认更新";
	public static final String CONFIRM_MSG_IGNORE="已确认忽略";
	
	public static final String RETURN_SUCCESS="success";
	public static final String RETURN_FAILED="fail";
	
	/**
	 * 普通常量
	 */
	public static final Integer ZERO = 0;
	
	public static final Integer ALL = 1;
	public static final Integer CHECKED = 2;
	public static final Integer REFUSE = 3;
	public static final Integer DELETED = 4;
	
	/**
	 * 订单常量
	 */
	public static final String ORDER_SYN_STATUS_SUCCEED = "HMDS300";
	
	/**
	 * webService调用返回参数类型JSON
	 */
	public static final String WEBSERVICE_RETURN_TYPE_JSON = "1";
	/**
	 * webService调用返回参数类型XML
	 */
	public static final String WEBSERVICE_RETURN_TYPE_XML = "2";
	
	/**
	 * ERP订单状态
	 */
	/**
	 * 待审核
	 */
	public static final String ORDER_STATUS_DAISHENHE = "0";
	/**
	 * 通知单
	 */
	public static final String ORDER_STATUS_TONGZHIDAN = "9";
	/**
	 * 已分单
	 */
	public static final String ORDER_STATUS_YIFENDAN = "2";
	/**
	 * 已发货
	 */
	public static final String ORDER_STATUS_YIFAHUO = "11";
	/**
	 * 问题单
	 */
	public static final String ORDER_STATUS_WENTIDAN = "8";
	/**
	 * 已结转
	 */
	public static final String ORDER_STATUS_YIJIEZHUAN = "4";
	/**
	 * 未通过
	 */
	public static final String ORDER_STATUS_WEITONGGUO = "7";
	/**
	 * 取消单
	 */
	public static final String ORDER_STATUS_QUXIAODAN = "6";
	/**
	 * 已退单
	 */
	public static final String ORDER_STATUS_YITUIDAN = "5";

	
	/**
	 * 订单付款状态:未支付;
	 */
	public static final String PAY_STATUS_UNPAID = "0";
	/**
	 * 订单付款状态:已支付;
	 */
	public static final String PAY_STATUS_PAID = "1";
	
	/**
	 * 订单发货状态
	 */
	/**
	 * 未发货
	 */
	public static final String SHIP_STATUS_UNSHIPPED = "0";
	/**
	 * 已发货
	 */
	public static final String SHIP_STATUS_SHIPPED = "1";
	
	/**
	 * 已分单
	 */
	public static final String SHIP_STATUS_YIFENDAN = "20";
	
	/**
	 * 订单状态:活动订单
	 */
	public static final String ORDER_STATUS_ACTIVE = "active";
	/**
	 * 订单状态:已完成
	 */
	public static final String ORDER_STATUS_FINISH = "finish";
	/**
	 * 订单状态:已作废
	 */
	public static final String ORDER_STATUS_DEAD = "dead";

	/**
	 * 保存订单 固定值
	 */
	/**订单所属人id **/
	public static final Integer SID = 3088;
	/**下单人id **/
	public static final Integer NOWSID = 3088;
	/**下单人no**/
	public static final Integer UNO = 200002;
	/**订单状态 **/
	public static final Integer STATUS = 0;
	/**渠道**/
	public static final Integer ORDERCLASS = 2;
	/**货到付款 **/
	public static final Integer PAYID = 1;
	/**订单所属人部门ID **/
	public static final Integer DEPARTID = 38;
	/**银行标示 **/
	public static final Integer BANKID = 111119;
	/**邮编 **/
	public static final String POSTVAL = "100000";
	
	/**
	 * 接口返回成功
	 */
	public static final String INTERFACE_SUCCEED = "0";
	/**
	 * 接口返回失败
	 */
	public static final String INTERFACE_FAILED = "1";
	//*************用户相关********************************************//
	public static String UPDATE_CUST_SUCCESS_HM101 = "HM101";
	/** 注册失败* */
	public static final String UPDATE_CUST_EXCEPTION_HM107 = "HM107";
	/** 密码修改失败* */
	public static final String UPDATE_CUST_EXCEPTION_HM108 = "HM108";
	/** 密码重置失败* */
	public static final String UPDATE_CUST_EXCEPTION_HM109 = "HM109";
	/** 手机号码已被使用* */
	public static final String UPDATE_CUST_EXCEPTION_HM111 = "HM111";
	/** 更新用户信息异常* */
	public static final String UPDATE_CUST_EXCEPTION_HM112 = "HM112";
	/** 调用比酷根据电话查询用户id异常* */
	public static final String UPDATE_CUST_EXCEPTION_HM113 = "HM113";
	/** 调用比酷根据电话查询电话id异常* */
	public static final String UPDATE_CUST_EXCEPTION_HM114 = "HM114";
	/** 提供的旧电话号码不在已绑定的电话列表中* */
	public static final String UPDATE_CUST_EXCEPTION_HM121 = "HM121";
	/** 调用比酷根据电话查询电话id异常，未查询到电话信息* */
	public static final String UPDATE_CUST_EXCEPTION_HM122 = "HM122";
	/** 未提供旧电话，且客户绑定的主电话在两个或以上，不支持新增电话，请提供旧电话进行修改！* */
	public static final String UPDATE_CUST_EXCEPTION_HM120 = "HM120";
	/** 未能根据提供的网站用户id查询到比酷用户id* */
	public static final String UPDATE_CUST_EXCEPTION_HM115 = "HM115";

	
	/** 调用比酷删除用户电话信息失败* */
	public static final String DEL_CUSTPHONE_EXCEPTION_HM116 = "HM116";
	/** 调用比酷新增电话信息失败* */
	public static final String ADD_PHONE_EXCEPTION_HM117 = "HM117";
	/** 调用比酷添加客户主电话失败* */
	public static final String ADD_PHONE_EXCEPTION_HM118 = "HM118";
	/** 调用比酷根据客户id查询客户电话id列表失败* */
	public static final String ADD_PHONE_EXCEPTION_HM119 = "HM119";
	
	/** 添加客户主电话失败* */
	public static final String ADD_CUST_PHONE_EXCEPTION_HM130 = "HM130";
	/** 添加客户主电话失败,客户未注册至比酷* */
	public static final String ADD_CUST_PHONE_EXCEPTION_HM131 = "HM131";
	/** 添加客户主电话失败,客户已存在两个电话，无法再添加电话* */
	public static final String ADD_CUST_PHONE_EXCEPTION_HM132 = "HM132";
	
	/** 解除网站用用户与ERP用户绑定关系失败* */
	public static final String REMOVE_RELATION_EXCEPTION_HM140 = "HM140";
	
	
	
	//***************调用比酷操作人id   SID******************************************//
	/** 网站在比酷使用的sid操作人id* */
	//public static final Integer CALL_BK_SID_WEBSITE = 14294;
	/** 网站在比酷使用的channel_id* */
	public static final Object CALL_BK_CHANNELID_WEBSITE = 201;
	/** 手机在比酷使用的sid操作人id* */
	//public static final Integer CALL_BK_SID_MOBILE = 44052;
	/** 手机在比酷使用的channel_id* */
	public static final Object CALL_BK_CHANNELID_MOBILE = 204;
	/** 微信在比酷使用的sid操作人id* */
	//public static final Integer CALL_BK_SID_WX = 44050;
	/** 微信在比酷使用的channel_id* */
	public static final Object CALL_BK_CHANNELID_WX = 203;
	
	/**运费产品id**/
	public static final Integer LOGISTICS_PRODUCT_ID = 17088;
	
	/**短信验证码有效时间**/
	public static int SMS_VALID_TIME = 30;
	
	/**调用惠成长后台接口的私钥**/
	public static String PRIVATE_KEY = "私钥";
	
	/**默认北京**/
	public static String PVC_ID = "11";
	
	/**默认北京市**/
	public static String LOCAL_ID = "11021";
	
	/**默认东城区**/
	public static String COUNTRY_ID = "11011";
	
	public static String[] LOGICTIES_ID = {"huitongkuaidi","ems","guotongkuaidi","quanritongkuaidi","sevendays","shunfeng","tiantian","yuntongkuaidi",
		"zhaijisong","zhongyouwuliu","shentong","yuantong","yunda","youzhengguonei","emsguoji","tiandihuayu","lianbangkuaidi","city100","dayangwuliu"}; 
	
	public static String[] LOGICTIES_NAME = {"汇通","EMS","国通","全日通","连锁","顺丰","天天","运通","宅急送","中邮","申通","圆通","韵达","邮政","国际","华宇","联邦","城市一百","大洋"};
	
	/**app页数**/
	public static int PAGESIZE=10;
	
	public static int WEBSIZE=10;
	
	/**网站页数**/
	public static int PAGEWEBSIZE=80;
	
	/**支付方式 货到付款**/
	public static String PAYMENT_OFFLINE="offline";
	
	/**支付方式 在线支付 支付宝**/
	public static String PAYMENT_ALIPAY="alipay";
	
	/**支付方式 在线支付 银联**/
	public static String PAYMENT_UNIONPAY="unionpay";
	
	/**支付方式 在线支付 快钱**/
	public static String PAYMENT_KQPAY="99billpay";
	
	/**第三方登录 微信**/
	public static String THIRD_LOGIN_WX="wx";
	/**第三方登录 qq**/
	public static String THIRD_LOGIN_QQ="qq";
	/**第三方登录 新浪微博**/
	public static String THIRD_LOGIN_XLWB="xlwb";
	//上传图片图路径
	public static final String IMAGE_URL ="http://img.hcz365.com";
	
	//public static final String IMAGE_URL ="http://img.hjk365.com";
	//取消订单开关 false走以前的
	//public static final boolean IVALUE3_FLAG =true;
	
	public static final String HCZ = "hcz";
	public static final String HJK = "hjk";
	public static final String HML = "hml";
	public static final String HSC = "hsc";
	
}


