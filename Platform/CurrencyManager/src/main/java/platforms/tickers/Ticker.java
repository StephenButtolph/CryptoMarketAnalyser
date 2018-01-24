package platforms.tickers;

import arithmetic.Pfloat;
import platforms.currencies.Currency;
import platforms.currencies.markets.CurrencyMarket;

public interface Ticker {
	Pfloat getPrice(CurrencyMarket market);

	Pfloat get24HVolume(Currency currency);
}
