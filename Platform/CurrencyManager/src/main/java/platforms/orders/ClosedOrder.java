package platforms.orders;

import platforms.currencies.markets.CurrencyMarket;

public abstract class ClosedOrder extends StandardOrder {
	protected ClosedOrder(CurrencyMarket market) {
		super(market);
	}
}
