package com.messages;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class ReadFileResponse {

	public String filecontent;
	public String authstatus;
	/**
	 * @return the filecontent
	 */
	public String getFilecontent() {
		return filecontent;
	}
	/**
	 * @param filecontent the filecontent to set
	 */
	public void setFilecontent(String filecontent) {
		this.filecontent = filecontent;
	}
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
	
	public ReadFileResponse getClassFromJsonString(String replyInString) {
		Gson gson = new GsonBuilder().disableHtmlEscaping().create();
		ReadFileResponse response = gson.fromJson(replyInString, ReadFileResponse.class);
		return response;
	}
}
