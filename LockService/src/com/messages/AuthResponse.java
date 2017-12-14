package com.messages;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class AuthResponse {
	String key1;
	String authstatus;
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
	public AuthResponse getClassFromJson(String param)
	{
		Gson gson = new GsonBuilder().disableHtmlEscaping().create();
		AuthResponse responsefromAS = gson.fromJson(param,AuthResponse.class);
		return responsefromAS;
		
	}

}
