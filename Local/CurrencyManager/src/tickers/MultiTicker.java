package tickers;

import java.math.BigDecimal;

import currencies.Currency;

public class MultiTicker implements Ticker {
	@Override
	public BigDecimal getPrice(Currency currency, Currency comodity) {
		throw new RuntimeException("Unimplemented.");
	}

	@Override
	public BigDecimal get24HVolume(Currency currency) {
		throw new RuntimeException("Unimplemented.");
	}
}
