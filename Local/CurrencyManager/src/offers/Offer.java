package offers;

import java.math.BigDecimal;

public class Offer {
	private BigDecimal price;
	private BigDecimal amount;

	public Offer(BigDecimal price, BigDecimal amount) {
		this.price = price;
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

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Offer)) {
			return false;
		}
		Offer other = (Offer) obj;
		return price.equals(other.price) && amount.equals(other.amount);
	}
}
