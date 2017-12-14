package com.messagetemplates;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GetCompleteInfoResponse {

	String filenamearr;
	String directoryarr;
	/**
	 * @return the filenamearr
	 */
	public String getFilenamearr() {
		return filenamearr;
	}
	/**
	 * @param filenamearr the filenamearr to set
	 */
	public void setFilenamearr(String filenamearr) {
		this.filenamearr = filenamearr;
	}
	/**
	 * @return the directoryarr
	 */
	public String getDirectoryarr() {
		return directoryarr;
	}
	/**
	 * @param directoryarr the directoryarr to set
	 */
	public void setDirectoryarr(String directoryarr) {
		this.directoryarr = directoryarr;
	}

	public String getJsonString() {
		Gson gson = new GsonBuilder().disableHtmlEscaping().create();
		String json = gson.toJson(this);
		return json;
	}
}
