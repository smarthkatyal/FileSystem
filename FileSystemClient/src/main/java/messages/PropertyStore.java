package messages;

import java.io.IOException;
import java.util.Properties;

public  class PropertyStore {

	public static String serverUrl;
	public static String loginUrl;
	public static String readfileUrl;
	

	public static void loadProperties() {
		Properties config = new Properties();
		try {
			config.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("config.properties"));
			serverUrl = config.getProperty("serverUrl");
			loginUrl = config.getProperty("loginUrl");
			readfileUrl = config.getProperty("readfileUrl");
		} catch (IOException e) {
			System.out.println("Failed to load property file"+e);
		}
	}
}
