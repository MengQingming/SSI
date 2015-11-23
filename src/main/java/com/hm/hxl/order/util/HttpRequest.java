package com.hm.hxl.order.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import org.apache.commons.lang.StringUtils;

public class HttpRequest{
    /**
     * 向指定URL发送GET方法的请求
     * 
     * @param url
     *            发送请求的URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return URL 所代表远程资源的响应结果
     */
    public static String sendGet(String url, String param) {
        String result = "";
        BufferedReader in = null;
        try {
            String urlNameString = url + ((param==null||param.equals(""))?"":"?" + param);
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            //connection.setRequestProperty("accept", "*/*");
           /* connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");*/
            // 建立实际的连接
            connection.connect();
            // 获取所有响应头字段
           /* Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段
            for (String key : map.keySet()) {
                System.out.println(key + "--->" + map.get(key));
            }*/
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(connection.getInputStream(),"UTF-8"));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally { // 使用finally块来关闭输入流
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        if(StringUtils.isBlank(result)){
        	return result;
        }
        StringBuffer sb = new StringBuffer();
		char charStr[] = result.toCharArray();
        for(int i=0;i<charStr.length;i++){
        	if((charStr[i]+" ").equals("﻿ ")){
        		//System.out.println(charStr[0]+" ");
        		continue;
        	}else{
            	sb.append(charStr[i]);
        	}
        	
        }
        //System.out.println(sb.toString());
        return sb.toString();
    }

    /**
     * 向指定 URL 发送POST方法的请求
     * 
     * @param url
     *            发送请求的 URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return 所代表远程资源的响应结果
     */
    public static String sendPost(String url, String param) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            //conn.setRequestProperty("accept", "*/*");
         /*   conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");*/
           // conn.setRequestProperty("Content-Type", "text/xml");  
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            System.out.println(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally{//使用finally块来关闭输出流、输入流
            try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
        if(StringUtils.isBlank(result)){
        	return result;
        }
        StringBuffer sb = new StringBuffer();
		char charStr[] = result.toCharArray();
        for(int i=0;i<charStr.length;i++){
        	if((charStr[i]+" ").equals("﻿ ")){
        		//System.out.println(charStr[0]+" ");
        		continue;
        	}
        	sb.append(charStr[i]);
        }
        return sb.toString();
    }   
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
	public static void main(String[] args) {
        //发送 GET 请求24948
        //String s=HttpRequest.sendGet("http://10.0.129.58/index.php/apis-get_gift.html", "member_lv_id=1&goods_id=24947,11124");
        //String s=HttpRequest.sendGet("http://10.0.129.58/index.php/apis-get_promotions.html", "member_lv_id=1&goods_id=24948");
        
        
       // System.out.println(s);
        
        // s=HttpRequest.sendGet("http://10.0.129.58/index.php/apis-get_final_price.html", "member_id=145");
        //System.out.println(s);
    	List list = new ArrayList();
    	Map maps = new HashMap();
    	maps.put("goods_id", "25036");
    	maps.put("price", "298");
    	list.add(maps);
    	Map maps2 = new HashMap();
    	maps2.put("goods_id", 25018);
    	maps2.put("price", 169.00);
    	list.add(maps2);
    	 String s = HttpRequest.sendPost("http://new.hjk365.com/index.php/apis-goods_prom.html", "goods_info="+JSONArray.fromObject(list));
         System.out.println(s);
		 /* RestTemplate restTemplate = new RestTemplate();
        String url = "http://10.0.129.42:8080/hjkRPC/services/logistics/queryGoodsStockById?prod_id=9076&pvc_id=11&local_id=11021&country_id=32048";
        			Map map = new HashMap();
        			String message = restTemplate.getForObject(url, String.class, map);
        			System.out.println(message.toString());*/
    }
}