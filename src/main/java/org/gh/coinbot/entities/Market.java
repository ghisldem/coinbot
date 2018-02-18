package org.gh.coinbot.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

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
	@Transient
	@JsonProperty("MarketCurrency")
	private String marketCurrency;
	@Transient
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
	
	@ManyToOne
	@JoinColumn(name = "CURRENCY_MARKET_ID", foreignKey = @ForeignKey (name="Fk_market_currencymarket"), nullable = false)
	private Currency currencyMarket;
	
	@ManyToOne
	@JoinColumn(name = "CURRENCY_BASE_ID", foreignKey = @ForeignKey (name="Fk_market_currencybase"), nullable = false)
	private Currency currencyBase;
	

	
	

}
