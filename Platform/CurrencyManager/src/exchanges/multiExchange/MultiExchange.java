package exchanges.multiExchange;

import java.util.Collection;

import arithmetic.Pfloat;
import currencies.Currency;
import currencyExchanges.CurrencyMarket;
import exchanges.BestEffortExchange;
import exchanges.Exchange;
import holdings.Holding;
import offerGroups.Offers;
import orders.Order;
import transactions.Transaction;

/**
 * This exchange will perform over a group of other exchanges. This means that
 * by using this class with multiple exchanges, you should be able to get a
 * full, integrated viewpoint of the entire market, seamlessly bundled like any
 * other exchange.
 * 
 * @author Stephen Buttolph
 */
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
	public Collection<Order> getOpenOrders(CurrencyMarket market) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<Transaction> getTradeHistory(CurrencyMarket market) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Pfloat getBalance(Currency currency) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean sendFundsTo(Exchange exchange, Holding holding) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getWalletAddress(Currency currency) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<CurrencyMarket> getCurrencyMarkets() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Order buy(Pfloat toSpend, CurrencyMarket market, Pfloat price) {
		// TODO Auto-generated method stub
		return null;
	}
}
