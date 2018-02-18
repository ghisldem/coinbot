package org.gh.coinbot;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.gh.coinbot.entities.Currency;
import org.gh.coinbot.entities.Market;
import org.gh.coinbot.externalapi.BittrexV1;
import org.gh.coinbot.service.CurrencyService;
import org.gh.coinbot.service.MarketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class LaunchProject {
	
	@Autowired
	private MarketService marketService;
	@Autowired
	private CurrencyService currencyService;
	@Autowired
	private BittrexV1 bittrex;
	@Autowired
	private ObjectMapper objectMapper;
	
	public void preloadData() throws JsonParseException, JsonMappingException, IOException, InterruptedException {
		String curenciesJson =  bittrex.getCurrencies().getResult();
		
		List<Currency> currencies = Arrays.asList(objectMapper.readValue(curenciesJson, Currency[].class));
		
		
		currencies.forEach(c->currencyService.create(c));

	
		String marketJson = bittrex.getMarkets().getResult();
		
		List<Market> markets = Arrays.asList(objectMapper.readValue(marketJson, Market[].class));
		
		markets.forEach(m->marketService.create(m));
		
		
//		Currency currencyBTC = currencyService.getbyId("BTC");
//		List<Market> markets2 = (List<Market>) currencyBTC.getMarketsCurrencyBase();
//		
//		markets2.forEach(m->System.out.println(m.getMarketName()));
		
//		Market market = markets.get(0);
//		
//		for(int i = 0 ; i<50;i++) {
//			
//			String tickerJson = bittrex.getTicker(market.getMarketName()).getResult();
//			
//			MarketTickValues marketTickValues = objectMapper.readValue(tickerJson, MarketTickValues.class);
//			
//			marketTickValues.setMarket(market);
//			marketTickValues.setUpdateDate(new Date(System.currentTimeMillis()));
//			
//			
//			marketTickValuesRepository.save(marketTickValues);
//			
//			Thread.sleep(4000);
//		}
		
	}
	

}
