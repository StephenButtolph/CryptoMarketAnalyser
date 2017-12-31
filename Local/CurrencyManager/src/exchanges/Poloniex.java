package exchanges;

import accounts.Holding;
import arithmetic.Pfloat;
import currencies.Currency;
import offers.Offer;
import offers.Offers;

public class Poloniex extends BestEffortExchange {
	@Override
	public Pfloat get24HVolume(Currency currency) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Offers getRawOffers(Currency currency, Currency commodity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Offers adjustOffers(Offers rawOffers) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void getOpenTransactions() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void getTradeHistory(Currency currency, Currency commodity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void getBalance(Currency currency) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void sendFundsTo(Exchange exchange, Holding holding) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void getWalletAddress(Currency currency) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void getTradableCurrencies() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void buy(Holding toSpend, Pfloat price, Currency commodity) {
		// TODO Auto-generated method stub
		
	}
}
