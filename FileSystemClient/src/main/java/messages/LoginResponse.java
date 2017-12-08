package messages;

import com.google.gson.Gson;

public class LoginResponse {

	String name;
	String token;
	String authstatus;
	String usertype;
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
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
	
	/**
	 * @return the usertype
	 */
	public String getUsertype() {
		return usertype;
	}
	/**
	 * @param usertype the usertype to set
	 */
	public void setUsertype(String usertype) {
		this.usertype = usertype;
	}
	public LoginResponse getClassFromJsonString(String replyInString) {
		Gson gson = new Gson();
		LoginResponse loginResponse = gson.fromJson(replyInString, LoginResponse.class);
		return loginResponse;
	}
}
