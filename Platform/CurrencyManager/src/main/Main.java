package main;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import constants.Timing;
import currencies.Currency;
import currencies.CurrencyFactory;
import guis.marketLogger.MarketLogger;
import tickers.coinMarketCap.CoinMarketCap;
import types.TypeProducer;
import types.TypeToken;
import utils.files.FileUtils;
import utils.maps.MapUtils;

/**
 * Test entry point for functions not clearly suited for JUnit testing.
 * 
 * @author Stephen Buttolph
 */
public class Main {
	public static void main(String[] args) throws InterruptedException {
		Currency btc = CurrencyFactory.parseSymbol("btc");
		Currency eth = CurrencyFactory.parseSymbol("eth");
		Currency usdt = CurrencyFactory.parseSymbol("usdt");

		Collection<Currency> currencies = new ArrayList<>();
		currencies.add(btc);
		currencies.add(eth);
		currencies.add(usdt);

		MarketLogger logger = new MarketLogger(new CoinMarketCap(Timing.SECOND));
		logger.setCurrencies(currencies);
		logger.start();

		Instant now = Instant.now();
		TypeProducer typeProducer = new TypeToken<Map<String, String>>();

		Map<Currency, Instant> lastUpdates = new HashMap<>();
		lastUpdates.put(eth, now);

		Map<String, String> lastUpdatesFormatted = MapUtils.convertEntries(lastUpdates, Object::toString,
				Object::toString);

		FileUtils.save("updates.txt", lastUpdatesFormatted, typeProducer);
		Map<String, String> lastUpdatesLoaded = FileUtils.load("updates.txt", typeProducer);

		Map<Currency, Instant> lastUpdatesParsed = MapUtils.convertEntries(lastUpdatesLoaded,
				CurrencyFactory::parseCurrency, Instant::parse);

		System.out.println(lastUpdates.get(eth));
		System.out.println(lastUpdatesParsed.containsKey(eth));
		System.out.println(lastUpdatesParsed.get(eth));
		System.out.println(lastUpdatesParsed);
		System.out.println(lastUpdates.get(eth).equals(lastUpdatesParsed.get(eth)));

		// System.out.println(new File("updates.txt").delete());
	}
}
