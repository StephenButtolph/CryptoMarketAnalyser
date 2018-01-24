package platforms.exchanges.markets;

import platforms.currencies.Currency;
import platforms.currencies.markets.CurrencyMarket;
import platforms.currencies.markets.StandardCurrencyMarket;
import platforms.exchanges.Exchange;

public class StandardCurrencyExchange extends StandardCurrencyMarket implements CurrencyExchange {
	private Exchange exchange;

	public StandardCurrencyExchange(Exchange exchange, CurrencyMarket market) {
		this(exchange, market.getCurrency(), market.getCommodity());
	}

	public StandardCurrencyExchange(Exchange exchange, Currency currency, Currency commodity) {
		super(currency, commodity);

		this.exchange = exchange;
	}

	@Override
	public StandardCurrencyExchange invert() {
		return new StandardCurrencyExchange(exchange, super.invert());
	}

	@Override
	public Exchange getExchange() {
		return exchange;
	}
}
