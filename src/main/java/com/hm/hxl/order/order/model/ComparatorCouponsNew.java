package com.hm.hxl.order.order.model;

import java.io.Serializable;
import java.util.Comparator;

public class ComparatorCouponsNew  implements Comparator<CouponsNew> , Serializable{

	/** 字段描述: **/
	private static final long serialVersionUID = -842434827248461081L;

	@Override
	public int compare(CouponsNew cn1, CouponsNew cn2) {
	//首先比较价格,再次比较优惠券号
		int flag = cn1.getCpns_price().compareTo(cn2.getCpns_price());
		if(flag==0){
			flag = cn1.getCpns_prefix().compareTo(cn2.getCpns_prefix());
//			if(flag<0){
//				flag = Math.abs(flag);
//			}else if(flag>0){
//				flag = 0-flag;
//			}
//		}else if(flag<0){
//			flag = Math.abs(flag);
//		}else{
//			flag = 0-flag;
		}
		return flag;
	}
}
