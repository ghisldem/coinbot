package org.gh.coinbot.service;

import org.gh.coinbot.entities.Currency;
import org.springframework.data.domain.Page;

public interface CurrencyService {
	
	public Currency create (Currency currency);
	
	public Currency update (Currency currency);
	
	public void remove (String id);
	
	public Currency getbyId (String id);
	
	public Page<Currency> getAll(int page, int size);
	
	

}
