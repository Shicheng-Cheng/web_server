package com.first.server.core;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

import com.first.server.util.Logger;
import com.server.Response;

public class Server01 {
	private static ServerSocket serversocket;
	private static Socket socket;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Server01 server01=new Server01();
		start();
		//server01.receive();

	}
	//启动服务
	public static void start() {
		Logger.log("创建一个web socket应用");
		try {
			long start=System.currentTimeMillis();
			int port=ServerParser.getPort();
			Logger.log("创建一个web socket应用"+port);
			serversocket=new ServerSocket(port);
			long end=System.currentTimeMillis();
			Logger.log("开始启动"+(end-start)+"ms");
			receive();
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("服务器启动失败");
		}
		finally {
			if(serversocket!=null) {
				try {
					serversocket.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		}
	//接收连接
	public static void receive() {
		try {
			Socket socket=serversocket.accept();
			System.out.println("客户端开始连接");
			//获取请求协议
			Request request=new Request(socket);
			Response response=new Response(socket);	
			Servlet servlet=null;
//			Servlet servlet=new RegisterServlet();
			System.out.println("***");
			System.out.println(request.getUrl());
			if(request.getUrl().trim().equals("login")) {
				servlet=new LoginServlet();
			}
			else if(request.getUrl().trim().equals("reg"))
				{					servlet=new RegisterServlet();
				}
			servlet.service(request, response);
			response.pushToBrowser(200);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("服务器连接出现问题");
		}
		
	}
	//停止服务
	public void stop() {
		
	}

}
