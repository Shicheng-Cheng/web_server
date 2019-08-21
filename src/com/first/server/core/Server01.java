package com.first.server.core;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

import com.first.server.util.Logger;

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
			socket=serversocket.accept();
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
			if(socket!=null) {
				try {
					socket.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		}
	//接收连接
	public void receive() {
		try {
			Socket socket=serversocket.accept();
			System.out.println("客户端开始连接");
			//获取请求协议
			InputStream inputStream =socket.getInputStream();
			//逐个字节进行读取
			byte[] data=new byte[1024];
			int len=inputStream.read(data);
			String requeString=new String(data,0,len);
			System.out.println(requeString);
			StringBuilder stringBuilder=new StringBuilder();
			stringBuilder.append("<html>");
			stringBuilder.append("<title>");
			stringBuilder.append("login");
			stringBuilder.append("</title>");
			stringBuilder.append("<body>");
			stringBuilder.append("页面内容");
			stringBuilder.append("</body>");
			stringBuilder.append("</html>");
			int size=stringBuilder.length();
			StringBuilder responseBuilder=new StringBuilder();
			String blank=" ";
			String CRLF ="\r\n";
			responseBuilder.append("HTTP/1.1").append(blank);
			responseBuilder.append(200).append(blank);
			responseBuilder.append("OK").append(CRLF);
			responseBuilder.append("Date:").append(new Date()).append(CRLF);
			responseBuilder.append("Server:").append("");
			responseBuilder.append("Content-type:text/html").append(CRLF);
			responseBuilder.append("Content-length:").append(size).append(CRLF);
			responseBuilder.append(CRLF);
			socket.getOutputStream();
			BufferedWriter bw=new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			bw.write(responseBuilder.toString());
			bw.flush();
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
