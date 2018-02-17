package org.gh.coinbot.entities;

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
public class Currency {

	@Id
	@JsonProperty("Currency")
	private String currency;
	@JsonProperty("CurrencyLong")
	private String currencyLong;
	@JsonProperty("MinConfirmation")
	private int minConfirmation;
	@JsonProperty("TxFee")
	private double txFee;
	@JsonProperty("IsActive")
	private boolean active;
	@JsonProperty("CoinType")
	private String coinType;
	@JsonProperty("BaseAddress")
	private String baseAddress;
	

}
