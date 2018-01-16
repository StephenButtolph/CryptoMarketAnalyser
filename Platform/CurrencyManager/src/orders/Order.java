package orders;

import arithmetic.Pfloat;
import currencyExchanges.CurrencyMarket;

public interface Order {
	CurrencyMarket getMarket();

	Pfloat getPrice();

	Pfloat getAmountCurrency();

	Pfloat getAmountCommodity();
}
