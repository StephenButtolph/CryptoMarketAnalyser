package main;

import constants.Auths;
import constants.Timing;
import currencies.Currency;
import currencies.CurrencyFactory;
import currencyExchanges.CurrencyMarket;
import exchanges.poloniex.Poloniex;
import tickers.Ticker;
import tickers.coinMarketCap.CoinMarketCap;

/**
 * Test entry point for functions not clearly suited for JUnit testing.
 * 
 * @author Stephen Buttolph
 */
public class Main {
	public static void main(String[] args) {
		poloniexTest();
	}

	public static void poloniexTest() {
		System.out.println(Auths.POLONIEX_AUTH);
		Poloniex p = new Poloniex(Auths.POLONIEX_AUTH);

		Currency etc = CurrencyFactory.parseSymbol("USDT");
		System.out.println(etc);

		Object ret = p.getWalletAddress(etc);
		System.out.println(ret);
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

		System.out.println("Ethereum 24h Volume = $" + ticker.get24HVolume(ethereum));
	}
}
