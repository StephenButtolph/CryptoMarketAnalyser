package orders;

import currencyExchanges.CurrencyMarket;

public abstract class ClosedOrder extends StandardOrder {
	protected ClosedOrder(CurrencyMarket market) {
		super(market);
	}
}
