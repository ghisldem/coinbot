package org.gh.coinbot.service.impl;

import java.util.List;

import org.gh.coinbot.entities.Currency;
import org.gh.coinbot.entities.Market;
import org.gh.coinbot.repositories.MarketRepository;
import org.gh.coinbot.service.CurrencyService;
import org.gh.coinbot.service.MarketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class MarketServiceImpl implements MarketService {

	@Autowired
	private MarketRepository marketRepository;
	@Autowired
	private CurrencyService currencyService;
	
	@Override
	public Market create(Market market) {
		
		if (marketRepository.exists(market.getMarketName())) {
			this.update(market);
		}
		
		if (market.getBaseCurrency()!=null && market.getMarketCurrency()!=null) {
			Currency currencyBase = currencyService.getbyId(market.getBaseCurrency());
			Currency currencyMarket = currencyService.getbyId(market.getMarketCurrency());
			
			market.setCurrencyBase(currencyBase);
			market.setCurrencyMarket(currencyMarket);
			
			marketRepository.save(market);
			
		}else {
			//exception
		}
		
		
		return null;
	}

	@Override
	public Market update(Market market) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void remove(String id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Market getbyId(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<Market> getAll(int page, int size) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Market> getMarketsByCurrencyBase(String currencyBase) {
		// TODO Auto-generated method stub
		return marketRepository.getMarketsByCurrencyBase(currencyBase);
	}

	
}
