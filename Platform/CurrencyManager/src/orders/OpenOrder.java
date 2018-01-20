package orders;

import java.util.Collection;

import arithmetic.Pfloat;
import currencyExchanges.CurrencyMarket;
import utils.collections.iterables.IterableUtils;

public abstract class OpenOrder extends StandardOrder {
	protected OpenOrder(CurrencyMarket market) {
		super(market);
	}

	public abstract boolean isOpen();

	public abstract boolean cancel();

	public abstract Collection<? extends ClosedOrder> getCompleted();

	public Pfloat getPercentCompleted() {
		Iterable<Pfloat> amountsCurrency = IterableUtils.map(getCompleted(), ClosedOrder::getAmountCurrency);

		Pfloat completed = IterableUtils.fold(amountsCurrency, Pfloat::add, Pfloat.ZERO);
		return completed.divide(getAmountCurrency());
	}
}
