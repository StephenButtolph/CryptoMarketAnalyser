package platforms.orders;

import arithmetic.Pfloat;
import platforms.currencies.markets.CurrencyMarket;

public interface Order {
	CurrencyMarket getMarket();

	Pfloat getPrice();

	Pfloat getAmountCurrency();

	Pfloat getAmountCommodity();
}
