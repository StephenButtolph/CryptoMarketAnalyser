package exchanges;

import arithmetic.Pfloat;
import currencyExchanges.CurrencyMarket;
import offerGroups.Offers;

public abstract class StandardExchange implements Exchange {
	protected abstract Offers getRawOffers(CurrencyMarket exchangePair);

	protected abstract Offers adjustOffers(Offers rawOffers);

	@Override
	public Offers getOffers(CurrencyMarket market) {
		Offers rawOffers = getRawOffers(market);
		return adjustOffers(rawOffers);
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
