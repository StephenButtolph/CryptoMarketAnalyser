package currencies;

public class Currency {
	private final String name, symbol;
	
	public Currency(String name, String symbol) {
		this.name = name;
		this.symbol = symbol;
	}
	
	@Override
	public String toString() {
		String format = "%s(%s)";
		return String.format(format, name, symbol);
	}
}
