package tickers;

import org.apfloat.Apfloat;

import currencies.Currency;

public class MultiTicker implements Ticker {
	@Override
	public Apfloat getPrice(Currency currency, Currency comodity) {
		throw new RuntimeException("Unimplemented.");
	}

	@Override
	public Apfloat get24HVolume(Currency currency) {
		throw new RuntimeException("Unimplemented.");
	}
}
