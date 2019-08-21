package com.server;

/*
 * 返回响应协议
 */
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Date;

import org.omg.CORBA.PRIVATE_MEMBER;

public class response {
	private BufferedWriter bwBufferedWriter;
	// 正文
	private StringBuilder contentBuilder;// 正文信息
	private StringBuilder headinfoBuilder;// 请求头信息
	private int len = 0;// 正文的字节长度
	private final String BLANK = " ";
	private final String CRLF = "\r\n";

	public response() {
		contentBuilder = new StringBuilder();
		headinfoBuilder = new StringBuilder();
		len = 0;

	}

	public response(Socket socket) {
		this();
		try {
			bwBufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public response(OutputStream os) {
		this();
		bwBufferedWriter = new BufferedWriter(new OutputStreamWriter(os));
	}

//动态添加内容
	public response print(String info) {
		contentBuilder.append(info);
		len+=info.getBytes().length;
		return this;
	}
	public response println(String info) {
		contentBuilder.append(info).append(CRLF);
		len+=(info+CRLF).getBytes().length;
		return this;
	}

	private void createInfo(int code) {
		headinfoBuilder.append("HTTP/1.1").append(BLANK);
		headinfoBuilder.append(code).append(BLANK);
		switch (code) {
		case 200:
			headinfoBuilder.append("OK").append(CRLF);
			break;
		case 404:
			headinfoBuilder.append("NOT FOUND").append(CRLF);
			break;
		case 505:
			headinfoBuilder.append("SERVER ERROR").append(CRLF);
			break;
		}
		// 拼接响应协议
		headinfoBuilder.append("Date:").append(new Date()).append(CRLF);
		headinfoBuilder.append("Server:").append("Test Server/0.0.1;charset=GBK").append(CRLF);
		headinfoBuilder.append("Content-type:text/html").append(CRLF);
		headinfoBuilder.append("Content-length:").append(len).append(CRLF);
		headinfoBuilder.append(CRLF);

	}
}
