/**
 * 项目名: hjkClient
 * 文件名: HjkRPCUtil.java
 * Copyright 2014 北京惠买在线网络科技有限公司 
 */
package com.hm.hxl.order.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


/**
 * 类名: Md5 
 * 作用: MD5加密工具类
 * 作者: yanpengjie 
 * 日期: 2015-7-17 下午12:50:47 
 * 版本: V 1.0
 *
 */
public class Md5 {

	/**
	 * md5加密
	 * @param plainText
	 * @return 秘钥
	 */
	public static String encipher( String plainText ) {
		try {
			MessageDigest md = MessageDigest.getInstance( "MD5" );
			md.update( plainText.getBytes("UTF-8") );
			byte b[] = md.digest();

			int i;

			StringBuffer buf = new StringBuffer("");
			for (int offset = 0; offset < b.length; offset++) {
				i = b[offset];
				if (i < 0)
					i += 256;
				if (i < 16)
					buf.append("0");
				buf.append(Integer.toHexString(i));
			}
			return buf.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
}
