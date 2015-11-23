package com.hm.hxl.order.order.model;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 订单传入参数
 * @author tubingbing
 */
public class OrderParameter implements Serializable{

	/**
	 * uid
	 */
	private static final long serialVersionUID = -1387163348568156155L;
	
	/**1大陆 2保税 3海外**/
	private String is_overseas;
	
	/**订单编号**/
	private String order_id;
	/**总付金额**/
	private BigDecimal total_amount;
	/**应付金额**/
	private BigDecimal final_amount;
	/**创建时间**/
	private Long createtime;
	/**最后更新时间**/
	private Long last_modified;
	/**支付方式 offline or aipay **/
	private String payment;
	/**用户id**/
	private String member_id;
	/**收获地域**/
	private String ship_area;
	/**收货人姓名**/
	private String ship_name;
	/**重量**/
	private BigDecimal weight = new BigDecimal(0.0);
	/**订单数量**/
	private Integer itemnum;
	/**下单人ip**/
	private String ip;
	/**收货人地址**/
	private String ship_addr;
	/**收货人邮编**/
	private String ship_zip;
	/**收货人电话号码**/
	private String ship_tel;
	/**收货人收货时间**/
	private String ship_time;
	/**收货人手机号码**/
	private String ship_mobile;
	/**商品总金额**/
	private BigDecimal cost_item;
	/**是否需要发票 true or false **/
	private String is_tax= "false";
	/**发票类型 false person company **/
	private String tax_type = "false";
	/**发票单位名称**/
	private String tax_company ="";
	/**获得积分**/
	private BigDecimal score_g;
	/**订单减免**/
	private BigDecimal discount = new BigDecimal(0.0);
	/**商品促销优惠**/
	private BigDecimal pmt_goods = new BigDecimal(0.0);
	/**订单促销优惠**/
	private BigDecimal pmt_order;
	/**线下价格（不收款）**/
	private BigDecimal wipe_price = new BigDecimal(0.0);
	/**在线支付金额**/
	private BigDecimal payed = new BigDecimal(0.0);
	/**物流运费**/
	private BigDecimal cost_freight;
	/**订单分单关联id**/
	private String order_rel ;
	/**收货地址id**/
	private String addr_id;
	/**仓库分单区别**/
	private int from_sto;
	/**商品id**/
	private String goods_id;
	/**货品id**/
	private String product_id;
	
	/**数量**/
	private String quantity;
	
	/**省id**/
	private String pvc_id ;
	/**城市id**/
	private String local_id ;
	/**区县id**/
	private String country_id ;
	/**平台来源 pc:标准平台;wap:手机触屏**/
	private String source;
	/**优惠券号**/
	private String memc_code;
	/** 优惠券类型 1代金券,2满减券 **/
	private String memc_type;
	
	private String ident_code;//身份证号码
	private String ident_code_front;//正面
	private String ident_code_opposite;//反面
	
	public String getIdent_code() {
		return ident_code;
	}
	public void setIdent_code(String ident_code) {
		this.ident_code = ident_code;
	}
	public String getIdent_code_front() {
		return ident_code_front;
	}
	public void setIdent_code_front(String ident_code_front) {
		this.ident_code_front = ident_code_front;
	}
	public String getIdent_code_opposite() {
		return ident_code_opposite;
	}
	public void setIdent_code_opposite(String ident_code_opposite) {
		this.ident_code_opposite = ident_code_opposite;
	}
	public String getMemc_type() {
		return memc_type;
	}
	public void setMemc_type(String memc_type) {
		this.memc_type = memc_type;
	}
	/*** 返回: the is_overseas ***/
	public String getIs_overseas() {
		return is_overseas;
	}
	/*** 参数: is_overseas the is_overseas to set ***/
	public void setIs_overseas(String is_overseas) {
		this.is_overseas = is_overseas;
	}
	
	/**
	 * @return the order_id
	 */
	public String getOrder_id() {
		return order_id;
	}
	/**
	 * @param order_id the order_id to set
	 */
	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}
	/**
	 * @return the total_amount
	 */
	public BigDecimal getTotal_amount() {
		return total_amount;
	}
	/**
	 * @param total_amount the total_amount to set
	 */
	public void setTotal_amount(BigDecimal total_amount) {
		this.total_amount = total_amount;
	}
	/**
	 * @return the final_amount
	 */
	public BigDecimal getFinal_amount() {
		return final_amount;
	}
	/**
	 * @param final_amount the final_amount to set
	 */
	public void setFinal_amount(BigDecimal final_amount) {
		this.final_amount = final_amount;
	}
	/**
	 * @return the createtime
	 */
	public Long getCreatetime() {
		return createtime;
	}
	/**
	 * @param createtime the createtime to set
	 */
	public void setCreatetime(Long createtime) {
		this.createtime = createtime;
	}
	/**
	 * @return the last_modified
	 */
	public Long getLast_modified() {
		return last_modified;
	}
	/**
	 * @param last_modified the last_modified to set
	 */
	public void setLast_modified(Long last_modified) {
		this.last_modified = last_modified;
	}
	/**
	 * @return the payment
	 */
	public String getPayment() {
		return payment;
	}
	/**
	 * @param payment the payment to set
	 */
	public void setPayment(String payment) {
		this.payment = payment;
	}
	/**
	 * @return the member_id
	 */
	public String getMember_id() {
		return member_id;
	}
	/**
	 * @param member_id the member_id to set
	 */
	public void setMember_id(String member_id) {
		this.member_id = member_id;
	}
	/**
	 * @return the ship_area
	 */
	public String getShip_area() {
		return ship_area;
	}
	/**
	 * @param ship_area the ship_area to set
	 */
	public void setShip_area(String ship_area) {
		this.ship_area = ship_area;
	}
	/**
	 * @return the ship_name
	 */
	public String getShip_name() {
		return ship_name;
	}
	/**
	 * @param ship_name the ship_name to set
	 */
	public void setShip_name(String ship_name) {
		this.ship_name = ship_name;
	}
	/**
	 * @return the weight
	 */
	public BigDecimal getWeight() {
		return weight;
	}
	/**
	 * @param weight the weight to set
	 */
	public void setWeight(BigDecimal weight) {
		this.weight = weight;
	}
	/**
	 * @return the itemnum
	 */
	public Integer getItemnum() {
		return itemnum;
	}
	/**
	 * @param itemnum the itemnum to set
	 */
	public void setItemnum(Integer itemnum) {
		this.itemnum = itemnum;
	}
	/**
	 * @return the ip
	 */
	public String getIp() {
		return ip;
	}
	/**
	 * @param ip the ip to set
	 */
	public void setIp(String ip) {
		this.ip = ip;
	}
	/**
	 * @return the ship_addr
	 */
	public String getShip_addr() {
		return ship_addr;
	}
	/**
	 * @param ship_addr the ship_addr to set
	 */
	public void setShip_addr(String ship_addr) {
		this.ship_addr = ship_addr;
	}
	/**
	 * @return the ship_zip
	 */
	public String getShip_zip() {
		return ship_zip;
	}
	/**
	 * @param ship_zip the ship_zip to set
	 */
	public void setShip_zip(String ship_zip) {
		this.ship_zip = ship_zip;
	}
	/**
	 * @return the ship_tel
	 */
	public String getShip_tel() {
		return ship_tel;
	}
	/**
	 * @param ship_tel the ship_tel to set
	 */
	public void setShip_tel(String ship_tel) {
		this.ship_tel = ship_tel;
	}
	/**
	 * @return the ship_time
	 */
	public String getShip_time() {
		return ship_time;
	}
	/**
	 * @param ship_time the ship_time to set
	 */
	public void setShip_time(String ship_time) {
		this.ship_time = ship_time;
	}
	/**
	 * @return the ship_mobile
	 */
	public String getShip_mobile() {
		return ship_mobile;
	}
	/**
	 * @param ship_mobile the ship_mobile to set
	 */
	public void setShip_mobile(String ship_mobile) {
		this.ship_mobile = ship_mobile;
	}
	/**
	 * @return the cost_item
	 */
	public BigDecimal getCost_item() {
		return cost_item;
	}
	/**
	 * @param cost_item the cost_item to set
	 */
	public void setCost_item(BigDecimal cost_item) {
		this.cost_item = cost_item;
	}
	/**
	 * @return the is_tax
	 */
	public String getIs_tax() {
		return is_tax;
	}
	/**
	 * @param is_tax the is_tax to set
	 */
	public void setIs_tax(String is_tax) {
		this.is_tax = is_tax;
	}
	/**
	 * @return the tax_type
	 */
	public String getTax_type() {
		return tax_type;
	}
	/**
	 * @param tax_type the tax_type to set
	 */
	public void setTax_type(String tax_type) {
		this.tax_type = tax_type;
	}
	/**
	 * @return the tax_company
	 */
	public String getTax_company() {
		return tax_company;
	}
	/**
	 * @param tax_company the tax_company to set
	 */
	public void setTax_company(String tax_company) {
		this.tax_company = tax_company;
	}
	/**
	 * @return the score_g
	 */
	public BigDecimal getScore_g() {
		return score_g;
	}
	/**
	 * @param score_g the score_g to set
	 */
	public void setScore_g(BigDecimal score_g) {
		this.score_g = score_g;
	}
	/**
	 * @return the discount
	 */
	public BigDecimal getDiscount() {
		return discount;
	}
	/**
	 * @param discount the discount to set
	 */
	public void setDiscount(BigDecimal discount) {
		this.discount = discount;
	}
	/**
	 * @return the pmt_goods
	 */
	public BigDecimal getPmt_goods() {
		return pmt_goods;
	}
	/**
	 * @param pmt_goods the pmt_goods to set
	 */
	public void setPmt_goods(BigDecimal pmt_goods) {
		this.pmt_goods = pmt_goods;
	}
	/**
	 * @return the pmt_order
	 */
	public BigDecimal getPmt_order() {
		return pmt_order;
	}
	/**
	 * @param pmt_order the pmt_order to set
	 */
	public void setPmt_order(BigDecimal pmt_order) {
		this.pmt_order = pmt_order;
	}
	/**
	 * @return the wipe_price
	 */
	public BigDecimal getWipe_price() {
		return wipe_price;
	}
	/**
	 * @param wipe_price the wipe_price to set
	 */
	public void setWipe_price(BigDecimal wipe_price) {
		this.wipe_price = wipe_price;
	}
	/**
	 * @return the payed
	 */
	public BigDecimal getPayed() {
		return payed;
	}
	/**
	 * @param payed the payed to set
	 */
	public void setPayed(BigDecimal payed) {
		this.payed = payed;
	}
	/**
	 * @return the cost_freight
	 */
	public BigDecimal getCost_freight() {
		return cost_freight;
	}
	/**
	 * @param cost_freight the cost_freight to set
	 */
	public void setCost_freight(BigDecimal cost_freight) {
		this.cost_freight = cost_freight;
	}
	/**
	 * @return the order_rel
	 */
	public String getOrder_rel() {
		return order_rel;
	}
	/**
	 * @param order_rel the order_rel to set
	 */
	public void setOrder_rel(String order_rel) {
		this.order_rel = order_rel;
	}
	/**
	 * @return the addr_id
	 */
	public String getAddr_id() {
		return addr_id;
	}
	/**
	 * @param addr_id the addr_id to set
	 */
	public void setAddr_id(String addr_id) {
		this.addr_id = addr_id;
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
	 * @return the pvc_id
	 */
	public String getPvc_id() {
		return pvc_id;
	}
	/**
	 * @param pvc_id the pvc_id to set
	 */
	public void setPvc_id(String pvc_id) {
		this.pvc_id = pvc_id;
	}
	/**
	 * @return the local_id
	 */
	public String getLocal_id() {
		return local_id;
	}
	/**
	 * @param local_id the local_id to set
	 */
	public void setLocal_id(String local_id) {
		this.local_id = local_id;
	}
	/**
	 * @return the country_id
	 */
	public String getCountry_id() {
		return country_id;
	}
	/**
	 * @param country_id the country_id to set
	 */
	public void setCountry_id(String country_id) {
		this.country_id = country_id;
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
	 * @return the from_sto
	 */
	public int getFrom_sto() {
		return from_sto;
	}
	/**
	 * @param from_sto the from_sto to set
	 */
	public void setFrom_sto(int from_sto) {
		this.from_sto = from_sto;
	}
	/**
	 * @return the source
	 */
	public String getSource() {
		return source;
	}
	/**
	 * @param source the source to set
	 */
	public void setSource(String source) {
		this.source = source;
	}
	/**
	 * @return the mem_code
	 */
	public String getMemc_code() {
		return memc_code;
	}
	/**
	 * @param mem_code the mem_code to set
	 */
	public void setMemc_code(String memc_code) {
		this.memc_code = memc_code;
	}

	
	
}
