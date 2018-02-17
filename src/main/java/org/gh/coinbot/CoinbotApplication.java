package org.gh.coinbot;

import java.util.Arrays;
import java.util.List;

import org.gh.coinbot.entities.Currency;
import org.gh.coinbot.externalapi.BittrexV1;
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
	
	public static void main(String[] args) {
		SpringApplication.run(CoinbotApplication.class, args);
	}

	@Override
	public void run(String... arg0) throws Exception {
		// TODO Auto-generated method stub
		
		
		String currencyJson = bittrex.getCurrencies().getResult();
		
		List<Currency> currencies = Arrays.asList(objectMapper.readValue(currencyJson, Currency[].class));
		
		currencies.forEach(c->System.out.println(c.getCurrency()));
		System.out.println(currencies.size());
	}
}
