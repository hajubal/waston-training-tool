package com.util;

public class Asserter {
	private Asserter() {}
	
	public static String getNotNullData(Object data, String alternateData) {
		if(data == null) return alternateData;
		else {
			String strData = data.toString();
			return "".equals(strData) ? alternateData : strData;
		}
	}
	
	public static String getNotNullData(String data, String alternateData) {
		return data == null || "".equals(data.trim()) ? alternateData : data;
	}

	public static boolean isNullOrEmpty(Object data) {
		if(data == null) return true;
		else {
			if(data.getClass().isArray()) {
				Object[] values = (Object[])data;
				if(values.length == 0) {
					return true;
				} else {
					return false;
				}
			} else {
				String strData = data.toString();
				return "".equals(strData.trim()) ? true : false;
			}
		}
	}
	
	public static boolean isNullOrEmpty(String data) {
		if(data == null) {
			return true;
		}
		
		data = data.trim();
		
		return "".equals(data) || "NULL".equalsIgnoreCase(data) ? true : false;
	}
	
	public static boolean isNotNullAndNotEmpty(Object data) {
		if(data == null) {
			return false;
		}
		if(data instanceof String) return isNotNullAndNotEmpty((String)data);
		else {
			if(data.getClass().isArray()) {
				Object[] values = (Object[])data;
				if(values.length == 0) {
					return false;
				} else {
					return true;
				}
			}
			
			return data != null;
		}
	}
	
	public static boolean isNotNullAndNotEmpty(String data) {
		if(data == null) {
			return false;
		}
		
		data = data.trim();
		
		return"".equals(data) == false && "NULL".equalsIgnoreCase(data) == false;
	}
}
