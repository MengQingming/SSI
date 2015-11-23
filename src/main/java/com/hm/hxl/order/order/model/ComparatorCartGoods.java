package com.hm.hxl.order.order.model;

import java.io.Serializable;
import java.util.Comparator;


public class ComparatorCartGoods  implements Comparator<CartGoods>,  Serializable{
	/** 字段描述: **/
	private static final long serialVersionUID = -1535801445307210090L;

	@Override
	public int compare(CartGoods cg1, CartGoods cg2) {
		//首先比较价格再比较商品id
		String price1 = cg1.getPrice();
		String price2 = cg2.getPrice();
		double quantity1 = Double.parseDouble(cg1.getQuantity());
		double aprice1 = (Double.parseDouble(price1)*quantity1);
		double quantity2 = Double.parseDouble(cg2.getQuantity());
		double aprice2 = (Double.parseDouble(price2)*quantity2);
		if(aprice1>aprice2){
			return -1;
		}else if(aprice1<aprice2){
			return 1;
		}else{
			return 0;
		}
	}
}