package com.messages;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class TokenValidationResponse {
	String authstatus;
	String key1;

	public String getKey1() {
		return key1;
	}

	public void setKey1(String key1) {
		this.key1 = key1;
	}

	public String getAuthstatus() {
		return authstatus;
	}

	public void setAuthstatus(String authstatus) {
		this.authstatus = authstatus;
	}
	public String getJsonString()
	{
		Gson gson = new GsonBuilder().disableHtmlEscaping().create(); 
		String response = gson.toJson(this);
		return response;
	}
	public TokenValidationResponse getClassFromJson(String param)
	{
		Gson gson = new GsonBuilder().disableHtmlEscaping().create();
		TokenValidationResponse response = gson.fromJson(param,TokenValidationResponse.class);
		return response;
		
	}

}