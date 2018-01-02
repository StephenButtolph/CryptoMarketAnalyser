package main;

import constants.Timing;
import currencies.Currency;
import currencies.CurrencyFactory;
import currencies.CurrencyMarket;
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

		CurrencyMarket tetherToBitcoin = new CurrencyMarket(tether, bitcoin);
		CurrencyMarket ethereumToBitcoin = new CurrencyMarket(ethereum, bitcoin);

		Ticker ticker = new CoinMarketCap(Timing.MINUTE);

		System.out.println("Bitcoin price = " + ticker.getPrice(tetherToBitcoin) + " usdt");
		System.out.println("Bitcoin price = " + ticker.getPrice(ethereumToBitcoin) + " eth");
	}
}
