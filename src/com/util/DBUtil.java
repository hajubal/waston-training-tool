package com.util;

import java.net.MalformedURLException;
import java.net.URL;

import com.cloudant.client.api.ClientBuilder;
import com.cloudant.client.api.CloudantClient;
import com.cloudant.client.api.Database;

public class DBUtil {
	
	private static final String DB_NAME = "test_history";

	private CloudantClient client = null;
	
	private static DBUtil instance = new DBUtil();
	
	private DBUtil() {
		String url = PropertiesUtil.get("db.url");
		String userName = PropertiesUtil.get("db.username");
		String password = PropertiesUtil.get("db.password");
		
		try {
			client = ClientBuilder.url(new URL(url)).username(userName).password(password).build();
		} catch (MalformedURLException e) {
			throw new RuntimeException();
		}
	}
	
	public static DBUtil getInstance() {
		return instance;
	}
	
	public CloudantClient getClient() {
		return this.client;
	}
	
	public Database getDatabase() {
		return this.client.database(DB_NAME, true);
	}
	
	public Database getDatabase(String databaseName) {
		return this.client.database(databaseName, true);
	}
}
