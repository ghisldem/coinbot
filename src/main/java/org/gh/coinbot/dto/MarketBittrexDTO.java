package org.gh.coinbot.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;


@lombok.Data
public class MarketBittrexDTO {
	
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
