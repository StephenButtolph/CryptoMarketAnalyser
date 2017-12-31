package accounts;

import arithmetic.Pfloat;
import currencies.Currency;

/**
 * @author Stephen Buttolph
 * 
 *         Represents a currency holding. This will include, the currency to
 *         represent the units of the holding, the amount to represent the
 *         magnitude of the holding, and the location the holding is stored in.
 */
public class Holding {
	private Currency currency;
	private Pfloat amount;

	// TODO include the location of storage.

	/**
	 * Create a new holding with the given magnitude [amount] and the unit of
	 * currency [currency].
	 * 
	 * @param currency
	 *            The unit of currency that this holding will have.
	 * @param amount
	 *            The magnitude this holding will have.
	 */
	public Holding(Currency currency, Pfloat amount) {
		this.currency = currency;
		this.amount = amount;
	}

	/**
	 * Get the unit of currency that this holding has.
	 * 
	 * @return The unit of currency that this holding has.
	 */
	public Currency getCurrency() {
		return this.currency;
	}

	/**
	 * Get the magnitude of this holding.
	 * 
	 * @return The magnitude of this holding.
	 */
	public Pfloat getAmount() {
		return this.amount;
	}
}
