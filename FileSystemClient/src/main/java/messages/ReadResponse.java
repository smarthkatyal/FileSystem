package messages;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class ReadResponse {

	String filecontent;

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
	
	public ReadResponse getClassFromJsonString(String replyInString) {
		Gson gson = new GsonBuilder().disableHtmlEscaping().create();
		ReadResponse response = gson.fromJson(replyInString, ReadResponse.class);
		return response;
	}
}
