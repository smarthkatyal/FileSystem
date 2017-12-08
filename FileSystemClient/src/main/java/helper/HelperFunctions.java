package helper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;

import messages.PropertyStore;

public class HelperFunctions {

	public String connection(String url, String input) {
		String output="";
		String reply="";
		try {
			CloseableHttpClient httpClient = HttpClientBuilder.create().build();
			HttpPost postRequest = new HttpPost(url);
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

		}catch(MalformedURLException e) {

			e.printStackTrace();

		}catch(IOException e) {

			e.printStackTrace();

		}
		return reply;
	}
	public String sendLoginRequest(String input) {
		String url = PropertyStore.serverUrl+PropertyStore.loginUrl;
		return connection(url,input);

	}
	public String sendReadRequest(String input) {
		String url = PropertyStore.serverUrl+PropertyStore.readfileUrl;
		return connection(url,input);
	}

}
