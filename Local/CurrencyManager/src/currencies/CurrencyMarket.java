package currencies;

import java.util.Objects;

public class CurrencyMarket {
	private final Currency currency, commodity;

	public CurrencyMarket(Currency currency, Currency commodity) {
		this.currency = currency;
		this.commodity = commodity;
	}

	public Currency getCurrency() {
		return currency;
	}

	public Currency getCommodity() {
		return commodity;
	}

	public CurrencyMarket invert() {
		return new CurrencyMarket(getCommodity(), getCurrency());
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
		return (Objects.equals(currency, other.currency) && Objects.equals(commodity, other.commodity))
				|| (Objects.equals(currency, other.commodity) && Objects.equals(commodity, other.currency));
	}
	
	public boolean contains(Currency currency) {
		return Objects.equals(this.currency, currency) || Objects.equals(commodity, currency);
	}

	@Override
	public String toString() {
		String format = "%s/%s";
		return String.format(format, getCommodity(), getCurrency());
	}
}
