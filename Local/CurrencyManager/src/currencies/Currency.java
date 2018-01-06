package currencies;

import java.util.Objects;

public class Currency {
	private final String name, symbol;

	public Currency(String name, String symbol) {
		this.name = name;
		this.symbol = symbol;
	}

	public String getName() {
		return name;
	}

	public String getSymbol() {
		return symbol;
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(name) + Objects.hashCode(symbol);
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof Currency)) {
			return false;
		}

		Currency other = (Currency) o;
		return Objects.equals(name, other.name) && Objects.equals(symbol, other.symbol);
	}

	@Override
	public String toString() {
		String format = "%s(%s)";
		return String.format(format, getName(), getSymbol());
	}
}
