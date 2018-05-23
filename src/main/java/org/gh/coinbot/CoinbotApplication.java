package org.gh.coinbot;

import java.util.List;

import org.gh.coinbot.entities.Market;
import org.gh.coinbot.service.MarketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CoinbotApplication implements CommandLineRunner{

	@Autowired
	private LaunchProject launchProject;
	@Autowired
	private MarketService marketService;
		
	public static void main(String[] args) {
		SpringApplication.run(CoinbotApplication.class, args);
	}

	@Override
	public void run(String... arg0) throws Exception {
		// TODO Auto-generated method stub
		
		launchProject.preloadData();
		
		List<Market> markets2 = marketService.getMarketsByCurrencyBase("BTC");
		
		
		markets2.forEach(m->System.out.println(m.getMarketName()));
		System.out.println("nombre de marché : "+markets2.size());

	}
}
