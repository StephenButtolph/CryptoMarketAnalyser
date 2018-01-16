package currencies;

import java.util.Objects;

/**
 * This is a standard representation of an internal currency that can be traded.
 * 
 * @author Stephen Buttolph
 */
class StandardCurrency implements Currency {
	private final String name;
	private final String symbol;

	/**
	 * Create a new currency with the given name [name] and symbol [symbol].
	 * 
	 * @param name
	 *            The name of the currency to represent.
	 * @param symbol
	 *            The symbol of the currency to represent.
	 */
	public StandardCurrency(String name, String symbol) {
		this.name = name;
		this.symbol = symbol;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getSymbol() {
		return symbol;
	}

	@Override
	public int hashCode() {
		return Objects.hash(name, symbol);
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof StandardCurrency)) {
			return false;
		}

		StandardCurrency other = (StandardCurrency) o;
		return Objects.equals(name, other.name) && Objects.equals(symbol, other.symbol);
	}

	@Override
	public String toString() {
		String format = "%s(%s)";
		return String.format(format, getName(), getSymbol());
	}
}
