package com.services;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

import com.helper.HelperFunctions;
import com.messages.AuthCheckRequest;
import com.messages.AuthCheckResponse;
import com.messages.PropertyStore;
import com.messages.ReadFileRequest;
import com.messages.ReadFileResponse;
import com.messages.WriteFileRequest;
import com.messages.WriteFileResponse;
import com.security.SecurityFunctions;

@Path("/reader")
public class RequestHandler {
	
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
			String fcontent="";
			try {
				fcontent = new String(Files.readAllBytes(Paths.get(path)));
				System.out.println(fcontent);
				fcontent = SecurityFunctions.encrypt(fcontent, checkResponse.getKey1());
				//Send filecontent and auth status back to client
				readResponse.setAuthstatus(checkResponse.getAuthstatus());
				readResponse.setFilecontent(fcontent);
			} catch (NoSuchFileException e) {
				fcontent = "";
				fcontent = SecurityFunctions.encrypt(fcontent, checkResponse.getKey1());
				//Send filecontent and auth status back to client
				readResponse.setAuthstatus(checkResponse.getAuthstatus());
				readResponse.setFilecontent(fcontent);
			}catch(IOException e) {
				readResponse.setAuthstatus(checkResponse.getAuthstatus());
				e.printStackTrace();
			}
		}else {
			readResponse.setAuthstatus(checkResponse.getAuthstatus());
		}
		String readResponseJson = readResponse.getJsonString();
		System.out.println(readResponseJson);
		return readResponseJson;
	}
	
	@POST
	@Consumes({"application/json"})
	@Path("/writeFile")
	public String writeFile(String input) {
		PropertyStore.loadProperties();
		WriteFileRequest writeRequest = new WriteFileRequest();
		WriteFileResponse writeResponse = new WriteFileResponse();
		writeRequest = writeRequest.getClassFromJsonString(input);
		String token = writeRequest.getToken();
		String encUsername = writeRequest.getEncryptedUsername();
		String directory = writeRequest.getDirectory();
		String filename = writeRequest.getFilename();
		String filecontent = writeRequest.getFilecontent();
		//Send request to AS to check token
		AuthCheckRequest checkReq = new AuthCheckRequest();
		AuthCheckResponse checkResponse = new AuthCheckResponse();
		HelperFunctions hf = new HelperFunctions();
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
			filecontent = SecurityFunctions.decrypt(filecontent, checkResponse.getKey1());
			//Encrypt file content with key1 and return content
			path = "C:\\Users\\Smarth Katyal\\Desktop\\FileStorage\\"+directory+filename;
			try {
				Files.write(Paths.get(path), filecontent.getBytes());
				writeResponse.setAuthstatus(checkResponse.getAuthstatus());
			}catch(IOException e) {
				writeResponse.setAuthstatus("N");
				e.printStackTrace();
			}
		}else {
			writeResponse.setAuthstatus("N");
		}
		return writeResponse.getJsonString();
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
