package com.services;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
@Path("/dslookup")
public class RequestHandler {

	@POST
	@Consumes({"application/json"})
	@Path("/getFileInfo")
	public String getFileInfo(String input) {
		String response="{'hello':'Smarth'}";
		
		return response;
}
}
