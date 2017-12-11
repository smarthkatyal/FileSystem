package com.services;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

import org.apache.tomcat.jni.File;
import org.apache.tomcat.util.http.fileupload.FileUtils;

import com.helper.HelperFunctions;
import com.messages.AuthCheckRequest;
import com.messages.AuthCheckResponse;
import com.messages.PropertyStore;
import com.messages.ReadFileRequest;
import com.messages.ReadFileResponse;
import com.security.SecurityFunctions;

@Path("/reader")
public class Reader {
	
	@POST
	@Consumes({"application/json"})
	@Path("/readFile")
	public String readFile(String input) {
		PropertyStore.loadProperties();
		ReadFileRequest readRequest = new ReadFileRequest();
		readRequest = readRequest.getClassFromJsonString(input);
		String token = readRequest.getToken();
		String encUsername = readRequest.getEncryptedUsername();
		String directory = readRequest.getDirectory();
		String filename = readRequest.getFilename();
		
		//Send request to AS to check token
		AuthCheckRequest checkReq = new AuthCheckRequest();
		AuthCheckResponse checkResponse = new AuthCheckResponse();
		HelperFunctions hf = new HelperFunctions();
		ReadFileResponse readResponse = new ReadFileResponse();
		String path = "";
		checkReq.setToken(token);
		checkReq.setEncryptedUsername(encUsername);
		String authCheckRequest = checkReq.getJsonString();
		String authCheckResponse = hf.sendAuthCheckRequest(authCheckRequest);
		checkResponse = checkResponse.getClassFromJsonString(authCheckResponse);
		if(checkResponse.getAuthstatus().equals("Y")) {
			//First decrypt directory, filename
			filename = SecurityFunctions.decrypt(filename, checkResponse.getKey1());
			directory = SecurityFunctions.decrypt(directory, checkResponse.getKey1());
			//Encrypt file content with key1 and return content
			path = "C:\\Users\\Smarth Katyal\\Desktop\\FileStorage\\"+directory+filename;
			try {
				String fcontent = new String(Files.readAllBytes(Paths.get(path)));
				System.out.println(fcontent);
				fcontent = SecurityFunctions.encrypt(fcontent, checkResponse.getKey1());
				//Send filecontent and auth status back to client
				readResponse.setAuthstatus(checkResponse.getAuthstatus());
				readResponse.setFilecontent(fcontent);
			} catch (IOException e) {
				readResponse.setAuthstatus(checkResponse.getAuthstatus());
				e.printStackTrace();
			}
		}else {
			readResponse.setAuthstatus(checkResponse.getAuthstatus());
			//TODO:Return failure to authenticate
		}
		String readResponseJson = readResponse.getJsonString();
		System.out.println(readResponseJson);
		return readResponseJson;
	}

}
