package com.cache;

import java.util.HashMap;

public class CrudeMemoryCache {
	//Keep adding files here, key is filename and value is the filecontent
	public static HashMap<String, String> cacheStore = new HashMap<String, String>();
	public static HashMap<String, Long> cacheStoreTime = new HashMap<String, Long>();
}
