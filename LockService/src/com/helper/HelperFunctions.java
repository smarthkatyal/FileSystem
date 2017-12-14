package com.helper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;

import com.messages.PropertyStore;



public class HelperFunctions {

	public  Connection sqlconnect() {
		Connection con= null;
		if(!PropertyStore.loadstatus.equals("1"))
			PropertyStore.loadProperties();
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con=DriverManager.getConnection(  
					"jdbc:mysql://"+PropertyStore.dbip+":"+
							PropertyStore.dbport+"/"+PropertyStore.dbname+
							"?useSSL=false",
							PropertyStore.dbusername,
							PropertyStore.dbpassword);
		} 

		catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return con;
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
	public String sendAuthRequest(String input) {
		String serverurl = PropertyStore.authserverip+PropertyStore.aurthserverurl;
		String type = "AuthRequest";
		return connection(serverurl,input,type);
	}

}
