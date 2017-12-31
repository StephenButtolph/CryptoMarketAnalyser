package exchanges;

import accounts.Holding;
import arithmetic.Pfloat;
import currencies.Currency;
import offers.Offers;

public abstract class StandardExchange implements Exchange {
	protected abstract Offers getRawOffers(Currency currency, Currency commodity);

	protected abstract Offers adjustOffers(Offers rawOffers);
	
	@Override
	public Offers getOffers(Currency currency, Currency commodity) {
		// TODO Auto-generated method stub
		return null;
	}

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
