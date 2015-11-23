package com.hm.hxl.order.order.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.hm.hxl.order.util.Checksum;
import com.hm.hxl.order.util.SystemConfig;

/**
 * 购物车商品信息
 * @author tubingbing
 */
public class CartGoods implements Serializable {
	
	/** 字段描述: **/
	private static final long serialVersionUID = -7134911246448635154L;
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
	private String area_name;
	/**app保税区名字**/
	private String area_app_name;
	/**对象ident**/
	private String obj_ident;
	/**有货无货**/
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
	/**大图片地址**/
	private String l_url;
	/**中图片地址**/
	private String m_url;
	/**小图片地址**/
	private String s_url;
	/**正常图片地址**/
	private String url;
	/**商品编号**/
	private String bn;
	/**购买数量**/
	private String quantity;
	/**是否上架 **/
	private String marketable;
	/**是否失效**/
	private String disabled;
	/**状态**/
	private String status;
	/**状态描述**/
	private String statusDesc;
	/**仓库区分**/
	private String from_sto;
	/** 仓库区分类型 **/
	private String area_type;
	
	private String is_selected;
	
	private String g_price;
	private String order_id;
	private List<CouponsNew> couponsNews = new ArrayList<CouponsNew>();
	
	private BigDecimal pmt = new BigDecimal(0.00);
	private String amount;
	
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public BigDecimal getPmt() {
		return pmt;
	}
	public void setPmt(BigDecimal pmt) {
		this.pmt = pmt;
	}
	
	public List<CouponsNew> getCouponsNews() {
		return couponsNews;
	}
	public void setCouponsNews(List<CouponsNew> couponsNews) {
		this.couponsNews = couponsNews;
	}
	public String getOrder_id() {
		return order_id;
	}
	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}
	/*** 返回: the limitbuy_us_quantity ***/
	public String getLimitbuy_us_quantity() {
		return limitbuy_us_quantity;
	}
	/*** 参数: limitbuy_us_quantity the limitbuy_us_quantity to set ***/
	public void setLimitbuy_us_quantity(String limitbuy_us_quantity) {
		this.limitbuy_us_quantity = limitbuy_us_quantity;
	}
	public String getArea_app_name() {
		return area_app_name;
	}
	public void setArea_app_name(String area_app_name) {
		this.area_app_name = area_app_name;
	}
	/*** 返回: the cost ***/
	public String getCost() {
		return cost;
	}
	/*** 参数: cost the cost to set ***/
	public void setCost(String cost) {
		this.cost = cost;
	}
	/*** 返回: the type_id ***/
	public String getType_id() {
		return type_id;
	}
	/*** 参数: type_id the type_id to set ***/
	public void setType_id(String type_id) {
		this.type_id = type_id;
	}
	/*** 返回: the goods_type ***/
	public String getGoods_type() {
		return goods_type;
	}
	/*** 参数: goods_type the goods_type to set ***/
	public void setGoods_type(String goods_type) {
		this.goods_type = goods_type;
	}
	/*** 返回: the weight ***/
	public String getWeight() {
		return weight;
	}
	/*** 参数: weight the weight to set ***/
	public void setWeight(String weight) {
		this.weight = weight;
	}
	public String getArea_name() {
		return area_name;
	}
	public void setArea_name(String area_name) {
		this.area_name = area_name;
	}
	public String getObj_ident() {
		return obj_ident;
	}
	public void setObj_ident(String obj_ident) {
		this.obj_ident = obj_ident;
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
	/*** 返回: the isBorH ***/
	public String getIsBorH() {
		return isBorH;
	}
	/*** 参数: isBorH the isBorH to set ***/
	public void setIsBorH(String isBorH) {
		this.isBorH = isBorH;
	}
	public String getYwhflag() {
		return ywhflag;
	}
	public void setYwhflag(String ywhflag) {
		this.ywhflag = ywhflag;
	}
	/*** 返回: the area_type ***/
	public String getArea_type() {
		return area_type;
	}
	/*** 参数: area_type the area_type to set ***/
	public void setArea_type(String area_type) {
		this.area_type = area_type;
	}
	/**
	 * @return the biku_id
	 */
	public String getBiku_id() {
		return biku_id;
	}
	/**
	 * @param biku_id the biku_id to set
	 */
	public void setBiku_id(String biku_id) {
		this.biku_id = biku_id;
	}
	/**
	 * @return the goods_id
	 */
	public String getGoods_id() {
		return goods_id;
	}
	/**
	 * @param goods_id the goods_id to set
	 */
	public void setGoods_id(String goods_id) {
		this.goods_id = goods_id;
	}
	/**
	 * @return the product_id
	 */
	public String getProduct_id() {
		return product_id;
	}
	/**
	 * @param product_id the product_id to set
	 */
	public void setProduct_id(String product_id) {
		this.product_id = product_id;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the price
	 */
	public String getPrice() {
		return price;
	}
	/**
	 * @param price the price to set
	 */
	public void setPrice(String price) {
		this.price = price;
	}
	/**
	 * @return the mktprice
	 */
	public String getMktprice() {
		return mktprice;
	}
	/**
	 * @param mktprice the mktprice to set
	 */
	public void setMktprice(String mktprice) {
		this.mktprice = mktprice;
	}
	/**
	 * @return the m_url
	 */
	public String getM_url() {
		return m_url;
	}
	/**
	 * @param m_url the m_url to set
	 */
	public void setM_url(Checksum checkSum,String m_url) {
		if(StringUtils.isBlank(m_url)){
			this.m_url = m_url;
		}else{
			if(m_url.toLowerCase().startsWith("http")){
				this.m_url = m_url;
			}else{
				String hxl_type = checkSum.getHxl_type();
				String webService = new SystemConfig("clientService").getProperty(hxl_type);
				this.m_url=new SystemConfig(webService).getProperty("picture_url")+m_url;
			}
		}
	}
	/**
	 * @return the l_url
	 */
	public String getL_url() {
		return l_url;
	}
	/**
	 * @param l_url the l_url to set
	 */
	public void setL_url(Checksum checkSum,String l_url) {
		if(StringUtils.isBlank(l_url)){
			this.l_url = l_url;
		}else{
			if(l_url.toLowerCase().startsWith("http")){
				this.l_url = l_url;
			}else{
				String hxl_type = checkSum.getHxl_type();
				String webService = new SystemConfig("clientService").getProperty(hxl_type);
				
				this.l_url=new SystemConfig(webService).getProperty("picture_url")+l_url;
			}
		}
	}
	/**
	 * @return the s_url
	 */
	public String getS_url() {
		return s_url;
	}
	/**
	 * @param s_url the s_url to set
	 */
	public void setS_url(Checksum checkSum,String s_url) {
		if(StringUtils.isBlank(s_url)){
			this.s_url = s_url;
		}else{
			if(s_url.toLowerCase().startsWith("http")){
				this.s_url = s_url;
			}else{
				String hxl_type = checkSum.getHxl_type();
				String webService = new SystemConfig("clientService").getProperty(hxl_type);
				this.s_url=new SystemConfig(webService).getProperty("picture_url")+s_url;
			}
		}
	}
	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}
	/**
	 * @param url the url to set
	 */
	public void setUrl(Checksum checkSum,String url) {
		if(StringUtils.isBlank(url)){
			this.url = url;
		}else{
			if(url.toLowerCase().startsWith("http")){
				this.url = url;
			}else{
				String hxl_type = checkSum.getHxl_type();
				String webService = new SystemConfig("clientService").getProperty(hxl_type);
				this.url=new SystemConfig(webService).getProperty("picture_url")+url;
			}
		}
	}
	/**
	 * @return the bn
	 */
	public String getBn() {
		return bn;
	}
	/**
	 * @param bn the bn to set
	 */
	public void setBn(String bn) {
		this.bn = bn;
	}
	/**
	 * @return the quantity
	 */
	public String getQuantity() {
		return quantity;
	}
	/**
	 * @param quantity the quantity to set
	 */
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
	/**
	 * @return the marketable
	 */
	public String getMarketable() {
		return marketable;
	}
	/**
	 * @param marketable the marketable to set
	 */
	public void setMarketable(String marketable) {
		this.marketable = marketable;
	}
	/**
	 * @return the disabled
	 */
	public String getDisabled() {
		return disabled;
	}
	/**
	 * @param disabled the disabled to set
	 */
	public void setDisabled(String disabled) {
		this.disabled = disabled;
	}
	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * @return the statusDesc
	 */
	public String getStatusDesc() {
		return statusDesc;
	}
	/**
	 * @param statusDesc the statusDesc to set
	 */
	public void setStatusDesc(String statusDesc) {
		this.statusDesc = statusDesc;
	}
	/**
	 * @return the from_sto
	 */
	public String getFrom_sto() {
		return from_sto;
	}
	/**
	 * @param from_sto the from_sto to set
	 */
	public void setFrom_sto(String from_sto) {
		this.from_sto = from_sto;
	}
	/**
	 * @return the is_selected
	 */
	public String getIs_selected() {
		return is_selected;
	}
	/**
	 * @param is_selected the is_selected to set
	 */
	public void setIs_selected(String is_selected) {
		this.is_selected = is_selected;
	}
	/**
	 * @return the g_price
	 */
	public String getG_price() {
		return g_price;
	}
	/**
	 * @param g_price the g_price to set
	 */
	public void setG_price(String g_price) {
		this.g_price = g_price;
	}
}
