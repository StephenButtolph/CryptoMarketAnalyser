package tickers;

import java.math.BigDecimal;

import currencies.Currency;

public interface Ticker {
	BigDecimal getPrice(Currency currency, Currency comodity);
	BigDecimal get24HVolume(Currency currency);
}
