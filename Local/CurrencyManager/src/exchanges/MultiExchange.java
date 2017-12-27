package exchanges;

import java.math.BigDecimal;

import currencies.Currency;

public class MultiExchange implements Exchange {
	@Override
	public BigDecimal getPrice(Currency currency, Currency comodity) {
		throw new RuntimeException("Unimplemented.");
	}

	@Override
	public BigDecimal get24HVolume(Currency currency) {
		throw new RuntimeException("Unimplemented.");
	}
}
