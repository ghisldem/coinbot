package org.gh.coinbot.externalapi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Scanner;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.gh.coinbot.util.EncryptionUtility;

public class Bittrex {

	public static final String ORDER_LIMIT = "LIMIT", ORDER_MARKET = "MARKET";
	public static final String TRADE_BUY = "BUY", TRADE_SELL = "SELL";
	public static final String TIMEINEFFECT_GOOD_TIL_CANCELLED = "GOOD_TIL_CANCELLED", TIMEINEFFECT_IMMEDIATE_OR_CANCEL = "IMMEDIATE_OR_CANCEL", TIMEINEFFECT_FILL_OR_KILL = "FILL_OR_KILL";
	public static final String CONDITION_NONE = "NONE", CONDITION_GREATER_THAN = "GREATER_THAN", CONDITION_LESS_THAN = "LESS_THAN", CONDITION_STOP_LOSS_FIXED = "STOP_LOSS_FIXED", CONDITION_STOP_LOSS_PERCENTAGE = "STOP_LOSS_PERCENTAGE";
	private static final Exception InvalidStringListException = new Exception("Must be in key-value pairs");
	private final String API_VERSION = "2.0", INITIAL_URL = "https://bittrex.com/api/";
	private final String METHOD_PUBLIC = "pub", METHOD_KEY = "key";
	private final String MARKET = "market", MARKETS = "markets", CURRENCY = "currency", CURRENCIES = "currencies", BALANCE = "balance", ORDERS = "orders";
	private final String encryptionAlgorithm = "HmacSHA512";
	private String apikey = "";
	private String secret = "";

	public Bittrex(String apikey, String secret) {

		this.apikey = apikey;
		this.secret = secret;
	}

	public Bittrex() {

	}

	public void setAuthKeysFromTextFile(String textFile) { // Add the text file containing the key & secret in the same path as the source code

		try (Scanner scan = new Scanner(getClass().getResourceAsStream(textFile))) {

			String apikeyLine = scan.nextLine(), secretLine = scan.nextLine();

			apikey = apikeyLine.substring(apikeyLine.indexOf("\"") + 1, apikeyLine.lastIndexOf("\""));
			secret = secretLine.substring(secretLine.indexOf("\"") + 1, secretLine.lastIndexOf("\""));

		} catch (NullPointerException | IndexOutOfBoundsException e) {

			System.err.println("Text file not found or corrupted - please attach key & secret in the format provided.");
		}
	}

	public Response getMarketSummaries() { // Returns a 24-hour summary of all markets

		return getResponse(METHOD_PUBLIC, MARKETS, "getmarketsummaries");
	}

	public Response getCurrencies() { // Returns all currencies currently on Bittrex with their metadata

		return getResponse(METHOD_PUBLIC, CURRENCIES, "getcurrencies");
	}

	public Response getWalletHealth() { // Returns wallet health

		return getResponse(METHOD_PUBLIC, CURRENCIES, "getwallethealth");
	}

	public Response getBalanceDistribution(String currency) { // Returns the balance distribution for a specific currency

		return getResponse(METHOD_PUBLIC, CURRENCY, "getbalancedistribution", returnCorrectMap("currencyname", currency));
	}

	public Response getMarketSummary(String market) { // Returns a 24-hour summar for a specific market

		return getResponse(METHOD_PUBLIC, MARKET, "getmarketsummary", returnCorrectMap("marketname", market));
	}
	
	public Response getTicker(String market) { // Returns the current tick values for a market.

		return getResponse(METHOD_PUBLIC, MARKET, "getticker", returnCorrectMap("marketname", market));
	}

	public Response getMarketOrderBook(String market) { // Returns the orderbook for a specific market

		return getResponse(METHOD_PUBLIC, MARKET, "getmarketorderbook", returnCorrectMap("marketname", market));
	}

	public Response getMarketHistory(String market) { // Returns latest trades that occurred for a specific market

		return getResponse(METHOD_PUBLIC, MARKET, "getmarkethistory", returnCorrectMap("marketname", market));
	}

	public Response getMarkets() { // Returns all markets with their metadata

		return getResponse(METHOD_PUBLIC, MARKETS, "getmarkets");
	}

	public Response getOrder(String orderId) { // Returns information about a specific order (by UUID)

		return getResponse(METHOD_KEY, ORDERS, "getorder", returnCorrectMap("orderid", orderId));
	}

	public Response getOpenOrders() { // Returns all your currently open orders

		return getResponse(METHOD_KEY, ORDERS, "getopenorders");
	}

	public Response getOrderHistory() { // Returns all of your order history

		return getResponse(METHOD_KEY, ORDERS, "getorderhistory");
	}

	public Response cancelOrder(String orderId) { // Cancels a specific order based on its order's UUID.

		return getResponse(METHOD_KEY, MARKET, "tradecancel", returnCorrectMap("orderid", orderId));
	}

	public Response getOpenOrders(String market) { // Returns your currently open orders in a specific market

		return getResponse(METHOD_KEY, MARKET, "getopenorders", returnCorrectMap("marketname", market));
	}

	public Response getOrderHistory(String market) { // Returns your order history in a specific market

		return getResponse(METHOD_KEY, MARKET, "getorderhistory", returnCorrectMap("marketname", market));
	}

	public Response getBalances() { // Returns all current balances

		return getResponse(METHOD_KEY, BALANCE, "getbalances");
	}

	public Response getBalance(String currency) { // Returns the balance of a specific currency

		return getResponse(METHOD_KEY, BALANCE, "getbalance", returnCorrectMap("currencyname", currency));
	}

	public Response getPendingWithdrawals(String currency) { // Returns pending withdrawals for a specific currency

		return getResponse(METHOD_KEY, BALANCE, "getpendingwithdrawals", returnCorrectMap("currencyname", currency));
	}

	public Response getPendingWithdrawals() { // Returns all pending withdrawals

		return getPendingWithdrawals("");
	}

	public Response getWithdrawalHistory(String currency) { // Returns your withdrawal history for a specific currency

		return getResponse(METHOD_KEY, BALANCE, "getwithdrawalhistory", returnCorrectMap("currencyname", currency));
	}

	public Response getWithdrawalHistory() { // Returns your whole withdrawal history

		return getWithdrawalHistory("");
	}

	public Response getPendingDeposits(String currency) { // Returns pending deposits for a specific currency

		return getResponse(METHOD_KEY, BALANCE, "getpendingdeposits", returnCorrectMap("currencyname", currency));
	}

	public Response getPendingDeposits() { // Returns pending deposits for a specific currency

		return getPendingDeposits("");
	}

	public Response getDepositHistory(String currency) { // Returns your deposit history for a specific currency

		return getResponse(METHOD_KEY, BALANCE, "getdeposithistory", returnCorrectMap("currencyname", currency));
	}

	public Response getDepositHistory() { // Returns your whole deposit history

		return getDepositHistory("");
	}

	public Response getDepositAddress(String currency) { // Returns your deposit address for a specific currency

		return getResponse(METHOD_KEY, BALANCE, "getdepositaddress", returnCorrectMap("currencyname", currency));
	}

	public Response generateDepositAddress(String currency) { // Generates a new deposit address for a specific currency

		return getResponse(METHOD_KEY, BALANCE, "generatedepositaddress", returnCorrectMap("currencyname", currency));
	}

	public Response withdraw(String currency, String amount, String address) { // Withdraws a specific amount of a certain currency to the specified address

		return getResponse(METHOD_KEY, BALANCE, "withdrawcurrency", returnCorrectMap("currencyname", currency, "quantity", amount, "address", address));
	}

	public Response placeOrder(String tradeType, String market, String orderType, String quantity, String rate, String timeInEffect, String conditionType, String target) { // Places a buy/sell order with these specific conditions (target only required if a condition is in place)

		String method = null;

		if(tradeType.equals(TRADE_BUY))

			method = "tradebuy";

		else if(tradeType.equals(TRADE_SELL))

			method = "tradesell";

		if(conditionType.equals(CONDITION_NONE)) // Ignore target if the condition is none.

			return placeNonConditionalOrder(tradeType, market, orderType, quantity, rate, timeInEffect);

		return getResponse(METHOD_KEY, MARKET, method, returnCorrectMap("marketname", market, "ordertype", orderType, "quantity", quantity, "rate", rate, "timeineffect", timeInEffect, "conditiontype", conditionType, "target", target));
	}

	public Response placeNonConditionalOrder(String tradeType, String market, String orderType, String quantity, String rate, String timeInEffect) { // Used for non-conditional orders

		return placeOrder(tradeType, market, orderType, quantity, rate, timeInEffect, CONDITION_NONE, "0");
	}

	public void setSecret(String secret) {

		this.secret = secret;
	}

	public void setKey(String apikey) {

		this.apikey = apikey;
	}

	private HashMap<String, String> returnCorrectMap(String...parameters) { // Handles the exception of the generateHashMapFromStringList() method gracefully as to not have an excess of try-catch statements

		HashMap<String, String> map = null;

		try {

			map = generateHashMapFromStringList(parameters);

		} catch (Exception e) {

			e.printStackTrace();
		}

		return map;
	}

	private HashMap<String, String> generateHashMapFromStringList(String...strings) throws Exception { // Method to easily create a HashMap from a list of Strings

		if(strings.length % 2 != 0)

			throw InvalidStringListException;

		HashMap<String, String> map = new HashMap<String, String>();

		for(int i = 0; i < strings.length; i += 2) // Each key will be i, with the following becoming its value

			map.put(strings[i], strings[i + 1]);

		return map;
	}

	private Response getResponse(String type, String methodGroup, String method) {

		return getResponse(type, methodGroup, method, new HashMap<String, String>());
	}

	private Response getResponse(String type, String methodGroup, String method, HashMap<String, String> parameters) {

		return getResponseBody(generateUrl(type, methodGroup, method, parameters));
	}

	private String generateUrl(String type, String methodGroup, String method, HashMap<String, String> parameters) {

		String url = INITIAL_URL;

		url += "v" + API_VERSION + "/";
		url += type + "/";
		url += methodGroup + "/";
		url += method;

		url += generateUrlParameters(parameters);

		return url;
	}

	private String generateUrlParameters(HashMap<String, String> parameters) { // Returns a String with the key-value pairs formatted for URL

		String urlAttachment = "?";

		Object[] keys = parameters.keySet().toArray();

		for(Object key : keys)

			urlAttachment += key.toString() + "=" + parameters.get(key) + "&";

		return urlAttachment;
	}

	private Response getResponseBody(String url) {

		Response response = null;
		boolean publicRequest = true;

		if(!url.substring(url.indexOf("v" + API_VERSION)).contains("/" + METHOD_PUBLIC + "/")) { // Only attach apikey & nonce if it is not a public method

			url += "apikey=" + apikey + "&nonce=" + EncryptionUtility.generateNonce();
			publicRequest = false;
		}

		try {

			HttpClient client = HttpClientBuilder.create().build();
			HttpGet request = new HttpGet(url);
System.out.println("url = " +url);
			if(!publicRequest)

				request.addHeader("apisign", EncryptionUtility.calculateHash(secret, url, encryptionAlgorithm)); // Attaches signature as a header

			HttpResponse httpResponse = client.execute(request);

			int responseCode = httpResponse.getStatusLine().getStatusCode();

			BufferedReader reader = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent()));

			StringBuffer resultBuffer = new StringBuffer();
			String line = "";
			
			while ((line = reader.readLine()) != null)

				resultBuffer.append(line);

			response = createResposeFromUrlResponse(resultBuffer.toString());
			response.setResponseCode(responseCode);

		} catch (IOException e) {

			e.printStackTrace();
		}

		return response;
	}
	
	private Response createResposeFromUrlResponse(String urlResponse) { // Creates a new Response object with the fields found in the result
		
		String successString = "\"success\":";
		int indexOfSuccessString = urlResponse.indexOf(successString) + successString.length();
		String strSuccess = urlResponse.substring(indexOfSuccessString, urlResponse.indexOf(",\"", indexOfSuccessString));
		
		String resultString = "\"result\":";
		int indexOfResultString = urlResponse.indexOf(resultString) + resultString.length();
		String result = urlResponse.substring(indexOfResultString, urlResponse.lastIndexOf("}"));
		
		String messageString = "\"message\":\"";
		int indexOfMessageString = urlResponse.indexOf(messageString) + messageString.length();
		String message = urlResponse.substring(indexOfMessageString, urlResponse.indexOf("\"", indexOfMessageString));
		
		boolean success = Boolean.parseBoolean(strSuccess);
		
		return new Response(success, result, message);
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