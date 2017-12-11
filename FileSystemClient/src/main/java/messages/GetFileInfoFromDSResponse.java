package messages;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GetFileInfoFromDSResponse {

	public String serverurl;
	public String directory;
	public String token;
	public String authstatus;
	/**
	 * @return the serverurl
	 */
	public String getServerurl() {
		return serverurl;
	}
	/**
	 * @param serverurl the serverurl to set
	 */
	public void setServerurl(String serverurl) {
		this.serverurl = serverurl;
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
	
	public GetFileInfoFromDSResponse getClassFromJsonString(String replyInString) {
		Gson gson = new GsonBuilder().disableHtmlEscaping().create();
		GetFileInfoFromDSResponse response = gson.fromJson(replyInString, GetFileInfoFromDSResponse.class);
		return response;
	}
	 
}
