package org.gh.coinbot.repositories;

import org.gh.coinbot.entities.Currency;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CurrencyRepository extends JpaRepository<Currency, String>{
	
}
