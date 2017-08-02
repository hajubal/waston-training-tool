package com.util;

import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;

public class PropertiesUtil {
	final static Logger log = Logger.getLogger(PropertiesUtil.class);
	
	private Properties prop = new Properties();
	
	private static PropertiesUtil util = new PropertiesUtil();
	
	private static final String SYSTEM_PROPERTIES_PATH = "system.properties";
	
	private PropertiesUtil() {
		try {
			prop.load(PropertiesUtil.class.getClassLoader().getResourceAsStream(SYSTEM_PROPERTIES_PATH));
		} catch (IOException e) {
			log.error("system.properties load error.", e);
		}
	}
	
	public static String get(String key) {
		return util.prop.getProperty(key);
	}
	
	public static String get(String key, String defaultValue) {
		return util.prop.getProperty(key, defaultValue);
	}
}
