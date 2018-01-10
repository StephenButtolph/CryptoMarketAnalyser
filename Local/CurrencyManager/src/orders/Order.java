package orders;

import arithmetic.Pfloat;
import currencies.CurrencyMarket;

public interface Order {
	boolean isOpen();

	boolean cancel();

	CurrencyMarket getMarket();

	Pfloat getPrice();

	Pfloat getAmountCurrency();

	Pfloat getAmountCommodity();
}
