package com.messages;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class TokenValidationRequest {
	String token;
	String encryptedUsername;
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	
	public String getEncryptedUsername() {
		return encryptedUsername;
	}
	public void setEncryptedUsername(String encryptedUsername) {
		this.encryptedUsername = encryptedUsername;
	}
	public TokenValidationRequest getClassFromJson(String param)
	{
		Gson gson = new GsonBuilder().disableHtmlEscaping().create();
		TokenValidationRequest tokenRequest = gson.fromJson(param,TokenValidationRequest.class);
		return tokenRequest;
		
	}

}