package com.first.server.core;
/*
 * 封装请求协议：获取method的uri
 */

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Request {
	private String requeString;
	private String method;
	private String url;
	public String getUrl() {
		return url;
	}
	private String queryString;
	private final String CRLF = "\r\n";
	private Map<String,List<String>> parameterMap;
	public Request(Socket socket) throws IOException {
		this(socket.getInputStream());
	}
	
	public Request(InputStream is) {
		parameterMap=new HashMap<String, List<String>>();
		byte[] data=new byte[1024];
		int len;
		try {
			len=is.read(data);
			requeString=new String(data,0,len);
			System.out.println(requeString);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}
		parseRequest();
		
	}
	private void parseRequest() {
		System.out.println("开始解析字符串");
		System.out.println(requeString);
		this.method=this.requeString.substring(0,requeString.indexOf("/")).toLowerCase();
		System.out.println(method);
		int start=this.requeString.indexOf("/")+1;
		int end=this.requeString.indexOf("HTTP/");
		this.url=this.requeString.substring(start, end);
		System.out.println("*************");
		
		System.out.println(url);
		int queryIndex=requeString.indexOf("?");
		if (queryIndex>=0) {
			String[] queryArray=requeString.split("\\?");
			url=queryArray[0];
			queryString=queryArray[1];
			System.out.println(queryString);
		}
		if(method.equals("post")) {
			String qStr=this.requeString.substring(this.requeString.lastIndexOf(CRLF)).trim();
			if(null==queryString) {
				queryString=qStr;
				
			}
			else {
				queryString+="&"+qStr;
			}
		}
		queryString=null==queryString?"":queryString;//先执行后面的三元运算符
		convertMap();
	}

	private void convertMap() {
		// TODO Auto-generated method stub
		String[] str_num=queryString.split("&");
		for(String str1: str_num) {
			String[] kv_numString=str1.split("=");
			kv_numString=Arrays.copyOf(kv_numString, 2);
			String key=kv_numString[0];
			String value=kv_numString[1]==null?null:decode(kv_numString[1], "gbk");
			if(!parameterMap.containsKey(key)) {
				parameterMap.put(key, new ArrayList<String>());
			}
			parameterMap.get(key).add(value);
		}	
	}
	public String[] getParaterValueStrings(String key) {
		List<String>list=this.parameterMap.get(key);
		if(null==list || list.size()<0) {
			return null;
		}
		return list.toArray(new String[0]);
	}
	public String getParater(String key) {
		String[] values=this.getParaterValueStrings(key);
		return key==null?null:values[0];
	}
	public String decode(String value,String rec) {
		 try {
			return java.net.URLDecoder.decode(value,rec);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		 
	}

}
