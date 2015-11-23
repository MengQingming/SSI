package com.hm.hxl.order.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * 类名: UrlTools 
 * 作用: 读取指定文件的内容，并返回String
 * 作者: yanpengjie 
 * 日期: 2015-7-17 下午12:52:08 
 * 版本: V 1.0
 *
 */
public class UrlTools {
	public UrlTools() {
	} 
	public static String readURLContents(String urlString) {
		String inputLine = "";
		String line = "";
		BufferedReader in = null;
		URL url = null;
		try {
			url = new URL(urlString);
			HttpURLConnection connection = (HttpURLConnection) url
					.openConnection();
			in = new BufferedReader(new InputStreamReader(connection
					.getInputStream(),"UTF-8"));
			while ((line = in.readLine()) != null) {
				inputLine = inputLine + line + "\n";
			}
		} catch (MalformedURLException e) {
			// TODO 锟皆讹拷锟斤拷锟?catch 锟斤拷
			e.printStackTrace();
		} catch (IOException e) {
			// TODO 锟皆讹拷锟斤拷锟?catch 锟斤拷
			e.printStackTrace();
		} finally {
		}
		return inputLine;
	}

}
