package exchanges;

import accounts.Holding;
import arithmetic.Pfloat;
import currencies.Currency;
import currencies.CurrencyMarket;
import offers.Offers;

public abstract class StandardExchange implements Exchange {
	protected abstract Offers getRawOffers(CurrencyMarket exchangePair);

	protected abstract Offers adjustOffers(Offers rawOffers);
	
	@Override
	public Offers getOffers(CurrencyMarket market) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void getOpenTransactions(CurrencyMarket market) {
		// TODO Auto-generated method stub

	}

	@Override
	public Pfloat getPrice(CurrencyMarket market) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Pfloat getPrice(CurrencyMarket market, Pfloat amount) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Pfloat getReturn(CurrencyMarket market, Pfloat amount) {
		// TODO Auto-generated method stub
		return null;
	}
}
