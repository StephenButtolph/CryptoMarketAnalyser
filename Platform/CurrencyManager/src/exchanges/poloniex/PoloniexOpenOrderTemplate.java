package exchanges.poloniex;

import java.util.Objects;

import arithmetic.Pfloat;
import currencyExchanges.CurrencyMarket;
import orders.OpenOrder;

abstract class PoloniexOpenOrderTemplate extends OpenOrder {
	private Pfloat price;
	private Pfloat amountCurrency;
	private Pfloat amountCommodity;

	private String orderID;

	protected PoloniexOpenOrderTemplate(CurrencyMarket market, String price, String amountCurrency,
			String amountCommodity, String orderID) throws NumberFormatException {
		this(market, new Pfloat(price), new Pfloat(amountCurrency), new Pfloat(amountCommodity), orderID);
	}

	protected PoloniexOpenOrderTemplate(CurrencyMarket market, Pfloat price, Pfloat amountCurrency,
			Pfloat amountCommodity, String orderID) {
		super(market);

		this.price = price;
		this.amountCurrency = amountCurrency;
		this.amountCommodity = amountCommodity;

		this.orderID = orderID;
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

	public String getOrderID() {
		return orderID;
	}

	@Override
	public int hashCode() {
		return Objects.hash(getMarket(), getOrderID());
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof PoloniexOpenOrderTemplate)) {
			return false;
		}

		PoloniexOpenOrderTemplate other = (PoloniexOpenOrderTemplate) o;
		return getOrderID().equals(other.getOrderID());
	}
}
