package com.util;

public class Logger {

	
	private Logger() {
		
	}
	
	public static void debug(String msg) {
		System.out.println(msg);
	}
	
	public static void error(String msg) {
		System.out.println(msg);
	}
	
	public static void error(String msg, Exception e) {
		e.printStackTrace();
		
		System.out.println(msg);
	}
}
