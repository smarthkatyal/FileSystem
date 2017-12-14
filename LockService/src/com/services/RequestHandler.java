package com.services;

import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

import com.helper.HelperFunctions;
import com.messages.LockFileRequest;
import com.messages.AuthRequest;
import com.messages.PropertyStore;
import com.messages.AuthResponse;
import com.messages.LockFileResponse;
import com.security.SecurityFunctions;

@Path("/locker")
public class RequestHandler {
	@POST
	@Consumes({"application/json"})
	@Path("/getLock")
	public String lock(String input){
		PropertyStore.loadProperties();
		String reply = null;
		LockFileRequest lr = new LockFileRequest();
		lr = lr.getClassFromJson(input);
		String client_filename = lr.getFilename();
		String client_email = lr.getEmail();
		String client_username = lr.getUsername();
		String client_token = lr.getToken();
		AuthRequest lresponse = new AuthRequest();
		lresponse.setToken(client_token);
		lresponse.setEncryptedUsername(client_username);
		String jsonstr = lresponse.getJsonString();
		HelperFunctions hf = new HelperFunctions();
		String resp = hf.sendAuthRequest(jsonstr);
		AuthResponse as = new AuthResponse();
		as=as.getClassFromJson(resp);
		String key1 = as.getKey1();
		try {
			String auth = as.getAuthstatus();
			Connection conn = hf.sqlconnect();
			LockFileResponse rtc = new LockFileResponse();
			//To process lock request
			if (auth.equals("Y")) {
				String decrypt_client_filename = SecurityFunctions.decrypt(client_filename, key1);
				String decrypt_client_email = SecurityFunctions.decrypt(client_email, key1);
				Statement stmt=conn.createStatement();  
				ResultSet rs=stmt.executeQuery("select locked,username from lookup where filename = '" + decrypt_client_filename +"';");
				if(rs.next()) {
					if(rs.getString(1).equals("Y") && !rs.getString(2).equals(client_username))	{
						rtc.setLockstatus("N");
						reply= rtc.getJsonString();
					}
					else {
						String query = "update lookup set locked = 'Y' where filename = '" + decrypt_client_filename +"';  ";
						PreparedStatement preparedStmt = conn.prepareStatement(query);
						preparedStmt.execute();
						rtc.setLockstatus("Y");
						reply=  rtc.getJsonString();
					}
				}
				else {
					String query = " insert into lookup values (?,?,?,'Y')";
					PreparedStatement preparedStmt = conn.prepareStatement(query);
					preparedStmt.setString (1, client_username);
					preparedStmt.setString (2, decrypt_client_filename);
					preparedStmt.setString (3, decrypt_client_email);
					preparedStmt.execute();
					rtc.setLockstatus("Y");
					reply=  rtc.getJsonString();
				}
			}else {	
				rtc.setAuthstatus("N");
				reply=  rtc.getJsonString();
			}
		}catch(SQLException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return reply;

	}


	@POST
	@Consumes({"application/json"})
	@Path("/releaseLock")
	public String release(String param1){
		PropertyStore.loadProperties();
		String reply = null;
		LockFileRequest lr = new LockFileRequest();
		lr = lr.getClassFromJson(param1);
		String client_filename = lr.getFilename();
		String client_username = lr.getUsername();
		String client_token = lr.getToken();
		AuthRequest lresponse = new AuthRequest();
		lresponse.setToken(client_token);
		lresponse.setEncryptedUsername(client_username);
		String jsonstr = lresponse.getJsonString();
		HelperFunctions hf = new HelperFunctions();
		String resp = hf.sendAuthRequest(jsonstr);
		AuthResponse as = new AuthResponse();
		as=as.getClassFromJson(resp);
		String key1 = as.getKey1();
		try {
			String auth = as.getAuthstatus();
			Connection conn = hf.sqlconnect();
			LockFileResponse rtc = new LockFileResponse();
			if (auth.equals("Y")) {
				String decrypt_client_filename = SecurityFunctions.decrypt(client_filename, key1);
				Statement stmt=conn.createStatement();
				ResultSet rs=stmt.executeQuery("select locked from lookup where filename = '" + decrypt_client_filename +"';");
				if(rs.next()) {
					String query = "update lookup set locked = 'N' where filename = '" + decrypt_client_filename +"';  ";
					PreparedStatement preparedStmt = conn.prepareStatement(query);
					preparedStmt.execute();
					rtc.setReleaseStatus("0");
					reply=  rtc.getJsonString();
				}else{
					rtc.setReleaseStatus("0");
					reply=  rtc.getJsonString();
				}
			}
			else {	
				rtc.setAuthstatus("N");
				reply=  rtc.getJsonString();
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
			LockFileResponse rtc = new LockFileResponse();
			rtc.setReleaseStatus("1");
			reply=  rtc.getJsonString();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return reply;
	}
	/*
	 * Created only to test if  server is up and running
	 */
	@POST
	@Consumes({"application/json"})
	@Path("/test")
	public String testServer(String input) {
		return "up and running";
	}

}
