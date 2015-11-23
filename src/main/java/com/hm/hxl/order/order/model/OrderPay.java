package com.hm.hxl.order.order.model;

import java.io.Serializable;

public class OrderPay  implements Serializable{

	/** 字段描述: **/
	private static final long serialVersionUID = -1517721817247684262L;
	/**已占数量**/
	private String limitbuy_us_quantity;
	/**货品成本**/
	private String cost;
	/**货品类型 product or gift **/
	private String type_id;
	/**订单商品类型 goods or gift **/
	private String goods_type;
	/**商品总重量**/
	private String weight;
	/**总剩余量**/
	private String limitbuy_quantity;
	/**每人限购数量**/
	private String limitbuy_sg_quantity;
	/**商品状态（1，特卖，2，预售，3下架）**/
	private String goodsflag;
	/**是否是保税区或海外商品**/
	private String isBorH;//2,3-->保税,海外
	/**保税区名字**/
	private String area_name;//'跨境保税' '海外直邮' '大陆仓储'
	/**app保税区名字**/
	private String area_app_name;
	/**限购有货无货**/
	private String ywhflag;
	/**比酷id**/
	private String biku_id;
	/**商品id**/
	private String goods_id;
	/**货品id**/
	private String product_id;
	/**商品名称**/
	private String name;
	/**惠买价格**/
	private String price;
	/**市场价格**/
	private String mktprice;
	/**商品编号**/
	private String bn;
	/**是否上架 **/
	private String marketable;
	/**是否失效**/
	private String disabled;
	/**仓库区分**/
	private String from_sto;
	/** 仓库区分类型 **/
	private String area_type;
	//销售价格
	private String g_price;
	//订单号
	private String order_id;
	//数量
    private String quantity;
    //原支付价格
    private String y_price;
    //原销售价格
    private String y_g_price;
    //原订单是否特卖0非特卖1特卖
    private String is_limitbuy;
    //是否压单 0 没压1压单本地2压单比酷
    private String used_store;
    //订单商品主键
    private String obj_id;
    //订单货品主键
    private String item_id;
    //优惠金额
    private String pmt_price;
    
	public String getPmt_price() {
		return pmt_price;
	}
	public void setPmt_price(String pmt_price) {
		this.pmt_price = pmt_price;
	}
    
	/*** 返回: the is_limitbuy ***/
	public String getIs_limitbuy() {
		return is_limitbuy;
	}
	/*** 参数: is_limitbuy the is_limitbuy to set ***/
	public void setIs_limitbuy(String is_limitbuy) {
		this.is_limitbuy = is_limitbuy;
	}
	/*** 返回: the used_store ***/
	public String getUsed_store() {
		return used_store;
	}
	/*** 参数: used_store the used_store to set ***/
	public void setUsed_store(String used_store) {
		this.used_store = used_store;
	}
	public String getLimitbuy_us_quantity() {
		return limitbuy_us_quantity;
	}
	public void setLimitbuy_us_quantity(String limitbuy_us_quantity) {
		this.limitbuy_us_quantity = limitbuy_us_quantity;
	}
	public String getCost() {
		return cost;
	}
	public void setCost(String cost) {
		this.cost = cost;
	}
	public String getType_id() {
		return type_id;
	}
	public void setType_id(String type_id) {
		this.type_id = type_id;
	}
	public String getGoods_type() {
		return goods_type;
	}
	public void setGoods_type(String goods_type) {
		this.goods_type = goods_type;
	}
	public String getWeight() {
		return weight;
	}
	public void setWeight(String weight) {
		this.weight = weight;
	}
	public String getLimitbuy_quantity() {
		return limitbuy_quantity;
	}
	public void setLimitbuy_quantity(String limitbuy_quantity) {
		this.limitbuy_quantity = limitbuy_quantity;
	}
	public String getLimitbuy_sg_quantity() {
		return limitbuy_sg_quantity;
	}
	public void setLimitbuy_sg_quantity(String limitbuy_sg_quantity) {
		this.limitbuy_sg_quantity = limitbuy_sg_quantity;
	}
	public String getGoodsflag() {
		return goodsflag;
	}
	public void setGoodsflag(String goodsflag) {
		this.goodsflag = goodsflag;
	}
	public String getIsBorH() {
		return isBorH;
	}
	public void setIsBorH(String isBorH) {
		this.isBorH = isBorH;
	}
	public String getArea_name() {
		return area_name;
	}
	public void setArea_name(String area_name) {
		this.area_name = area_name;
	}
	public String getArea_app_name() {
		return area_app_name;
	}
	public void setArea_app_name(String area_app_name) {
		this.area_app_name = area_app_name;
	}
	public String getYwhflag() {
		return ywhflag;
	}
	public void setYwhflag(String ywhflag) {
		this.ywhflag = ywhflag;
	}
	public String getBiku_id() {
		return biku_id;
	}
	public void setBiku_id(String biku_id) {
		this.biku_id = biku_id;
	}
	public String getGoods_id() {
		return goods_id;
	}
	public void setGoods_id(String goods_id) {
		this.goods_id = goods_id;
	}
	public String getProduct_id() {
		return product_id;
	}
	public void setProduct_id(String product_id) {
		this.product_id = product_id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getMktprice() {
		return mktprice;
	}
	public void setMktprice(String mktprice) {
		this.mktprice = mktprice;
	}
	public String getBn() {
		return bn;
	}
	public void setBn(String bn) {
		this.bn = bn;
	}
	public String getMarketable() {
		return marketable;
	}
	public void setMarketable(String marketable) {
		this.marketable = marketable;
	}
	public String getDisabled() {
		return disabled;
	}
	public void setDisabled(String disabled) {
		this.disabled = disabled;
	}
	public String getFrom_sto() {
		return from_sto;
	}
	public void setFrom_sto(String from_sto) {
		this.from_sto = from_sto;
	}
	public String getArea_type() {
		return area_type;
	}
	public void setArea_type(String area_type) {
		this.area_type = area_type;
	}
	public String getG_price() {
		return g_price;
	}
	public void setG_price(String g_price) {
		this.g_price = g_price;
	}
	public String getOrder_id() {
		return order_id;
	}
	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}
	public String getQuantity() {
		return quantity;
	}
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
	public String getY_price() {
		return y_price;
	}
	public void setY_price(String y_price) {
		this.y_price = y_price;
	}
	public String getY_g_price() {
		return y_g_price;
	}
	public void setY_g_price(String y_g_price) {
		this.y_g_price = y_g_price;
	}
	public String getObj_id() {
		return obj_id;
	}
	public void setObj_id(String obj_id) {
		this.obj_id = obj_id;
	}
	public String getItem_id() {
		return item_id;
	}
	public void setItem_id(String item_id) {
		this.item_id = item_id;
	}
	
}
