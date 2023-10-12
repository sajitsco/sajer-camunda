package com.sajits.sajer.library.auth.providers;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.sajits.sajer.core.auth.AuthProviderInterface;
import com.sajits.sajer.core.auth.User;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;


public class Google implements AuthProviderInterface {

    private String clientID = "896013761811-c35cfl2m6jhh4d7p387rhj7bk5droqo6.apps.googleusercontent.com";
	private String response_type = "code";
	private String scope = "https://www.googleapis.com/auth/userinfo.profile https://www.googleapis.com/auth/userinfo.email";
	private String access_type = "offline";//&prompt=consent";
	private String redirect_uri = "p/auth/redirect";

    public String login(String state){
        String googleAPI = "https://accounts.google.com/o/oauth2/v2/auth?state=" + state + "&client_id="
            + clientID + "&response_type=" + response_type + "&scope="+ scope 
            + "&access_type=" + access_type + "&redirect_uri=" + getRedirectHost(state) + redirect_uri;

        return googleAPI;
    }

    public User redirect(String code, String state)  throws IOException  {

        CloseableHttpClient client = HttpClients.createDefault();
		 
        HttpPost httpPost = new HttpPost("https://www.googleapis.com/oauth2/v4/token");

		ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("client_id", clientID));
		params.add(new BasicNameValuePair("client_secret", "KKGtn25DkilwPWZJGytySIGW"));
		params.add(new BasicNameValuePair("code", code));
		params.add(new BasicNameValuePair("redirect_uri", getRedirectHost(state) + redirect_uri));
		params.add(new BasicNameValuePair("grant_type", "authorization_code"));
		httpPost.setEntity(new UrlEncodedFormEntity(params));

		CloseableHttpResponse response = client.execute(httpPost);

		if( response.getStatusLine().getStatusCode() != 200) {
			System.out.println("Error on https://www.googleapis.com/oauth2/v4/token");
			return null;
		}

        String inputLine = "";
		String content = "";
		BufferedReader br = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
		try {
			  while ((inputLine = br.readLine()) != null) {
					 System.out.println(inputLine);
					 content += inputLine;
			  }
			  br.close();
		 } catch (IOException e) {
			  e.printStackTrace();
		 }

		 
		ObjectMapper objectMapper = new ObjectMapper();
		GoogleApiToken gat = objectMapper.readValue(content, GoogleApiToken.class);

		String[] chunks = gat.getId_token().split("\\.");
		Base64.Decoder decoder = Base64.getDecoder();
		String header = new String(decoder.decode(chunks[0]));
		String payload = new String(decoder.decode(chunks[1]));
		System.out.println(header);
		System.out.println(payload);

		GoogleUserToken gut = objectMapper.readValue(payload, GoogleUserToken.class);


		User su = new User();
		su.setEmail(gut.getEmail());
		su.setName(gut.getName());
		su.setFname(gut.getGiven_name());
		su.setLname(gut.getFamily_name());
		su.setPicture(gut.getPicture());

		return su;


    }

	public int validateToken(String token) throws IOException, MalformedURLException {
		URL url = new URL("https://www.googleapis.com/oauth2/v3/tokeninfo");
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod("GET");
		Map<String, String> parameters = new HashMap<>();
		parameters.put("access_token", token);

		con.setDoOutput(true);
		DataOutputStream out = new DataOutputStream(con.getOutputStream());
		out.writeBytes(getParamsString(parameters));
		out.flush();
		out.close();
		int status = con.getResponseCode();
		con.disconnect();
		return status;
	}
	

	public static String getParamsString(Map<String, String> params) throws UnsupportedEncodingException{
		StringBuilder result = new StringBuilder();

		for (Map.Entry<String, String> entry : params.entrySet()) {
		  result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
		  result.append("=");
		  result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
		  result.append("&");
		}

		String resultString = result.toString();
		return resultString.length() > 0
		  ? resultString.substring(0, resultString.length() - 1)
		  : resultString;
	}

	private static String getRedirectHost(String state){
		String redirectHost = state.substring(0, state.indexOf("-ciid-"));
		if( redirectHost.indexOf("localhost:3333") > -1 )
			redirectHost = "http://localhost:8080/";
		return redirectHost;
	}
}
