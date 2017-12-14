package com.messages;

import java.io.IOException;
import java.util.Properties;

public  class PropertyStore {

	public static String authserverip;
	public static String aurthserverurl;
	public static String loadstatus="";
	public static String dbusername;
	public static String dbpassword;
	public static String dbip;
	public static String dbport;
	public static String dbname;
	

	public static void loadProperties() {
		Properties config = new Properties();
		try {
			config.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("config.properties"));
			authserverip = config.getProperty("authserverip");
			aurthserverurl = config.getProperty("aurthserverurl");
			dbusername = config.getProperty("dbusername");
			dbpassword = config.getProperty("dbpassword");
			dbip = config.getProperty("dbip");
			dbport = config.getProperty("dbport");
			dbname = config.getProperty("dbname");
			loadstatus = "1";
		} catch (IOException e) {
			System.out.println("Failed to load property file"+e);
		}
	}
}