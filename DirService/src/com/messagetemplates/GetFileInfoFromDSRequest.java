package com.messagetemplates;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


public class GetFileInfoFromDSRequest {

	String token;
	String filename;
	String encryptedUsername;
	/**
	 * @return the token
	 */
	public String getToken() {
		return token;
	}
	/**
	 * @param token the token to set
	 */
	public void setToken(String token) {
		this.token = token;
	}
	/**
	 * @return the filename
	 */
	public String getFilename() {
		return filename;
	}
	/**
	 * @param filename the filename to set
	 */
	public void setFilename(String filename) {
		this.filename = filename;
	}
	
	/**
	 * @return the encryptedUsername
	 */
	public String getEncryptedUsername() {
		return encryptedUsername;
	}
	/**
	 * @param encryptedUsername the encryptedUsername to set
	 */
	public void setEncryptedUsername(String encryptedUsername) {
		this.encryptedUsername = encryptedUsername;
	}
	
	public GetFileInfoFromDSRequest getClassFromJsonString(String replyInString) {
		Gson gson = new GsonBuilder().disableHtmlEscaping().create();
		GetFileInfoFromDSRequest getFileInfoFromDSRequest = gson.fromJson(replyInString, GetFileInfoFromDSRequest.class);
		return getFileInfoFromDSRequest;
	}

}
