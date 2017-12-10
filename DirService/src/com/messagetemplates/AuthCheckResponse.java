package com.messagetemplates;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


public class AuthCheckResponse {

	String authStatus;
	String key2;
	/**
	 * @return the authStatus
	 */
	public String getAuthStatus() {
		return authStatus;
	}

	/**
	 * @param authStatus the authStatus to set
	 */
	public void setAuthStatus(String authStatus) {
		this.authStatus = authStatus;
	}

	
	
	/**
	 * @return the key2
	 */
	public String getKey2() {
		return key2;
	}

	/**
	 * @param key2 the key2 to set
	 */
	public void setKey2(String key2) {
		this.key2 = key2;
	}

	public AuthCheckResponse getClassFromJsonString(String replyInString) {
		Gson gson = new GsonBuilder().disableHtmlEscaping().create();
		AuthCheckResponse response = gson.fromJson(replyInString, AuthCheckResponse.class);
		return response;
	}
}
