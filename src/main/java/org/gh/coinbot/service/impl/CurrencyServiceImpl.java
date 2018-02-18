package org.gh.coinbot.service.impl;

import org.gh.coinbot.entities.Currency;
import org.gh.coinbot.repositories.CurrencyRepository;
import org.gh.coinbot.service.CurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CurrencyServiceImpl implements CurrencyService {

	@Autowired
	private CurrencyRepository currencyRepository;

	@Override
	public Currency create(Currency currency) {
		// TODO Auto-generated method stub
		return currencyRepository.save(currency);
	}

	@Override
	public Currency update(Currency currency) {
		// TODO Auto-generated method stub
		return currencyRepository.save(currency);
	}

	@Override
	public void remove(String id) {
		// TODO Auto-generated method stub
		currencyRepository.delete(id);
	}

	@Override
	public Currency getbyId(String Id) {
		// TODO Auto-generated method stub
		return currencyRepository.findOne(Id);
	}

	@Override
	public Page<Currency> getAll(int page, int size) {
		// TODO Auto-generated method stub
		return currencyRepository.findAll(new PageRequest(page, size));
	}

}
