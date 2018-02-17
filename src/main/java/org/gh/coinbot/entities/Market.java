package org.gh.coinbot.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Market implements Serializable{
	@Id
	@JsonProperty("MarketName")
	private String marketName;
	@JsonProperty("MarketCurrency")
	private String marketCurrency;
	@JsonProperty("BaseCurrency")
	private String baseCurrency;
	@JsonProperty("MarketCurrencyLong")
	private String marketCurrencyLong;
	@JsonProperty("BaseCurrencyLong")
	private String baseCurrencyLong;
	@JsonProperty("MinTradeSize")
	private double minTradeSize;
	@JsonProperty("IsActive")
	private boolean active;
	@JsonProperty("Created")
	private Date created;

	
	

}
