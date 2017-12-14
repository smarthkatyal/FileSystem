package com.restservices;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

import com.cache.CrudeMemoryCache;

import messages.UpdateCacheRequest;

@Path("/client")
public class RequestHandler {

	//Function to remove file from client cache
	@POST
	@Consumes({"application/json"})
	@Path("/removeFromCache")
	public String removeFromCache(String input) {
		UpdateCacheRequest req = new UpdateCacheRequest();
		req = req.getClassFromJsonString(input);
		String filename = req.getFilename();
		CrudeMemoryCache.cacheStore.remove(filename);
		CrudeMemoryCache.cacheStoreTime.remove(filename);
		return "{\"status\":\"1\"}";
	}

	
}
