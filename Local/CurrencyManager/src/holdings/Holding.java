package holdings;

import arithmetic.Pfloat;
import currencies.Currency;

/**
 * @author Stephen Buttolph
 * 
 *         Represents a currency holding. This will include, the currency to
 *         represent the units of the holding and the amount to represent the
 *         magnitude of the holding.
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
