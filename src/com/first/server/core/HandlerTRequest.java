package com.first.server.core;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import com.first.server.util.Logger;

public class HandlerTRequest implements Runnable {
    Socket socket=null;
    
    PrintWriter out=null;
	public HandlerTRequest(Socket socket) {
		this.socket=socket;
		// TODO Auto-generated constructor stub
	}

	@Override
	public void run() {	
		BufferedReader br=null;
		Logger.log("thread:"+Thread.currentThread().getName());
		try {
			br=new BufferedReader(new InputStreamReader(socket.getInputStream()));
			//获取输出流对象
			out=new PrintWriter(socket.getOutputStream());
			//获取请求协议的请求行
			String requestLine=br.readLine();
			//请求方式 	URI 以及请求协议版本  三者通过一个空格拼接
			String requestURI=requestLine.split(" ")[1];
			//判断用户请求是否为静态页面
			if(requestURI.endsWith(".html") || requestURI.endsWith(".htm")) {
				responseStaticPage(requestURI,out);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			if(br!=null) {
				try {
					br.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		// TODO Auto-generated method stub

	}

	private void responseStaticPage(String requestURI, PrintWriter out2) {
		String htmlPath=requestURI.substring(1);
		
		BufferedReader br=null;
		try {
			BufferedReader bufferedReader=new BufferedReader(new FileReader(htmlPath));
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// TODO Auto-generated method stub
		
	}

}
