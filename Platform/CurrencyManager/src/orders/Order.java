package orders;

import arithmetic.Pfloat;
import currencyExchanges.CurrencyMarket;

public interface Order {
	boolean isOpen();

	boolean cancel();

	CurrencyMarket getMarket();

	Pfloat getPrice();

	Pfloat getAmountCurrency();

	Pfloat getAmountCommodity();
}
