package exchanges;

import arithmetic.Pfloat;
import currencies.Currency;

public class Poloniex implements Exchange {
	@Override
	public Pfloat getPrice(Currency currency, Currency comodity) {
		throw new RuntimeException("Unimplemented.");
	}

	@Override
	public Pfloat get24HVolume(Currency currency) {
		throw new RuntimeException("Unimplemented.");
	}
}
