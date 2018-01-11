package tickers;

import arithmetic.Pfloat;
import currencies.Currency;
import currencyExchanges.CurrencyMarket;

public interface Ticker {
	Pfloat getPrice(CurrencyMarket market);

	Pfloat get24HVolume(Currency currency);
}
