package com.example.samtransbusstopdisplay;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.zip.GZIPInputStream;

public class SamTransAPI {
	private static HttpURLConnection connection;
	private final static String apiKey = ""; // Replace with your own API key
	public static String SamTransAPICall() {
		String agency = "SM"; //samtrans agency code
		String stopID = "341081"; //Stop ID for the CSM Transit Center (can be adapted to any stop within the Bay Area network)
		String apiUrl = "http://api.511.org/transit/StopMonitoring?api_key=" + apiKey + "&agency=" + agency + "&stopCode=" + stopID + "&format=json";

		BufferedReader reader;
		String line;
		StringBuilder responseContent = new StringBuilder();
		try {
			URL url = new URL(apiUrl);
			connection = (HttpURLConnection) url.openConnection();

			connection.setRequestMethod("GET");
			connection.setConnectTimeout(5000);
			//wait up to 5 seconds to connect
			connection.setReadTimeout(5000);
			//wait up to 5 seconds for data

			int status = connection.getResponseCode();
			//System.out.println(status); //prints 200 if connected

			if(status > 299){
				reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
			}else{
				if ("gzip".equals(connection.getHeaderField("Content-Encoding"))) {
					reader = new BufferedReader(new InputStreamReader(new GZIPInputStream(connection.getInputStream())));
				} else {
					reader = new BufferedReader((new InputStreamReader(connection.getInputStream())));
				}
			}
			while((line = reader.readLine()) != null){
				responseContent.append(line);
			}
			reader.close();

		} catch (IOException e) {
			throw new RuntimeException(e);
		} finally {
			connection.disconnect();
		}
		return responseContent.toString();
	}
}
