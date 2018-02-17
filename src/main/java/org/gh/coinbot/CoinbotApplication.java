package org.gh.coinbot;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.gh.coinbot.entities.Market;
import org.gh.coinbot.entities.MarketTickValues;
import org.gh.coinbot.externalapi.BittrexV1;
import org.gh.coinbot.repositories.MarketRepository;
import org.gh.coinbot.repositories.MarketTickValuesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootApplication
public class CoinbotApplication implements CommandLineRunner{
	@Autowired
	private BittrexV1 bittrex;
	@Autowired
	private ObjectMapper objectMapper;
	@Autowired
	private MarketRepository marketRepository;
	@Autowired
	private MarketTickValuesRepository marketTickValuesRepository;
	
	
	public static void main(String[] args) {
		SpringApplication.run(CoinbotApplication.class, args);
	}

	@Override
	public void run(String... arg0) throws Exception {
		// TODO Auto-generated method stub
		
		
		String marketJson = bittrex.getMarkets().getResult();
		
		List<Market> markets = Arrays.asList(objectMapper.readValue(marketJson, Market[].class));
		
		markets.forEach(m->marketRepository.save(m));
		
		Market market = markets.get(0);
		
		for(int i = 0 ; i<50;i++) {
			
			String tickerJson = bittrex.getTicker(market.getMarketName()).getResult();
			
			MarketTickValues marketTickValues = objectMapper.readValue(tickerJson, MarketTickValues.class);
			
			marketTickValues.setMarket(market);
			marketTickValues.setUpdateDate(new Date(System.currentTimeMillis()));
			
			
			marketTickValuesRepository.save(marketTickValues);
			
			Thread.sleep(4000);
		}
		
		
		
	}
}
