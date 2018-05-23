package org.gh.coinbot.dto;

import java.util.Date;

import org.gh.coinbot.entities.Market;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

@Component
@JsonIgnoreProperties(ignoreUnknown = true)
public class MarketGetTickerCoinMarkertCapDTO {

	
	// info market
	private String marketName;// a construire
	private String plateform = "coinMarketCap";
	@JsonProperty("symbol") // example "BTC"
	private String marketCurrency;
	private String baseCurrency = "EUR";
	@JsonProperty("name") // example "Bitcoin"
	private String marketCurrencyLong;
	private String baseCurrencyLong = "Euro";
	private Double minTradeSize = null;
	private boolean active = true;
	private Date created = null;
	private String Type = "MR";
	
	
	//info ticker
	
	private Market market;
	private Double bid = null;
	private Double ask = null;
	private Double last = null;
	@JsonProperty("price_eur")
	private Double valueEuro;
	private Date updateDate;
	
	
	@JsonProperty("id")
	private String id;
	
	// info currency
	@JsonProperty("rank")
	private String rank;
	
	private Observable<String> observable;
	
	
	{
		
	}
	
	public MarketGetTickerCoinMarkertCapDTO() {
//		this.observable = Observable.create(emitter->{
//			
//			emitter.onNext(this.getMarketCurrency());
//		});
		
		this.getObservableMarketCurrency()	.subscribeOn(Schedulers.io())
											.observeOn(scheduler)
		
//		observable.subscribe(marketCurrency ->
//		//this.setMarketName(marketCurrency+ "-" + this.getBaseCurrency() + "-" + "coinMarketCap")
//		{System.out.println("test rxjava : " +marketCurrency);});

	}

	


	public String getMarketName() {
		return marketName;
	}


	public void setMarketName(String marketName) {
		this.marketName = marketName;
	}


	public String getPlateform() {
		return plateform;
	}


	public void setPlateform(String plateform) {
		this.plateform = plateform;
	}


	public String getMarketCurrency() {
		return marketCurrency;
	}
	public Observable<String> getObservableMarketCurrency(){
		
		return Observable.just(this.getMarketCurrency());
	}

	public void setMarketCurrency(String marketCurrency) {
		this.marketCurrency = marketCurrency;
	}


	public String getBaseCurrency() {
		return baseCurrency;
	}


	public void setBaseCurrency(String baseCurrency) {
		this.baseCurrency = baseCurrency;
	}


	public String getMarketCurrencyLong() {
		return marketCurrencyLong;
	}


	public void setMarketCurrencyLong(String marketCurrencyLong) {
		this.marketCurrencyLong = marketCurrencyLong;
	}


	public String getBaseCurrencyLong() {
		return baseCurrencyLong;
	}


	public void setBaseCurrencyLong(String baseCurrencyLong) {
		this.baseCurrencyLong = baseCurrencyLong;
	}


	public Double getMinTradeSize() {
		return minTradeSize;
	}


	public void setMinTradeSize(Double minTradeSize) {
		this.minTradeSize = minTradeSize;
	}


	public boolean isActive() {
		return active;
	}


	public void setActive(boolean active) {
		this.active = active;
	}


	public Date getCreated() {
		return created;
	}


	public void setCreated(Date created) {
		this.created = created;
	}


	public String getType() {
		return Type;
	}


	public void setType(String type) {
		Type = type;
	}


	public Market getMarket() {
		return market;
	}


	public void setMarket(Market market) {
		this.market = market;
	}


	public Double getBid() {
		return bid;
	}


	public void setBid(Double bid) {
		this.bid = bid;
	}


	public Double getAsk() {
		return ask;
	}


	public void setAsk(Double ask) {
		this.ask = ask;
	}


	public Double getLast() {
		return last;
	}


	public void setLast(Double last) {
		this.last = last;
	}


	public Double getValueEuro() {
		return valueEuro;
	}


	public void setValueEuro(Double valueEuro) {
		this.valueEuro = valueEuro;
	}


	public Date getUpdateDate() {
		return updateDate;
	}


	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	
	
}
