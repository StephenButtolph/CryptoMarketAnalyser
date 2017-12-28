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

		Currency tether = CurrencyFactory.parseSymbol(usdt);
		Currency bitcoin = CurrencyFactory.parseSymbol(btc);
		
		Ticker ticker = new CoinMarketCap(Timing.MINUTE);
		
		System.out.println("Bitcoin price = " + ticker.getPrice(tether, bitcoin));
	}
}
