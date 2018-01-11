package exchanges;

import arithmetic.Pfloat;
import currencyExchanges.CurrencyMarket;
import orders.Order;

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
