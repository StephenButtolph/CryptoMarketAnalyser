package currencies;

/**
 * This is an internal representation of a currency that can be traded.
 * 
 * @author Stephen Buttolph
 */
public interface Currency {
	/**
	 * @return the name of this currency.
	 */
	String getName();

	/**
	 * 
	 * @return the symbol of this currency.
	 */
	String getSymbol();
}
