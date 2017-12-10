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
	
	void RequestHandler(){
		PropertyStore.loadProperties();
	}

	@POST
	@Consumes({"application/json"})
	@Path("/getFileInfo")
	public String getFileInfo(String input) {
		PropertyStore.loadProperties();
		GetFileInfoFromDSResponse getFileInfoFromDSResponse = new GetFileInfoFromDSResponse();
		GetFileInfoFromDSRequest getFileInfoFromDSRequest = new GetFileInfoFromDSRequest();
		getFileInfoFromDSRequest = getFileInfoFromDSRequest.getClassFromJsonString(input);

		
		AuthCheckRequest checkReq = new AuthCheckRequest();
		checkReq.setToken(getFileInfoFromDSRequest.getToken());
		checkReq.setEncryptedUsername(getFileInfoFromDSRequest.getEncryptedUsername());
		String authCheckRequest = checkReq.getJsonString();
		HelperFunctions hf = new HelperFunctions();
		String authCheckResponse = hf.sendAuthCheckRequest(authCheckRequest);
		AuthCheckResponse checkResponse = new AuthCheckResponse();
		checkResponse = checkResponse.getClassFromJsonString(authCheckRequest);
		if(checkResponse.getAuthStatus().equals("Y")) {
			HashMap<String, String> fileStats = new HashMap<String,String>();
			try {
				fileStats = hf.getFileLocation(SecurityFunctions.decrypt(getFileInfoFromDSRequest.getFilename(),checkResponse.getKey2()));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			
			getFileInfoFromDSResponse.setServerurl(fileStats.get("serverurl"));
			getFileInfoFromDSResponse.setDirectory(fileStats.get("directory"));
		}
		System.out.println(authCheckResponse);
		return getFileInfoFromDSResponse.getJsonString();
}
}

