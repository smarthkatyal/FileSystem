package messages;

import com.google.gson.Gson;

public class LoginRequest {

	String username;
	String password;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getJsonString() {
		Gson gson = new Gson();
		String json = gson.toJson(this);
		return json;
	}
	
}
