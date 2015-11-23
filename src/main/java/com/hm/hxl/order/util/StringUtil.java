/**
 * 项目名: hjkClient
 * 文件名: StringUtil.java
 * Copyright 2014 北京惠买在线网络科技有限公司 
 */
package com.hm.hxl.order.util;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;


/**
 * 
 * <p>Title: StringUtil.java</p>
 * <p>Description: 对字符串的类型转化或处理相关的操作</p>
 * @version 1.0 
 *
 */
@SuppressWarnings("rawtypes")
public class StringUtil {

	/**默认字符串正则式**/
	private static final String DEFAULT_FILTERED_CHAR = "`~\\:;\"'<,>./";
	
	/**
	 * 判断字符串是否为空
	 * 
	 * @param str
	 * @return boolean
	 * 
	 */
	public static boolean isEmpty(Object str) {
		return (str == null || (String.valueOf(str)).trim().length() < 1);
	}

	/**
	 * 判断字符串是否为非空
	 * 
	 * @param str
	 * @return boolean
	 * 
	 */
	public static boolean isNotEmpty(Object str) {
		return !isEmpty(str);
	}

	/**
	 * 判断字符串是否为空
	 * 
	 * @param str
	 * @return boolean
	 * 
	 */
	public static boolean isEmpty(String str) {
		return (str == null || str.trim().length() < 1);
	}

	/**
	 * 判断字符串是否为非空
	 * 
	 * @param str
	 * @return boolean
	 * 
	 */
	public static boolean isNotEmpty(String str) {
		return !isEmpty(str);
	}
	
	/**
	 * Object转化为字符串
	 * 
	 * @param obj
	 * @return String
	 * 
	 */
	public static String obj2Str(Object obj) {
		return obj == null ? "" : obj.toString().trim();
	}
	
	/**
	 * Object转化为字符串，设置默认值，如果为空返回默认值
	 * @param obj		对象
	 * @param defaultValue	默认值
	 * @return
	 * String
	 *
	 */
	public static String obj2Str(Object obj, String defaultValue) {
		return obj == null ? defaultValue : obj.toString().trim();
	}

	/**
	 * 字符串转化为Integer类型
	 * 
	 * @param str
	 * @return Integer
	 * 
	 */
	public static Integer string2Integer(String str) {
		if (isNotEmpty(str)) {
			try {
				return new Integer(str.trim());
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	/**
	 * 字符串转化为Integer类型,为空则返回默认值
	 * @param str
	 * @param defaultValue	默认值
	 * @return
	 * Integer
	 *
	 */
	public static Integer string2Integer(String str, Integer defaultValue) {
		if (isNotEmpty(str)) {
			try {
				return new Integer(str.trim());
			} catch (NumberFormatException e) {
				e.printStackTrace();
				return defaultValue;
			}
		}
		return defaultValue;
	}

	/**
	 * 字符串转化为Long类型
	 * 
	 * @param str
	 * @return Long
	 * 
	 */
	public static Long string2Long(String str) {
		if (isNotEmpty(str)) {
			try {
				return new Long(str.trim());
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	/**
	 * 
	 * 字符串转化为Long类型,为空则返回默认值
	 * @param str
	 * @param defaultValue	默认值
	 * @return
	 * Long
	 *
	 */
	public static Long string2Long(String str, Long defaultValue) {
		if (isNotEmpty(str)) {
			try {
				return new Long(str.trim());
			} catch (NumberFormatException e) {
				e.printStackTrace();
				return defaultValue;
			}
		}
		return defaultValue;
	}

	/**
	 * 字符串转化为Double类型
	 * 
	 * @param str
	 * @return Double
	 * 
	 */
	public static Double stringToDouble(String str) {
		if (isNotEmpty(str)) {
			try {
				return new Double(str.trim());
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	/**
	 * 
	 * 字符串转化为Double类型,为空则返回默认值
	 * @param str
	 * @param defaultValue	默认值
	 * @return
	 * Double
	 *
	 */
	public static Double stringToDouble(String str, Double defaultValue) {
		if (isNotEmpty(str)) {
			try {
				return new Double(str.trim());
			} catch (NumberFormatException e) {
				e.printStackTrace();
				return defaultValue;
			}
		}
		return defaultValue;
	}

	/**
	 * 字符串转化为BigDecimal类型
	 * 
	 * @param str
	 * @return BigDecimal
	 * 
	 */
	public static BigDecimal string2BigDecimal(String str) {
		if (isNotEmpty(str)) {
			try {
				return new BigDecimal(str);
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	/**
	 * 
	 * 字符串转化为BigDecimal类型,为空则返回默认值
	 * @param str
	 * @param defaultValue	默认值
	 * @return
	 * BigDecimal
	 *
	 */
	public static BigDecimal string2BigDecimal(String str,BigDecimal defaultValue) {
		if (isNotEmpty(str)) {
			try {
				return new BigDecimal(str);
			} catch (NumberFormatException e) {
				e.printStackTrace();
				return defaultValue;
			}
		}
		return defaultValue;
	}

	/**
	 * 判断字符串是否为数字
	 * 
	 * @param str
	 * @return boolean
	 * 
	 */
	public static boolean isDecimal(String str) {
		boolean res = true;
		if (isEmpty(str)) {
			return false;
		}
		try {
			new BigDecimal(str);
		} catch (NumberFormatException e) {
			res = false;
		}
		return res;
	}
	
	/**
	 * 
	 * 将字符串格式化为数字形式的字符串
	 * @param value	初始字符串
	 * @return
	 * String	数字形式的字符串
	 *
	 */
	public static String numberFormat(String value) {
		if (!isEmpty(value)) {
			NumberFormat format = NumberFormat.getInstance();
			return format.format(Double.parseDouble(value));
		}
		return null;
	}
	
	
	/**
	 * 判断是否是中文字符串
	 * 
	 * @param str
	 * @return
	 * boolean
	 *
	 */
	public static boolean isChinese(String str) {
		if(isEmpty(str)) {
			return false;
		}
		char[] ch = str.toCharArray();
		for (int i = 0; i < ch.length; i++) {
			char c = ch[i];
			if (isChinese(c) == true) {
				return true;
			}
		}
		return false;
	}
	
	public static boolean isChinese(char c) {
		Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
		if ((ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS)
				|| (ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS)
				|| (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A)
				|| (ub == Character.UnicodeBlock.GENERAL_PUNCTUATION)
				|| (ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION)
				|| (ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS)) {
			return true;
		}
		return false;
	}
	
	/**
	 * 比较两个字符串是否相等
	 * @param str1
	 * @param str2
	 * @return
	 * boolean
	 *
	 */
	public static boolean stringEquals(String str1, String str2) {
		if (isEmpty(str1) && isEmpty(str2)) {
			return true;
		}
		// str1不等于null
		if (isNotEmpty(str1)) {
			return str1.equals(str2);
		}
		// str2肯定不为null
		return false;
	}

	/**
	 * 
	 * 将字符串数组转换成用逗号分割的字符串
	 * @param strArray	字符串数组
	 * @return 以逗号分隔的字符串
	 *
	 */
	public static String array2Str(Object[] strArray) {
		String str = "";
		for (int i = 0; i < strArray.length; i++) {
			str += strArray[i].toString() + ",";
		}
		if (str.length() > 0)
			str = str.substring(0, str.length() - 1);
		return str;
	}
	

	/**
	 * 
	 * 去除字符串间的空格
	 * @param s
	 * @return
	 * String
	 *
	 */
	public static String replaceAllBlank(String s) {
		if (isEmpty(s)) {
			return "";
		}
		return s.replaceAll("\\s", "");
	}
	
	/**
	 * 字符串替换，将 source 中的 oldString 全部换成 newString
	 * @param source 源字符串
	 * @param oldString 老的字符串
	 * @param newString 新的字符串
	 * @return 替换后的字符串
	 */
	public static String replaceAll(String source, String oldString, String newString) {
		if ( (source == null) || (source.equals("")) ) {
			return "";
		}
		StringBuffer output = new StringBuffer();

		int lengthOfSource = source.length();   // 源字符串长度
		int lengthOfOld = oldString.length();   // 老字符串长度

		int posStart = 0;   // 开始搜索位置
		int pos;            // 搜索到老字符串的位置

		while ((pos = source.indexOf(oldString, posStart)) >= 0) {
			output.append(source.substring(posStart, pos));

			output.append(newString);
			posStart = pos + lengthOfOld;
		}

		if (posStart < lengthOfSource) {
			output.append(source.substring(posStart));
		}

		return output.toString();
	}

	/**
	 * 将字符串数组转化为以逗号分隔的字符串
	 * @param s
	 * @return
	 * String	逗号分隔的字符串
	 *
	 */
	public static String arr2CommaString(String[] s) {
		if (s == null || s.length < 1) {
			return "";
		}
		String result = s[0];
		if (s.length > 1) {
			for (int i = 1; i < s.length; i++) {
				result += ("," + s[i]);
			}
		}
		return result;
	}

	/**
	 * 构造字符串，将列表中的字符串，用分隔符连成一个字符串
	 * 
	 * @param list
	 *            List 字符串（String）列表
	 * @param splitStr
	 *            String 分隔符
	 * @return String
	 */
	public static String list2StringWithSplit(List list, String splitStr) {
		if (list == null || list.size() < 1)
			return null;

		StringBuffer buf = new StringBuffer();
		Iterator iter = list.iterator();
		while (iter.hasNext()) {
			buf.append(splitStr);
			buf.append((String) iter.next());
		}
		return buf.toString().substring(1);
	}

	/**
	 * 构造字符串，将列表中的字符串，用分隔符连成一个字符串 每个字符串加上单引号 查询DATABASE用
	 * 
	 * @param list  Collection
	 * @param splitStr
	 *            String
	 * @return String
	 */
	public static String list2StringWithComma(Collection list, String splitStr) {
		if (list == null || list.size() < 1)
			return null;

		StringBuffer buf = new StringBuffer();
		Iterator iter = list.iterator();
		while (iter.hasNext()) {
			buf.append(splitStr);
			buf.append("'");
			buf.append((String) iter.next());
			buf.append("'");
		}

		return buf.toString().substring(1);
	}
	
	/**
	 * 删除某个字串中需要被过滤的字符 
	 * 
	 * @param src
	 * @param filterChar
	 * @return
	 */
	public static String filterStr(String src, String filterChar) {
		if (isEmpty(src))
			return "";
		src = src.trim();
		if (filterChar == null || filterChar.length() < 0) {
			filterChar = DEFAULT_FILTERED_CHAR;
		}
		int len = filterChar.length();
		for (int i = 0; i < len; i++) {
			src = src.replaceAll("\\" + String.valueOf(filterChar.charAt(i)), "");
		}
		return src;
	}



	/**
	 * 
	 * 将多个list中的数据合并,剔除重复的数据
	 * @param lists
	 * @return
	 * List
	 *
	 */
	@SuppressWarnings({ "unchecked" })
	public static List<Object> getDistinctList(List[] lists) {
		List<Object> retList = new ArrayList<Object>();
		Map<Object,Object> map = new HashMap<Object,Object>();
		for (int i = 0; i < lists.length; i++) {
			List<Object> list = (List) lists[i];
			if(list==null) {
				continue;
			}
			for (int j = 0; j < list.size(); j++) {
				if (list.get(j) != null) {
					map.put(list.get(j), list.get(j));
				}
			}
		}
		Iterator<Object> it = map.keySet().iterator();
		while (it.hasNext()) {
			retList.add(it.next());
		}
		return retList;
	}


	/**
	 * 
	 * 根据身份证号码获取性别(返回值：1－男，2－女，空为身份证号码错误)
	 * 
	 * @param iDCard
	 *            身份证号
	 * @return String 返回值：1－男，2－女，空为身份证号码错误
	 * 
	 */
	public static String getGender(String iDCard) {
		int gender = 3;
		if (iDCard.length() == 15) {
			gender = (new Integer(iDCard.substring(14, 15))).intValue() % 2;
		} else if (iDCard.length() == 18) {
			int number17 = (new Integer(iDCard.substring(16, 17))).intValue();
			gender = number17 % 2;
		}
		if (gender == 1) {
			return "1";
		} else if (gender == 0) {
			return "2";
		} else {
			return "";
		}
	}

	/**
	 * 检测是否为合法的E-MAIL格式
	 * 
	 * @param emailStr	
	 * @return boolean
	 * 
	 */
	public static boolean checkEmail(String emailStr) {
		if(isEmpty(emailStr)) {
			return false;
		}
		return emailStr.matches("[-_.a-zA-Z0-9]+@[-_a-zA-Z0-9]+.[a-zA-Z]+");
	}

	/**
	 * 检测是否是字母,数字,下划线
	 * 
	 * @param v
	 * @return boolean
	 * 
	 */
	public static boolean checkStr(String v) {
		if(isEmpty(v)) {
			return false;
		}
		return v.matches("[a-zA-Z0-9_]*");
	}
	
	/**
	 * 将字符串进行MD5加密,空字符串直接返回0长度字符串
	 * @param value
	 * @return
	 * @throws Exception
	 * String	加密后的字符串
	 *
	 */
	public static String encryptMD5(String value) throws Exception {
		String result = "";
		if (isEmpty(value)) {
			return "";
		}
		try {
			MessageDigest messageDigest = MessageDigest.getInstance("MD5");
			messageDigest.update(value.getBytes());
			result = byte2hex(messageDigest.digest());
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
		return result;
	}

	/**
	 * 2进制转16进制
	 * @param bytes
	 * @return String
	 */
	private static String byte2hex(byte[] bytes) {
		String result = "";
		String stmp = "";
		for (int n = 0; n < bytes.length; n++) {
			stmp = (java.lang.Integer.toHexString(bytes[n] & 0xFF));
			if (stmp.length() == 1) {
				result = result + "0" + stmp;
			} else {
				result = result + stmp;
			}
		}
		return result.toUpperCase();
	}
	
	
	/**
	 * 获取uuid
	 * @return uuid
	 */
	public static String getUuid() {
		String uuid = UUID.randomUUID().toString();
		uuid = uuid.replace("-", "");
		return uuid;
	}
	 
	/**
	 * 字符串编码格式转成utf-8
	 * @param code
	 * @return String
	 */
	public static String stringEncode( String code ) {
		try {
			return new String(code.getBytes(),"utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}
	
	/**
	 * url路径编码格式转成utf-8格式
	 * @param code
	 * @return String
	 */
	public static String urlEncode( String code ) {
		try {
			return URLEncoder.encode( code,"utf-8" );
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}
	
	/**
	 * url路径按utf-8转码
	 * @param code
	 * @return String
	 */
	public static String urlDecode( String code ) {
		if(code==null)return "";
		try {
			return URLDecoder.decode( code, "utf-8" );
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}
	/** 
     * 把中文转成Unicode码 
     * @param str 
     * @return 
     */  
    public static String chinaToUnicode(String str){  
        String result="";  
        try{
        	for (int i = 0; i < str.length(); i++){  
                int chr1 = (char) str.charAt(i);  
                if(chr1 >=32 && chr1 <=126){//assic码字符区间，包含数字字母符号空格，除此之外的一律转换为unicode字符
                    result+=str.charAt(i);  
                }else{  
                	result+="\\u" + Integer.toHexString(chr1);  
                } 
            }  
        }catch(Exception e){
        	e.printStackTrace();
        }
        return result;  
    }
    /**
     * 是否在中文符号范围内
     * @author zhuyuxin
     *
     * @param a
     * @return
     * createTime : 2014年7月17日 上午10:13:35
     */
    public static boolean isChineseF(char a){
    	int i = a;
    	if(i==12290//    	。=12290
    			|| i == 65292//，=65292
    			|| i == 65311//？=
    			|| i == 65281//！=
    			|| i == 12289//、=
    			|| i == 65307//；=
    			|| i == 65306//：=
    			|| i == 12300//「=
    			|| i == 12301//」=
    			|| i == 12302//『=
    			|| i == 12303//』=
    			|| i == 8216//‘=
    			|| i == 8217//’=
    			|| i == 8220//“=
    			|| i == 8221//”=
    			|| i == 65288//（=
    			|| i == 65289//）=
    			|| i == 12308//〔=
    			|| i == 12309//〕=
    			|| i == 12304//【=
    			|| i == 12305//】=
    			|| i == 8212//—=
    			|| i == 8230//…=
    			|| i == 8211//–=
    			|| i == 65294//．=
    			|| i == 12298//《=
    			|| i == 12299//》=
    			|| i == 12296//〈
    			|| i == 12297//〉
    			|| i == 12288//"　"中文全角空格 
    			|| i == 65340//＼
    			){
    		return true;
    	}else{
    		return false;
    	}
    		
    }
    /**
     * Unicode码转中文字符串
     * @param str
     * @return
     * createTime : 2014年7月7日 下午5:22:49
     */
  	 public static String unicodeTochina(String str) {   
  	        StringBuilder sb = new StringBuilder();   
  	        int begin = 0;   
  	        int index = str.indexOf("\\u");   
  	        while (index != -1) {   
  	            sb.append(str.substring(begin, index));   
  	            sb.append(ascii2Char(str.substring(index, index + 6)));   
  	            begin = index + 6;   
  	            index = str.indexOf("\\u", begin);   
  	        }   
  	        sb.append(str.substring(begin));   
  	        return sb.toString();   
  	    }
  	 private static char ascii2Char(String str) {   
          if (str.length() != 6) {   
              throw new IllegalArgumentException(   
                      "Ascii string of a native character must be 6 character.");   
          }   
          if (!"\\u".equals(str.substring(0, 2))) {   
              throw new IllegalArgumentException(   
                      "Ascii string of a native character must start with \"\\u\".");   
          }   
          String tmp = str.substring(2, 4);   
          int code = Integer.parseInt(tmp, 16) << 8;   
          tmp = str.substring(4, 6);   
          code += Integer.parseInt(tmp, 16);   
          return (char) code;   
      }
 	public static String ascii2native(String ascii) {
		int n = ascii.length() / 6;
		StringBuilder sb = new StringBuilder(n);
		for (int i = 0, j = 2; i < n; i++, j += 6) {
			String code = ascii.substring(j, j + 4);
			char ch = (char) Integer.parseInt(code, 16);
			sb.append(ch);
		}
		return sb.toString();
	}
 	 
	/**
	 * 字符串编码格式转成utf-8
	 * @param code
	 * @return String
	 */
	public static String stringfromIsoToUtf8( String code ) {
		try {
			return new String(code.getBytes("ISO-8859-1"),"utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}
	
	/**
	 * 字符串编码格式转成iso-8859-1
	 * @param code
	 * @return String
	 */
	public static String stringfromUtf8Toiso( String code ) {
		try {
			return new String(code.getBytes("UTF-8"),"ISO-8859-1");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}
}
