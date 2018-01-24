package platforms.exchanges;

import arithmetic.Pfloat;
import platforms.currencies.markets.CurrencyMarket;
import platforms.orders.Order;

/**
 * This represents an exchange that will simply place an order with a remote
 * exchange at the expected price, with no guarantee that the order will be
 * filled as requested.
 * 
 * @author Stephen Buttolph
 */
public abstract class BestEffortExchange extends StandardExchange {
	@Override
	public Order buy(Pfloat toSpend, CurrencyMarket market) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Order buy(CurrencyMarket market, Pfloat toBuy) {
		// TODO Auto-generated method stub
		return null;
	}
}
