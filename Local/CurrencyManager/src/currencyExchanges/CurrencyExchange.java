package currencyExchanges;

import currencies.Currency;
import exchanges.Exchange;

/**
 * Allows the usage of focusing on individual markets and transacting with them,
 * rather than using then entire exchange platform.
 * 
 * @author Stephen Buttolph
 */
public interface CurrencyExchange {
	/**
	 * @return the exchange this currency exchange is using.
	 */
	Exchange getExchange();

	/**
	 * @return the currency this exchange is using.
	 */
	Currency getCurrency();

	/**
	 * @return the commodity this exchange is selling.
	 */
	Currency getCommodity();

	/**
	 * @return the market this exchange is utilizing.
	 */
	CurrencyMarket getMarket();

	/**
	 * @return the currency exchange with the same base exchange but with the
	 *         currency and the commodity switched.
	 */
	CurrencyExchange invert();

	// TODO add standard exchange method stubs
}
