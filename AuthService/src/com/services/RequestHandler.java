package com.services;

import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.StringTokenizer;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

import com.helper.HelperFunctions;
import com.messages.LoginRequest;
import com.messages.LoginResponse;
import com.messages.TokenValidationRequest;
import com.messages.TokenValidationResponse;
import com.security.SecurityFunctions;



@Path("/auth")
public class RequestHandler {
	@POST
	@Consumes({"application/json"})
	@Path("/signIn")
	public String create(String param1){

		LoginRequest lr = new LoginRequest();
		lr = lr.getClassFromJson(param1);
		String client_username = lr.getUsername();
		String client_password = lr.getPassword();
		LoginResponse lresponse = new LoginResponse();
		try{
			HelperFunctions hf = new HelperFunctions();
			Connection conn = hf.sqlconnect();
			Statement stmt=conn.createStatement();  
			ResultSet rs=stmt.executeQuery("select fname,usertype,token,pswd from users where encrypt_username = '" + client_username +"';");
			if(rs.next()){
				if(rs.getString(4).equals(SecurityFunctions.decrypt(client_password, rs.getString(4)))){
					lresponse.setAuthstatus("Y");
					lresponse.setName(rs.getString(1));
					lresponse.setUsertype(rs.getString(2));
					lresponse.setToken(rs.getString(3));
					String key1 = SecurityFunctions.getNewKey();
					String key2 = SecurityFunctions.getNewKey();
					String time = String.valueOf(System.currentTimeMillis());
					String myToken = lresponse.getName() + ";;" + key1 + ";;" + time;
					lresponse.setToken(SecurityFunctions.encrypt(myToken,key2));
					lresponse.setKey1(key1);
					String query = "update users set key1 = ?, key2 = ?, token = ? where encrypt_username = '" + client_username +"';  ";
					PreparedStatement preparedStmt = conn.prepareStatement(query);
					preparedStmt.setString (1, key1);
					preparedStmt.setString (2, key2);
					preparedStmt.setString (3, lresponse.getToken());
					preparedStmt.execute();
					return lresponse.getJsonString();
				}
			}
			lresponse.setAuthstatus("N");
			conn.close();  
		}catch(Exception e){
			System.out.println("Exception: "+e);
		}  
		return lresponse.getJsonString();
	}

	@POST
	@Consumes({"application/json"})
	@Path("/tokenCheck")
	public String tokenCheck(String param){
		TokenValidationRequest tr = new TokenValidationRequest();
		tr = tr.getClassFromJson(param);
		String client_username = tr.getEncryptedUsername();
		String client_token = tr.getToken();
		TokenValidationResponse tresponse = new TokenValidationResponse();
		HelperFunctions hf = new HelperFunctions();
		Connection conn = hf.sqlconnect();
		try {
			Statement stmt=conn.createStatement();  
			ResultSet rs=stmt.executeQuery("select token,key2,key1 from users where token = '" + client_token +"' and encrypt_username = '" + client_username +"';");
			if(rs.next()) {
				client_token = SecurityFunctions.decrypt(client_token, rs.getString(2));
				StringTokenizer st = new StringTokenizer(client_token,";;");
				String ds_ttl = new String();
				while (st.hasMoreTokens()) {  
					ds_ttl = st.nextToken();  
				}
				long a = Long.parseLong(ds_ttl);
				System.out.println(""+a);
				long b = System.currentTimeMillis();
				if(b - a <= 300000)	{
					tresponse.setAuthstatus("Y");
					tresponse.setKey1(rs.getString(3));
					return tresponse.getJsonString();
				}
			}			
			tresponse.setAuthstatus("N");
			return tresponse.getJsonString();	
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		tresponse.setAuthstatus("N");
		return tresponse.getJsonString();
	}

	/*
	 * Created only to test if auth server is up and running
	 */
	@POST
	@Consumes({"application/json"})
	@Path("/test")
	public String testServer(String input) {
		return "up and running";
	}






}







