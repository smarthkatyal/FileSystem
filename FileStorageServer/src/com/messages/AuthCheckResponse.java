package com.messages;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


public class AuthCheckResponse {

	String authstatus;
	String key1;
	/**
	 * @return the authStatus
	 */
	public String getAuthstatus() {
		return authstatus;
	}

	/**
	 * @param authStatus the authStatus to set
	 */
	public void setAuthstatus(String authstatus) {
		this.authstatus = authstatus;
	}

	
	

	/**
	 * @return the key1
	 */
	public String getKey1() {
		return key1;
	}

	/**
	 * @param key1 the key1 to set
	 */
	public void setKey1(String key1) {
		this.key1 = key1;
	}

	public AuthCheckResponse getClassFromJsonString(String replyInString) {
		Gson gson = new GsonBuilder().disableHtmlEscaping().create();
		AuthCheckResponse response = gson.fromJson(replyInString, AuthCheckResponse.class);
		return response;
	}
}
