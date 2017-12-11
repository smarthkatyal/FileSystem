package com.helpers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.HashMap;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;

import com.messagetemplates.PropertyStore;


public class HelperFunctions {

	public static Connection sqlconnect() {
		Connection conn= null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/dirservice?useSSL=false","root","password");
		}
		catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}

	public String connection(String url, String input,String type) {
		String output="";
		String reply="";
		try {
			CloseableHttpClient httpClient = HttpClientBuilder.create().build();
			HttpPost postRequest = new HttpPost(url);
			System.out.println("*******\nSending "+type+" Request:\n"+input+"\n*********");
			System.out.println("*******\nSending "+type+" Request to "+url+"\n**********");

			postRequest.addHeader("accept", "application/json");
			StringEntity se = new StringEntity(input.toString());
			se.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
			postRequest.setEntity(se);
			HttpResponse response = httpClient.execute(postRequest);
			if (response.getStatusLine().getStatusCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : " + response.getStatusLine().getStatusCode());
			}
			BufferedReader br = new BufferedReader(new InputStreamReader((response.getEntity().getContent())));
			while ((output = br.readLine()) != null) {
				reply+=output;
			}
			System.out.println("*******\nGot  "+type+" Response:\n"+reply+"\n*********");

		}catch(MalformedURLException e) {

			e.printStackTrace();

		}catch(IOException e) {

			e.printStackTrace();

		}
		return reply;
	}
	public String sendAuthCheckRequest(String input) {
		String url = PropertyStore.serverUrl+PropertyStore.authcheckUrl;
		String type = "AuthCheck";
		return connection(url,input,type);

	}
	public HashMap<String, String> getFileLocation(String filename) {
		HashMap<String, String> filestats = new HashMap<String, String>();
		ResultSet rs;
		try {
			Connection conn = sqlconnect();
			Statement stmt=conn.createStatement();
			rs = stmt.executeQuery("select server,directory from dirservice.filelist where filename like '" + filename +"';");
			if(rs.next())
			{
				System.out.println("\n\n########################\nServer:: "+ rs.getString(1)+ "Dir:: "+ rs.getString(2));
				filestats.put("serverurl", rs.getString(1));
				filestats.put("directory", rs.getString(2));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}



		return filestats;
	}


}
