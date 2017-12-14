package helper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;

import messages.PropertyStore;

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
	public String sendLoginRequest(String input) {
		String url = PropertyStore.serverUrl+PropertyStore.loginUrl;
		String type = "Login";
		return connection(url,input,type);

	}
	public String sendReadRequest(String input, String serverip) {
		String url = serverip+ PropertyStore.readfileUrl;
		String type = "ReadFile";
		return connection(url,input,type);
	}
	public String getDirectoryInfo(String input) {
		String url = PropertyStore.directoryServerUrl+PropertyStore.directoryinfoUrl;
		String type = "GetDirectoryInfo";
		return connection(url,input,type);
	}
	public String sendLockRequest(String input) {
		String url = PropertyStore.lockServerUrl+PropertyStore.lockingUrl;
		String type = "GetLock";
		return connection(url,input,type);
	}
	public String sendWriteRequest(String input, String serverurl) {
		String url = serverurl+ PropertyStore.writefileUrl;
		String type = "WriteFile";
		return connection(url,input,type);
	}

}
