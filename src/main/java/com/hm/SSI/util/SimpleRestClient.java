package com.hm.SSI.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;


public class SimpleRestClient {
	public static void main(String[] args) throws Exception {
		
		RestTemplate rest = new RestTemplate();
		MultiValueMap<String, Object> formData = new LinkedMultiValueMap<String, Object>();
		formData.add("username", StringUtil.urlEncode("孟庆铭"));
		formData.add("password", "abc");
		
		HttpHeaders requestHeaders = new HttpHeaders();
		requestHeaders.set("Encoding", "UTF-8");
		requestHeaders.setContentType(org.springframework.http.MediaType.MULTIPART_FORM_DATA);    
		
		HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<MultiValueMap<String, Object>>(formData, requestHeaders);
		String result = rest.postForObject("http://localhost/SSI/service/rest/app/user/login", requestEntity, String.class);
		System.out.println(result);
		
		
		/**
		RestTemplate rest = new RestTemplate();
		MultiValueMap<String, Object> formData = new LinkedMultiValueMap<String, Object>();
		formData.add("param", StringUtil.urlEncode(""));
		formData.add("key", StringUtil.urlEncode(""));
		
		HttpHeaders requestHeaders = new HttpHeaders();
		requestHeaders.set("Encoding", "UTF-8");
		requestHeaders.setContentType(org.springframework.http.MediaType.MULTIPART_FORM_DATA);    
		
		HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<MultiValueMap<String, Object>>(formData, requestHeaders);
		
		String result = rest.postForObject("http://www.17jianzou.com/service/rest/app/devicedata/poll", requestEntity, String.class);
		System.out.println(result);*/
	}
	
}
