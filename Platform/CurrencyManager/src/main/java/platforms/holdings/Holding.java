package platforms.holdings;

import arithmetic.Pfloat;
import platforms.currencies.Currency;

/**
 * Represents a currency holding. This will include, the currency to represent
 * the units of the holding and the amount to represent the magnitude of the
 * holding.
 * 
 * @author Stephen Buttolph
 */
public interface Holding {
	/**
	 * Get the unit of currency that this holding has.
	 * 
	 * @return The unit of currency that this holding has.
	 */
	public Currency getCurrency();

	/**
	 * Get the magnitude of this holding.
	 * 
	 * @return The magnitude of this holding.
	 */
	public Pfloat getAmount();
}
