package com.services;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

import com.helpers.HelperFunctions;
import com.messagetemplates.*;
import com.security.SecurityFunctions;

@Path("/dslookup")
public class RequestHandler {

	/*
	 * Gets a request from client with encrypted username(encrypted with password) and token(encrypted with Key2) and filename(encrypted with key1)
	 * It then sends a request to AS with encrypted username(encrypted with password) and token(encrypted with Key2)
	 * The AS sends Y or N after validation of token and username. It also sends key1.
	 * The DS then sends the response to client with file location encrypted with key1.
	 * 
	 */
	@POST
	@Consumes({"application/json"})
	@Path("/getFileInfo")
	public String getFileInfo(String input) {
		
		PropertyStore.loadProperties();
		GetFileInfoFromDSResponse getFileInfoFromDSResponse = new GetFileInfoFromDSResponse();
		GetFileInfoFromDSRequest getFileInfoFromDSRequest = new GetFileInfoFromDSRequest();
		String getFileInfoFromDSResponseString=new String();
		HelperFunctions hf = new HelperFunctions();
		AuthCheckRequest checkReq = new AuthCheckRequest();
		AuthCheckResponse checkResponse = new AuthCheckResponse();
		getFileInfoFromDSRequest = getFileInfoFromDSRequest.getClassFromJsonString(input);
		HashMap<String, String> fileStats = new HashMap<String,String>();
		
		checkReq.setToken(getFileInfoFromDSRequest.getToken());
		checkReq.setEncryptedUsername(getFileInfoFromDSRequest.getEncryptedUsername());
		String authCheckRequest = checkReq.getJsonString();
		
		String authCheckResponse = hf.sendAuthCheckRequest(authCheckRequest);
		
		checkResponse = checkResponse.getClassFromJsonString(authCheckResponse);
		if(checkResponse.getAuthstatus().equals("Y")) {
			
			try {
				if(getFileInfoFromDSRequest.getOperation().equals("r"))
					fileStats = hf.getFileLocationForRead(SecurityFunctions.decrypt(getFileInfoFromDSRequest.getFilename(),checkResponse.getKey1()));
				else
					fileStats = hf.getFileLocationForWrite(SecurityFunctions.decrypt(getFileInfoFromDSRequest.getFilename(),checkResponse.getKey1()));
				if(fileStats.isEmpty()) {
					getFileInfoFromDSResponse.setAuthstatus("Directory Information Not Available for the file"); 
					getFileInfoFromDSResponseString = getFileInfoFromDSResponse.getJsonString();
					return getFileInfoFromDSResponseString;
				}
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			
			getFileInfoFromDSResponse.setServerurl(SecurityFunctions.encrypt(fileStats.get("serverurl"),checkResponse.getKey1()));
			getFileInfoFromDSResponse.setDirectory(SecurityFunctions.encrypt(fileStats.get("directory"),checkResponse.getKey1()));
			getFileInfoFromDSResponse.setAuthstatus("Y");
		}else {
			getFileInfoFromDSResponse.setAuthstatus("Validation of token Failed");
		}
		
		getFileInfoFromDSResponseString = getFileInfoFromDSResponse.getJsonString();
		System.out.println(getFileInfoFromDSResponseString);
		return getFileInfoFromDSResponseString;
	}
	
	/*
	 * Created only to test if webserver is up and running
	 */
	@POST
	@Consumes({"application/json"})
	@Path("/test")
	public String testServer(String input) {
		return "up and running";
	}
}

