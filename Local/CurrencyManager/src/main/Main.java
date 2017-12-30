package main;

import constants.Timing;
import currencies.Currency;
import currencies.CurrencyFactory;
import tickers.CoinMarketCap;
import tickers.Ticker;

public class Main {
	public static void main(String[] args) {
		coinCapTest();
	}

	public static void coinCapTest() {
		String usdt = "usdt";
		String btc = "btc";
		String eth = "eth";

		Currency tether = CurrencyFactory.parseSymbol(usdt);
		Currency bitcoin = CurrencyFactory.parseSymbol(btc);
		Currency ethereum = CurrencyFactory.parseSymbol(eth);

		Ticker ticker = new CoinMarketCap(Timing.MINUTE);

		System.out.println("Bitcoin price = " + ticker.getPrice(tether, bitcoin) + " usdt");
		System.out.println("Bitcoin price = " + ticker.getPrice(ethereum, bitcoin) + " eth");
	}
}
