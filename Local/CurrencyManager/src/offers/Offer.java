package offers;

import org.apfloat.Apfloat;

public class Offer {
	private final Apfloat price;
	private final Apfloat amount;

	public Offer(Apfloat price, Apfloat amount) {
		this.price = price;
		this.amount = amount;
	}

	public Apfloat getPrice() {
		return price;
	}

	public Apfloat getAmount() {
		return amount;
	}

	@Override
	public String toString() {
		return "Offer: Price = " + price + ", Amount = " + amount;
	}
}
