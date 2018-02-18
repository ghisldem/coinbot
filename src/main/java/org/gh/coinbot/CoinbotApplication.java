package org.gh.coinbot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CoinbotApplication implements CommandLineRunner{


	@Autowired
	private LaunchProject launchProject;
	
	
	public static void main(String[] args) {
		SpringApplication.run(CoinbotApplication.class, args);
	}

	@Override
	public void run(String... arg0) throws Exception {
		// TODO Auto-generated method stub
		
		launchProject.preloadData();

	}
}
