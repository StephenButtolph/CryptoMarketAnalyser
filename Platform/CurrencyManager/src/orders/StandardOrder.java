package orders;

import currencyExchanges.CurrencyMarket;

abstract class StandardOrder implements Order {
	private CurrencyMarket market;

	protected StandardOrder(CurrencyMarket market) {
		this.market = market;
	}

	@Override
	public CurrencyMarket getMarket() {
		return market;
	}

	@Override
	public String toString() {
		String format = "Market = %s, Amount Currency = %s, Amount Commodity = %s.";
		return String.format(format, getMarket(), getAmountCurrency(), getAmountCommodity());
	}
}
