package messages;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class WriteResponse {

	String authstatus;
	String filestatus;
	
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

	/**
	 * @return the filestatus
	 */
	public String getFilestatus() {
		return filestatus;
	}

	/**
	 * @param filestatus the filestatus to set
	 */
	public void setFilestatus(String filestatus) {
		this.filestatus = filestatus;
	}


	public String getJsonString() {
		Gson gson = new GsonBuilder().disableHtmlEscaping().create();
		String json = gson.toJson(this);
		return json;
	}
	
	public WriteResponse getClassFromJsonString(String replyInString) {
		Gson gson = new GsonBuilder().disableHtmlEscaping().create();
		WriteResponse response = gson.fromJson(replyInString, WriteResponse.class);
		return response;
	}
}
