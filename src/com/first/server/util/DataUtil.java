package com.first.server.util;

import java.text.SimpleDateFormat;
import java.util.Date;



public class DataUtil {
	private static SimpleDateFormat dataFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS");
	private DataUtil() {
		
	}
	public static String getCurrentTime() {
		return dataFormat.format(new Date());
		
	}

}
