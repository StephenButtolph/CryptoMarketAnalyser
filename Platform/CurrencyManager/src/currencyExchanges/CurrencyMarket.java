package currencyExchanges;

import java.util.Objects;

import currencies.Currency;

/**
 * Represents a currency pairing, a currency and a commodity currency. Used when
 * trying to by a commodity currency with a currency currency.
 * 
 * @author Stephen Buttolph
 */
public class CurrencyMarket {
	private final Currency currency;
	private final Currency commodity;

	/**
	 * Create a new currency market with the given [currency] currency and
	 * [commodity] currencies.
	 * 
	 * @param currency
	 *            The currency to spend.
	 * @param commodity
	 *            The currency to buy.
	 */
	public CurrencyMarket(Currency currency, Currency commodity) {
		this.currency = currency;
		this.commodity = commodity;
	}

	/**
	 * @return the currency currency.
	 */
	public Currency getCurrency() {
		return currency;
	}

	/**
	 * @return the commodity currency.
	 */
	public Currency getCommodity() {
		return commodity;
	}

	/**
	 * @return a new currency market with the currency equal to this markets
	 *         commodity and the commodity equal to this markets currency,
	 */
	public CurrencyMarket invert() {
		return new CurrencyMarket(getCommodity(), getCurrency());
	}

	/**
	 * Checks if the supplied currency [currency] is either this markets currency or
	 * commodity.
	 * 
	 * @param currency
	 *            The currency to check.
	 * @return True if [currency] is equal to this market's currency or commodity.
	 */
	public boolean contains(Currency currency) {
		return Objects.equals(this.currency, currency) || Objects.equals(commodity, currency);
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(currency) + Objects.hashCode(commodity);
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof CurrencyMarket)) {
			return false;
		}

		CurrencyMarket other = (CurrencyMarket) o;
		return (Objects.equals(currency, other.currency) && Objects.equals(commodity, other.commodity));
	}

	@Override
	public String toString() {
		String format = "%s/%s";
		return String.format(format, getCommodity(), getCurrency());
	}
}
