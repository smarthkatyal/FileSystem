package com.messages;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class AuthRequest {
	String encryptedUsername;
	String token;

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
	public String getJsonString()
	{
		Gson gson = new GsonBuilder().disableHtmlEscaping().create(); 
		String loginResponse = gson.toJson(this);
		return loginResponse;
	}

}
