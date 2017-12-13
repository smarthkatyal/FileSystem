package com.messages;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class LoginRequest {
	String username;
	String password;
	String filename;

	
	public String getFilename() {
		return filename;
	}


	public void setFilename(String filename) {
		this.filename = filename;
	}


	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public LoginRequest getClassFromJson(String param)
	{
		Gson gson = new GsonBuilder().disableHtmlEscaping().create();
		LoginRequest request = gson.fromJson(param,LoginRequest.class);
		return request;
		
	}
	
}