package org.gh.coinbot.entities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

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
public class MarketTickValues {

	@Id
	@GeneratedValue
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "MARKET_ID",referencedColumnName="marketName", foreignKey = @ForeignKey (name="Fk_tickValue_market"), nullable = false)
	private Market market;
	@JsonProperty("Bid")
	private Double bid;
	@JsonProperty("Ask")
	private Double ask;
	@JsonProperty("Last")
	private Double last;
	private Date updateDate;
	
	
}
