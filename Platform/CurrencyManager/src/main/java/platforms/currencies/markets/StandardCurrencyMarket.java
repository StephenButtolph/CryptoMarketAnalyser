package platforms.currencies.markets;

import java.util.Objects;

import platforms.currencies.Currency;

/**
 * Represents a standard implementation of a currency market.
 * 
 * @author Stephen Buttolph
 */
public class StandardCurrencyMarket implements CurrencyMarket {
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
	public StandardCurrencyMarket(Currency currency, Currency commodity) {
		this.currency = currency;
		this.commodity = commodity;
	}

	@Override
	public Currency getCurrency() {
		return currency;
	}

	@Override
	public Currency getCommodity() {
		return commodity;
	}

	@Override
	public StandardCurrencyMarket invert() {
		return new StandardCurrencyMarket(getCommodity(), getCurrency());
	}

	@Override
	public boolean contains(Currency currency) {
		return Objects.equals(this.currency, currency) || Objects.equals(commodity, currency);
	}

	@Override
	public int hashCode() {
		return Objects.hash(currency, commodity);
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof StandardCurrencyMarket)) {
			return false;
		}

		StandardCurrencyMarket other = (StandardCurrencyMarket) o;
		return (Objects.equals(currency, other.currency) && Objects.equals(commodity, other.commodity));
	}

	@Override
	public String toString() {
		String format = "%s/%s";
		return String.format(format, getCommodity(), getCurrency());
	}
}
