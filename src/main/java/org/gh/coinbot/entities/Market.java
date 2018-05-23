package org.gh.coinbot.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
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

	private String marketName;

	private String plateform;
	@Transient
	private String marketCurrency;
	@Transient
	private String baseCurrency;
	private String marketCurrencyLong;
	private String baseCurrencyLong;
	private double minTradeSize;
	private boolean active;
	private Date created;
	private String Type;
	
	@ManyToOne
	@JoinColumn(name = "CURRENCY_MARKET_ID", foreignKey = @ForeignKey (name="Fk_market_currencymarket"), nullable = false)
	private Currency currencyMarket;
	
	@ManyToOne
	@JoinColumn(name = "CURRENCY_BASE_ID", foreignKey = @ForeignKey (name="Fk_market_currencybase"), nullable = false )
	private Currency currencyBase;
	

}
