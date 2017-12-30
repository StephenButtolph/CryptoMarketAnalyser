package tickers;

import arithmetic.Pfloat;
import currencies.Currency;

public interface Ticker {
	Pfloat getPrice(Currency currency, Currency commodity);

	Pfloat get24HVolume(Currency currency);
}
