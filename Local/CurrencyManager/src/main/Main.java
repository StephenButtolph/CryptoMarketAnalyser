package main;

import arithmetic.Pfloat;
import constants.Auths;
import constants.Timing;
import currencies.Currency;
import currencies.CurrencyFactory;
import currencies.CurrencyMarket;
import exchanges.Poloniex;
import tickers.CoinMarketCap;
import tickers.Ticker;

public class Main {
	public static void main(String[] args) {
		poloniexTest();
	}
	
	public static void poloniexTest() {
		System.out.println(Auths.POLONIEX_AUTH);
		
		Poloniex p = new Poloniex(Auths.POLONIEX_AUTH);

		Currency eth = CurrencyFactory.parseSymbol("eth");

		Pfloat vol = p.get24HVolume(eth);

		System.out.println(vol);
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
