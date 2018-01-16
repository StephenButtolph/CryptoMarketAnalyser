package exchanges.poloniex;

import arithmetic.Pfloat;
import currencyExchanges.CurrencyMarket;
import orders.ClosedOrder;
import utils.arithmetic.ArithmeticUtils;

public class PoloniexClosedOrder extends ClosedOrder {
	private Pfloat price;
	private Pfloat amountCurrency;
	private Pfloat amountCommodity;

	public PoloniexClosedOrder(CurrencyMarket market, String amountCurrency, String amountCommodity)
			throws NumberFormatException {
		this(market, new Pfloat(amountCurrency), new Pfloat(amountCommodity));
	}

	public PoloniexClosedOrder(CurrencyMarket market, String price, String amountCurrency, String amountCommodity)
			throws NumberFormatException {
		this(market, new Pfloat(price), new Pfloat(amountCurrency), new Pfloat(amountCommodity));
	}

	public PoloniexClosedOrder(CurrencyMarket market, Pfloat amountCurrency, Pfloat amountCommodity) {
		this(market, ArithmeticUtils.getPrice(amountCurrency, amountCommodity), amountCurrency, amountCommodity);
	}

	public PoloniexClosedOrder(CurrencyMarket market, Pfloat price, Pfloat amountCurrency, Pfloat amountCommodity) {
		super(market);
		
		this.price = price;
		this.amountCurrency = amountCurrency;
		this.amountCommodity = amountCommodity;
	}

	@Override
	public Pfloat getPrice() {
		return price;
	}

	@Override
	public Pfloat getAmountCurrency() {
		return amountCurrency;
	}

	@Override
	public Pfloat getAmountCommodity() {
		return amountCommodity;
	}
}
