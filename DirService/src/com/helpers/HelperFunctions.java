package com.helpers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
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
		//TODO Query db for filename and remove hardcoding
		
		HashMap<String, String> filestats = new HashMap<String, String>();
		filestats.put("serverurl", "http://127.0.0.1:8083/");
		filestats.put("directory", "Smarth\\");
		return filestats;
	}


}
