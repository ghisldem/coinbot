package org.gh.coinbot.repositories;

import java.util.List;

import org.gh.coinbot.entities.Market;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MarketRepository extends JpaRepository<Market, String>{
	
	@Query("select m from Market m where m.currencyBase.currency =:cc")
	public List<Market> getMarketsByCurrencyBase (@Param("cc")String codeCurrency);
	
}
