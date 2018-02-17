package org.gh.coinbot;

import org.gh.coinbot.externalapi.Bittrex;
import org.gh.coinbot.externalapi.BittrexV1;

public class Run {
	public static void main(String[] args) {
		Bittrex bittrex = new Bittrex("50a2f086486641eabeec9212d2e01f5c", "4b2082232e9c40d5849e74a9152f5726");
		// System.out.println(bittrex.getCurrencies());
		// System.out.println(bittrex.getTicker("btc-ltc"));
		// System.out.println(bittrex.getMarketSummary("btc-ltc").getResponseCode());
		//System.out.println(bittrex.getOrder("btc"));

		BittrexV1 bittrexV1 = new BittrexV1("50a2f086486641eabeec9212d2e01f5c", "4b2082232e9c40d5849e74a9152f5726");

		System.out.println(bittrexV1.set);
	}
}
