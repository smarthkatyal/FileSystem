package messages;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class WriteRequest {

	String filename;
	String token;
	String encryptedUsername;
	String directory;
	String filecontent;
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
	/**
	 * @return the directory
	 */
	public String getDirectory() {
		return directory;
	}
	/**
	 * @param directory the directory to set
	 */
	public void setDirectory(String directory) {
		this.directory = directory;
	}
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
	public String getJsonString() {
		Gson gson = new GsonBuilder().disableHtmlEscaping().create();
		String json = gson.toJson(this);
		return json;
	}
	
	public WriteRequest getClassFromJsonString(String replyInString) {
		Gson gson = new GsonBuilder().disableHtmlEscaping().create();
		WriteRequest request = gson.fromJson(replyInString, WriteRequest.class);
		return request;
	}
	
}
