package messages;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class UpdateCacheRequest {

	String filename;

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
	
	public String getJsonString() {
		Gson gson = new GsonBuilder().disableHtmlEscaping().create();
		String json = gson.toJson(this);
		return json;
	}
	
	public UpdateCacheRequest getClassFromJsonString(String replyInString) {
		Gson gson = new GsonBuilder().disableHtmlEscaping().create();
		UpdateCacheRequest classObj = gson.fromJson(replyInString, UpdateCacheRequest.class);
		return classObj;
	}
	
}
