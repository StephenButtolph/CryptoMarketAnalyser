package exchanges;

import java.util.Collection;

import accounts.Holding;
import arithmetic.Pfloat;
import currencies.Currency;
import currencies.CurrencyMarket;
import offers.Offers;

public class MultiExchange extends BestEffortExchange {
	@Override
	public Pfloat get24HVolume(Currency currency) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Offers getRawOffers(CurrencyMarket market) {
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
	public void getTradeHistory(CurrencyMarket market) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Pfloat getBalance(Currency currency) {
		// TODO Auto-generated method stub
		return null;
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
	public Collection<CurrencyMarket> getCurrencyMarkets() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void buy(Pfloat toSpend, CurrencyMarket market, Pfloat price) {
		// TODO Auto-generated method stub
		
	}
}
