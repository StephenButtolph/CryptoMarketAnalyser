package exchanges;

import accounts.Holding;
import arithmetic.Pfloat;
import currencies.Currency;

public abstract class StandardExchange implements Exchange {
	@Override
	public void getOpenTransactions(Currency currency, Currency commodity) {
		// TODO Auto-generated method stub

	}

	@Override
	public Pfloat getPrice(Currency currency, Currency commodity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Pfloat getPrice(Currency currency, Currency commodity, Pfloat amount) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Pfloat getReturn(Holding toSpend, Currency commodity) {
		// TODO Auto-generated method stub
		return null;
	}
}
