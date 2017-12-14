package com.messages;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class LockFileResponse {
	String lockstatus;
	String authstatus;
	//0 - unlocked file successfully anyhow
	//1 - 
	String releaseStatus;   
	

	public String getReleaseStatus() {
		return releaseStatus;
	}

	public void setReleaseStatus(String releaseStatus) {
		this.releaseStatus = releaseStatus;
	}

	public String getLockstatus() {
		return lockstatus;
	}

	public void setLockstatus(String lockstatus) {
		this.lockstatus = lockstatus;
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
		String responsetoclient = gson.toJson(this);
		return responsetoclient;
	}
}
