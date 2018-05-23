package org.gh.coinbot.externalapi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.stereotype.Component;

@Component
public class CoinMarketCap {

	private final String INITIAL_URL = "https://api.coinmarketcap.com/";
	private final String METHOD_TICKER = "ticker", METHOD_GLOBAL = "global";

	public Response getticker(String cryptoCurrency, String realCurrency) {
		return getResponseBody("https://api.coinmarketcap.com/v1/ticker/" + cryptoCurrency + "/?convert=" + realCurrency);
	}

	private Response getResponseBody(String url) {

		Response response = null;
		
		try {

			HttpClient client = HttpClientBuilder.create().build();
			HttpGet request = new HttpGet(url);
			System.out.println("url = " + url);

			HttpResponse httpResponse = client.execute(request);

			int responseCode = httpResponse.getStatusLine().getStatusCode();

			BufferedReader reader = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent()));

			StringBuffer resultBuffer = new StringBuffer();
			String line = "";

			while ((line = reader.readLine()) != null)

				resultBuffer.append(line);
			
			String formattedUrlResponse = this.shapingUrl(resultBuffer.toString());
			response = createResposeFromUrlResponse(formattedUrlResponse);
			response.setResponseCode(responseCode);

		} catch (IOException e) {

			e.printStackTrace();
		}

		return response;
	}

	private Response createResposeFromUrlResponse(String urlResponse) { // Creates a new Response object with the fields
		// found in the result


		String strSuccess = "true";

		String resultString = "";
		int indexOfResultString = urlResponse.indexOf(resultString) + resultString.length();
		String result = urlResponse.substring(indexOfResultString, urlResponse.lastIndexOf("]"));


		String message = null;

		boolean success = Boolean.parseBoolean(strSuccess);

		return new Response(success, result, message);
	}
	
	private String shapingUrl (final String urlResponse) {
		String formattedUrlResponse = urlResponse;

		formattedUrlResponse += "]";

		formattedUrlResponse = formattedUrlResponse.replaceAll("\n", "").replaceAll(" ", "");

		return formattedUrlResponse;
	}
	
	public class Response {

		private boolean success;
		private int responseCode;
		private String result;
		private String message;

		private Response(boolean success, int responseCode, String result, String message) {

			this.success = success;
			this.responseCode = responseCode;
			this.result = result;
			this.message = message;
		}

		private Response(boolean success, String result, String message) {

			this.success = success;
			this.result = result;
			this.message = message;
		}

		private void setResponseCode(int responseCode) {

			this.responseCode = responseCode;
		}

		public boolean isSuccessful() {

			return success;
		}

		public String getResult() {

			return result;
		}

		public String getMessage() {

			return message;
		}

		public int getResponseCode() {

			return responseCode;
		}

		@Override
		public String toString() {

			return result;
		}
	}
}
