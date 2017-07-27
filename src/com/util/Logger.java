package com.util;

public class Logger {

	
	private static Logger logger = new Logger();
	
	private Logger() {
		
	}
	
	public static void debug(String msg) {
		System.out.println(msg);
	}
	
	public static void error(String msg) {
		System.out.println(msg);
	}
}
