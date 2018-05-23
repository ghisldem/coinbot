package org.gh.coinbot.service;

import java.util.List;

import org.gh.coinbot.entities.Market;
import org.springframework.data.domain.Page;

public interface MarketService {

	public Market create (Market market);
	
	public Market update (Market market);
	
	public void remove (String id);
	
	public Market getbyId (String id);
	
	public Page<Market> getAll(int page, int size);
	
	public List<Market> getMarketsByCurrencyBase (String currencyBase);
}
