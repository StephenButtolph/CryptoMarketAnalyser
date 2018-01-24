package platforms.currencies.markets;

import platforms.currencies.Currency;

/**
 * Represents a currency pairing, a currency and a commodity currency. Used when
 * trying to by a commodity currency with a currency currency.
 * 
 * @author Stephen Buttolph
 */
public interface CurrencyMarket {
	/**
	 * @return the currency currency.
	 */
	Currency getCurrency();

	/**
	 * @return the commodity currency.
	 */
	Currency getCommodity();

	/**
	 * @return a new currency market with the currency equal to this markets
	 *         commodity and the commodity equal to this markets currency,
	 */
	CurrencyMarket invert();

	/**
	 * Checks if the supplied currency [currency] is either this markets currency or
	 * commodity.
	 * 
	 * @param currency
	 *            The currency to check.
	 * @return True if [currency] is equal to this market's currency or commodity.
	 */
	boolean contains(Currency currency);
}
