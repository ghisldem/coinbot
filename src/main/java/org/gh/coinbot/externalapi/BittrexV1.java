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
import org.springframework.stereotype.Component;

/**
 * info : https://bittrex.com/home/api
 * 
 * @author GHISLAIN
 *
 */
@Component
public class BittrexV1 {

	public static final String ORDER_LIMIT = "LIMIT", ORDER_MARKET = "MARKET";
	public static final String TRADE_BUY = "BUY", TRADE_SELL = "SELL";
	public static final String TIMEINEFFECT_GOOD_TIL_CANCELLED = "GOOD_TIL_CANCELLED",
			TIMEINEFFECT_IMMEDIATE_OR_CANCEL = "IMMEDIATE_OR_CANCEL", TIMEINEFFECT_FILL_OR_KILL = "FILL_OR_KILL";
	public static final String CONDITION_NONE = "NONE", CONDITION_GREATER_THAN = "GREATER_THAN",
			CONDITION_LESS_THAN = "LESS_THAN", CONDITION_STOP_LOSS_FIXED = "STOP_LOSS_FIXED",
			CONDITION_STOP_LOSS_PERCENTAGE = "STOP_LOSS_PERCENTAGE";
	private static final Exception InvalidStringListException = new Exception("Must be in key-value pairs");
	private final String API_VERSION = "1.1", INITIAL_URL = "https://bittrex.com/api/";
	private final String METHOD_PUBLIC = "public", METHOD_KEY = "key", METHOD_MARKET = "market";
	private final String MARKET = "market", MARKETS = "markets", CURRENCY = "currency", CURRENCIES = "currencies",
			BALANCE = "balance", ORDERS = "orders", ACCOUNT = "account";
	private final String encryptionAlgorithm = "HmacSHA512";
	private String apikey = "";
	private String secret = "";

	public BittrexV1(String apikey, String secret) {

		this.apikey = apikey;
		this.secret = secret;
	}

	public BittrexV1() {

	}

	public void setAuthKeysFromTextFile(String textFile) { // Add the text file containing the key & secret in the same
															// path as the source code

		try (Scanner scan = new Scanner(getClass().getResourceAsStream(textFile))) {

			String apikeyLine = scan.nextLine(), secretLine = scan.nextLine();

			apikey = apikeyLine.substring(apikeyLine.indexOf("\"") + 1, apikeyLine.lastIndexOf("\""));
			secret = secretLine.substring(secretLine.indexOf("\"") + 1, secretLine.lastIndexOf("\""));

		} catch (NullPointerException | IndexOutOfBoundsException e) {

			System.err.println("Text file not found or corrupted - please attach key & secret in the format provided.");
		}
	}

	/**
	 * 
	 * @return response public api, get the open and available trading markets at
	 *         Bittrex along with other meta data.
	 */
	public Response getMarkets() {

		return getResponse(METHOD_PUBLIC, "getmarkets");
	}

	/**
	 * 
	 * @return response public api, get all supported currencies at Bittrex along
	 *         with other meta data
	 */
	public Response getCurrencies() {

		return getResponse(METHOD_PUBLIC, "getcurrencies");
	}

	/**
	 * 
	 * @param market,
	 *            required a string literal for the market (ex: BTC-LTC)
	 * @return response public api, get the current tick values for a market.
	 */
	public Response getTicker(String market) {

		return getResponse(METHOD_PUBLIC, "getticker", returnCorrectMap("market", market));
	}

	/**
	 * 
	 * @return response public api, get the last 24 hour summary of all active
	 *         exchanges.
	 */
	public Response getMarketSummaries() { // Returns a 24-hour summary of all markets

		return getResponse(METHOD_PUBLIC, "getmarketsummaries");
	}

	/**
	 * 
	 * @param market,
	 *            required a string literal for the market (ex: BTC-LTC)
	 * @return response public api, get the last 24 hour summary of all active
	 *         exchanges
	 */
	public Response getMarketSummary(String market) { // Returns a 24-hour summar for a specific market

		return getResponse(METHOD_PUBLIC, "getmarketsummary", returnCorrectMap("market", market));
	}

	/**
	 * Used to get retrieve the orderbook for a given market
	 * 
	 * @param market,
	 *            required a string literal for the market (ex: BTC-LTC)
	 * @param typeOrder,
	 *            required buy, sell or both to identify the type of orderbook to
	 *            return.
	 * @return Response - List of orders buy & sell
	 */

	public Response getOrderBook(String market, String typeOrder) {

		return getResponse(METHOD_PUBLIC, "getorderbook", returnCorrectMap("market", market, "type", typeOrder));
	}

	/**
	 * Used to retrieve the latest trades that have occured for a specific market.
	 * 
	 * @param market,
	 *            required a string literal for the market (ex: BTC-LTC)
	 * @return Response - List of trades objects
	 */

	public Response getMarketHistory(String market) {

		return getResponse(METHOD_PUBLIC, "getmarkethistory", returnCorrectMap("market", market));
	}

	/**
	 * Used to place a buy order in a specific market. Use buylimit to place limit
	 * orders. Make sure you have the proper permissions set on your API keys for
	 * this call to work
	 * 
	 * @param market,
	 *            required a string literal for the market (ex: BTC-LTC)
	 * @param quantity,
	 *            required the amount to purchase
	 * @param rate,
	 *            required the rate at which to place the order.
	 * 
	 * @return Response - Returns you the order uuid (code of the order)
	 */
	public Response setOrderBuyLimit(String market, String quantity, String rate) {
		return getResponse(MARKET, "buylimit", returnCorrectMap("market", market, "quantity", quantity, "rate", rate));
	}

	/**
	 * Used to place an sell order in a specific market. Use selllimit to place
	 * limit orders. Make sure you have the proper permissions set on your API keys
	 * for this call to work
	 * 
	 * @param market,
	 *            required a string literal for the market (ex: BTC-LTC)
	 * @param quantity,
	 *            required the amount to purchase
	 * @param rate,
	 *            required the rate at which to place the order
	 * @return Response - Returns you the order uuid (code of the order)
	 */

	public Response setOrderSellLimit(String market, String quantity, String rate) {
		return getResponse(MARKET, "selllimit", returnCorrectMap("market", market, "quantity", quantity, "rate", rate));
	}

	/**
	 * Used to cancel a buy or sell order.
	 * 
	 * @param uuid
	 * @return Response - Returns you the order uuid
	 */
	public Response cancelOrder(String uuid) {

		return getResponse(MARKET, "cancel", returnCorrectMap("orderid", uuid));
	}

	/**
	 * Get all orders that you currently have opened.
	 * 
	 * @return Response - Returns list of open order
	 */
	public Response getOpenOrders() {

		return getResponse(MARKET, "getopenorders");
	}

	/**
	 * Get all orders that you currently have opened. A specific market can be
	 * requested
	 * 
	 * @param market
	 * @return your currently open orders in a specific market
	 */
	public Response getOpenOrders(String market) {

		return getResponse(MARKET, "getopenorders", returnCorrectMap("marketname", market));
	}

	/**
	 * Used to retrieve all balances from your account
	 * 
	 * @return Response - Returns list of balances from your account
	 */

	public Response getBalances() { // Returns all current balances

		return getResponse(ACCOUNT, "getbalances");
	}

	/**
	 * Used to retrieve the balance from your account for a specific currency.
	 * 
	 * @param currency,
	 *            required a string literal for the currency (ex: LTC)
	 * @return Response - Returns one balance from your account for a specific
	 *         currency.
	 */

	public Response getBalance(String currency) {

		return getResponse(ACCOUNT, "getbalance", returnCorrectMap("currencyname", currency));
	}

	/**
	 * Used to retrieve or generate an address for a specific currency. If one does
	 * not exist, the call will fail and return ADDRESS_GENERATING until one is
	 * available.
	 * 
	 * @param currency,
	 *            required a string literal for the currency (ie. BTC)
	 * @return Response - Returns an address according the currency
	 */
	public Response getDepositAddress(String currency) {

		return getResponse(ACCOUNT, "getdepositaddress", returnCorrectMap("currency", currency));
	}

	/**
	 * Used to withdraw funds from your account. note: please account for txfee.
	 * 
	 * @param currency
	 * @param quantity
	 * @param address
	 * @return Response - Returns you the withdrawal uuid
	 */
	public Response withdraw(String currency, String quantity, String address) {

		return getResponse(ACCOUNT, "withdraw",
				returnCorrectMap("currency", currency, "quantity", quantity, "address", address));
	}

	/**
	 * Used to retrieve a single order by uuid.
	 * 
	 * @param uuid,
	 *            required the uuid of the buy or sell order
	 * 
	 * @return Response - Returns one order
	 */
	public Response getOrder(String uuid) { // Returns information about a specific order (by UUID)

		return getResponse(ACCOUNT, "getorder", returnCorrectMap("uuid", uuid));
	}
	/**
	 * Used to retrieve your order history.
	 * @return Response - Returns list of orders
	 */

	public Response getOrderHistory() { // Returns all of your order history

		return getResponse(ACCOUNT, "getorderhistory");
	}

	/**
	 * Used to retrieve your order history for one market.
	 * @param market
	 * @return Response - Returns list of orders for a specific market.
	 */
	public Response getOrderHistory(String market) { // Returns your order history in a specific market

		return getResponse(ACCOUNT, "getorderhistory", returnCorrectMap("market", market));
	}
	
	/**
	 * Used to retrieve your withdrawal history for a specific currency.
	 * @param currency
	 * @return
	 */

	public Response getWithdrawalHistory(String currency) { 

		return getResponse(ACCOUNT, "getwithdrawalhistory", returnCorrectMap("currencyname", currency));
	}
	
	/**
	 * Used to retrieve your withdrawal history for all currencies.
	 * @return
	 */
	public Response getWithdrawalHistory() {

		return getWithdrawalHistory("");
	}

	public void setSecret(String secret) {

		this.secret = secret;
	}

	public void setKey(String apikey) {

		this.apikey = apikey;
	}

	private HashMap<String, String> returnCorrectMap(String... parameters) { // Handles the exception of the
																				// generateHashMapFromStringList()
																				// method gracefully as to not have an
																				// excess of try-catch statements

		HashMap<String, String> map = null;

		try {

			map = generateHashMapFromStringList(parameters);

		} catch (Exception e) {

			e.printStackTrace();
		}

		return map;
	}

	private HashMap<String, String> generateHashMapFromStringList(String... strings) throws Exception { // Method to
																										// easily create
																										// a HashMap
																										// from a list
																										// of Strings

		if (strings.length % 2 != 0)

			throw InvalidStringListException;

		HashMap<String, String> map = new HashMap<String, String>();

		for (int i = 0; i < strings.length; i += 2) // Each key will be i, with the following becoming its value

			map.put(strings[i], strings[i + 1]);

		return map;
	}

	private Response getResponse(String type, String method) {

		return getResponse(type, method, new HashMap<String, String>());
	}

	private Response getResponse(String type, String method, HashMap<String, String> parameters) {

		return getResponseBody(generateUrl(type, method, parameters));
	}

	private String generateUrl(String type, String method, HashMap<String, String> parameters) {

		String url = INITIAL_URL;

		url += "v" + API_VERSION + "/";
		url += type + "/";
		url += method;

		url += generateUrlParameters(parameters);

		return url;
	}

	private String generateUrlParameters(HashMap<String, String> parameters) { // Returns a String with the key-value
																				// pairs formatted for URL

		String urlAttachment = "?";

		Object[] keys = parameters.keySet().toArray();

		for (Object key : keys)

			urlAttachment += key.toString() + "=" + parameters.get(key) + "&";

		return urlAttachment;
	}

	private Response getResponseBody(String url) {

		Response response = null;
		boolean publicRequest = true;

		if (!url.substring(url.indexOf("v" + API_VERSION)).contains("/" + METHOD_PUBLIC + "/")) { // Only attach apikey
																									// & nonce if it is
																									// not a public
																									// method

			url += "apikey=" + apikey + "&nonce=" + EncryptionUtility.generateNonce();
			publicRequest = false;
		}

		try {

			HttpClient client = HttpClientBuilder.create().build();
			HttpGet request = new HttpGet(url);
			System.out.println("url = " + url);
			if (!publicRequest)

				request.addHeader("apisign", EncryptionUtility.calculateHash(secret, url, encryptionAlgorithm)); // Attaches
																													// signature
																													// as
																													// a
																													// header

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

	private Response createResposeFromUrlResponse(String urlResponse) { // Creates a new Response object with the fields
																		// found in the result

		String successString = "\"success\":";
		int indexOfSuccessString = urlResponse.indexOf(successString) + successString.length();
		String strSuccess = urlResponse.substring(indexOfSuccessString,
				urlResponse.indexOf(",\"", indexOfSuccessString));

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