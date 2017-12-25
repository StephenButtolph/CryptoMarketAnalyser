package offers;

import java.math.BigDecimal;

public class Offer {
	private final BigDecimal price;
	private final BigDecimal amount;

	public Offer(BigDecimal price, BigDecimal amount) {
		this.price = price;
		this.amount = amount;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	@Override
	public String toString() {
		return "Offer: Price = " + price + ", Amount = " + amount;
	}
}
