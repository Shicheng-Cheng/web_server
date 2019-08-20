package com.first.server.util;

public class Logger {
	private Logger() {
		
	}
	
	public static void log(String msg) {
		System.out.println("[INFO]"+DataUtil.getCurrentTime()+""+msg);
	}

}
