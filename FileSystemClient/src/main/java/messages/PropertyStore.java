package messages;

import java.io.IOException;
import java.util.Properties;

public  class PropertyStore {

	public static String serverUrl;
	public static String directoryServerUrl;
	public static String loginUrl;
	public static String readfileUrl;
	public static String directoryinfoUrl;
	public static String lockServerUrl;
	public static String lockingUrl;

	public static void loadProperties() {
		Properties config = new Properties();
		try {
			config.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("config.properties"));
			
			
			serverUrl = config.getProperty("serverUrl");
			directoryServerUrl = config.getProperty("directoryServerUrl");
			loginUrl = config.getProperty("loginUrl");
			readfileUrl = config.getProperty("readfileUrl");
			directoryinfoUrl = config.getProperty("directoryinfoUrl");
			lockServerUrl = config.getProperty("lockServerUrl");
			lockingUrl = config.getProperty("lockingUrl");
			
		} catch (IOException e) {
			System.out.println("Failed to load property file"+e);
		}
	}
}
