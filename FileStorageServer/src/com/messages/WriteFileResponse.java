package com.messages;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class WriteFileResponse {

	String authstatus;

	/**
	 * @return the authstatus
	 */
	public String getAuthstatus() {
		return authstatus;
	}

	/**
	 * @param authstatus the authstatus to set
	 */
	public void setAuthstatus(String authstatus) {
		this.authstatus = authstatus;
	}
	
	
	public String getJsonString() {
		Gson gson = new GsonBuilder().disableHtmlEscaping().create();
		String json = gson.toJson(this);
		return json;
	}
	
	public WriteFileResponse getClassFromJsonString(String replyInString) {
		Gson gson = new GsonBuilder().disableHtmlEscaping().create();
		WriteFileResponse response = gson.fromJson(replyInString, WriteFileResponse.class);
		return response;
	}
}
