package org.gh.coinbot;

import java.io.IOException;

import org.gh.coinbot.dto.MarketGetTickerCoinMarkertCapDTO;
import org.gh.coinbot.externalapi.Bittrex;
import org.gh.coinbot.externalapi.CoinMarketCap;
import org.gh.coinbot.service.CurrencyService;
import org.gh.coinbot.service.MarketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class LaunchProject {

	@Autowired
	private MarketService marketService;
	@Autowired
	private CurrencyService currencyService;
	@Autowired
	private Bittrex bittrex;
	@Autowired
	private CoinMarketCap coinMarketCap;
	@Autowired
	private ObjectMapper objectMapper;

	public void preloadData() throws JsonParseException, JsonMappingException, IOException, InterruptedException {
		
		ObjectMapper mapper = new ObjectMapper();
		mapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT,DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
		
		String curenciesJson = bittrex.getTicker("BTC-LTC").getResult();
		
		System.out.println(curenciesJson);

		org.gh.coinbot.externalapi.CoinMarketCap.Response responseMarketCoiMarket = coinMarketCap.getticker("Bitcoin",
				"EUR");

		if (responseMarketCoiMarket.isSuccessful()) {
			String marketCoiMarketJson = responseMarketCoiMarket.getResult();

			
			System.out.println(marketCoiMarketJson);
			
			System.out.println("dernier caractère  : " +marketCoiMarketJson.substring(marketCoiMarketJson.length()-1));

			MarketGetTickerCoinMarkertCapDTO[] marketCapDTO = objectMapper.readValue(marketCoiMarketJson,
					MarketGetTickerCoinMarkertCapDTO[].class);
			
//			.observable.subscribe(marketCurrency ->
//			//this.setMarketName(marketCurrency+ "-" + this.getBaseCurrency() + "-" + "coinMarketCap")
//			{System.out.println("test rxjava : " +marketCurrency);});

			System.out.println(marketCapDTO[0].getBaseCurrency() + " , " + marketCapDTO[0].getValueEuro() + " , " + marketCapDTO[0].getMarketName());
			System.out.println(marketCapDTO[0].getBaseCurrency() + " , " + marketCapDTO[0].getValueEuro() + " , " + marketCapDTO[0].getMarketCurrency());
		}else {
			System.out.println("c'est la merde");
		}


		// bittrex.setAuthKeysFromTextFile("key.txt");
		//
		// String curenciesJson = bittrex.getCurrencies().getResult();
		//
		// List<Currency> currencies =
		// Arrays.asList(objectMapper.readValue(curenciesJson, Currency[].class));
		//
		//
		// currencies.forEach(c->currencyService.create(c));
		//
		//
		// String marketJson = bittrex.getMarkets().getResult();
		//
		// List<Market> markets = Arrays.asList(objectMapper.readValue(marketJson,
		// Market[].class));
		//
		// markets.forEach(m->{
		// m.setPlateform("bittrex");
		// marketService.create(m);
		// });
		//
		// System.out.println(bittrex.getOpenOrders());

		// List<Market> markets2 = marketService.getMarketsByCurrencyBase("BTC");
		//
		//
		// System.out.println(markets2);

		// String market = coinMarketCap.getticker("bitcoin", "eur").getResult();
		// System.out.println(objectMapper.readValue(market, valueType));

		// Currency currencyBTC = currencyService.getbyId("BTC");
		// List<Market> markets2 = (List<Market>) currencyBTC.getMarketsCurrencyBase();
		//
		// markets2.forEach(m->System.out.println(m.getMarketName()));

		// Market market = markets.get(0);
		//
		// for(int i = 0 ; i<50;i++) {
		//
		// String tickerJson = bittrex.getTicker(market.getMarketName()).getResult();
		//
		// MarketTickValues marketTickValues = objectMapper.readValue(tickerJson,
		// MarketTickValues.class);
		//
		// marketTickValues.setMarket(market);
		// marketTickValues.setUpdateDate(new Date(System.currentTimeMillis()));
		//
		//
		// marketTickValuesRepository.save(marketTickValues);
		//
		// Thread.sleep(4000);
		// }

	}

}
