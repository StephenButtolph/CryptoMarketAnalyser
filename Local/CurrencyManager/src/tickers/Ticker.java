package tickers;

import org.apfloat.Apfloat;

import currencies.Currency;

public interface Ticker {
	Apfloat getPrice(Currency currency, Currency comodity);
	Apfloat get24HVolume(Currency currency);
}
