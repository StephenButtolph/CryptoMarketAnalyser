package exchanges.poloniex;

import arithmetic.Pfloat;
import currencies.CurrencyMarket;
import orders.Order;

public class PoloniexOrder implements Order {

	@Override
	public boolean isOpen() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean cancel() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public CurrencyMarket getMarket() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Pfloat getPrice() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Pfloat getAmountCurrency() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Pfloat getAmountCommodity() {
		// TODO Auto-generated method stub
		return null;
	}

}
