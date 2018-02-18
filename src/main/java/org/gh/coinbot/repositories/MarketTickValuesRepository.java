package org.gh.coinbot.repositories;

import org.gh.coinbot.entities.MarketTickValues;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MarketTickValuesRepository extends JpaRepository<MarketTickValues, Long>{
	
}
